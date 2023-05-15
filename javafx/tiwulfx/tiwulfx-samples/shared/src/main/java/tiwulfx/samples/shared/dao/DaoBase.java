/*
 * Copyright (c) 2015, Panemu
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package tiwulfx.samples.shared.dao;

import com.panemu.tiwulfx.common.TableCriteria;
import com.panemu.tiwulfx.common.TableData;
import javafx.scene.control.TableColumn.SortType;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @param <T> the entity type
 * @author Amrullah <amrullah@panemu.com>
 */
public class DaoBase<T> {

    private final Class<T> voClass;
    private static final EntityManagerFactory factory = Persistence.createEntityManagerFactory("tiwulfx-samplePU");
    //    EntityManager em = JavaApplication6.factory.createEntityManager();
    EntityManager em;

    public DaoBase(Class<T> clazz) {
//        this.em = em;
        this.voClass = clazz;
    }

    public <C> TableData<T> fetch(int startIndex,
                                  List<TableCriteria<C>> filteredColumns,
                                  List<String> sortedColumns,
                                  List<SortType> sortingVersus,
                                  int maxResult) {
        return this.fetch(startIndex, filteredColumns, sortedColumns, sortingVersus, maxResult, new ArrayList<String>());
    }

    public <C> TableData<T> fetch(int startIndex,
                                  List<TableCriteria<C>> filteredColumns,
                                  List<String> sortedColumns,
                                  List<SortType> sortingVersus,
                                  int maxResult, List<String> lstJoin) {
        em = factory.createEntityManager();
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<T> cq = builder.createQuery(voClass);
        Root<T> root = cq.from(voClass);
        Map<String, From> mapJoin = buildJoinMap(root, lstJoin, true);
        cq.select(root);
        cq.where(buildPredicates(filteredColumns, root, mapJoin));
//        long count = count(predicates, root);
        long count = count(filteredColumns, lstJoin);
        // ordering
        cq.orderBy(new ArrayList<>());
        for (int i = 0; i < sortedColumns.size(); i++) {
            From from = root;
            String attributeName = sortedColumns.get(i);
            if (attributeName.contains(".")) {
                from = mapJoin.get(attributeName.substring(0, attributeName.lastIndexOf(".")));
                attributeName = attributeName.substring(attributeName.lastIndexOf(".") + 1, attributeName.length());
            }
            if (sortingVersus.get(i) == SortType.DESCENDING) {
                cq.getOrderList().add(builder.desc(from.get(attributeName)));
            } else {
                cq.getOrderList().add(builder.asc(from.get(attributeName)));
            }
        }

        TypedQuery<T> typedQuery = em.createQuery(cq);

        typedQuery.setFirstResult(startIndex);
        typedQuery.setMaxResults(maxResult + 1);
        List<T> result = typedQuery.getResultList();
        boolean moreRows = result.size() > maxResult;
        if (moreRows) {
            result.remove(maxResult);// remove the last row
        }
        TableData tb = new TableData(new ArrayList<>(result), moreRows, count);
        em.close();
        return tb;
    }

    private Map<String, From> buildJoinMap(Root<T> root, List<String> lstJoin, boolean fetchJoin) {

        Map<String, From> mapJoin = new HashMap<>();
        for (String joinName : lstJoin) {
            From<?, ?> from;
            String joinTable = joinName;
            if (joinName.contains(".")) {
                String parentTable = joinName.substring(0, joinName.lastIndexOf("."));
                from = mapJoin.get(parentTable);
                joinName = joinName.substring(joinName.lastIndexOf(".") + 1, joinName.length());
            } else {
                from = root;
            }
            Join theJoin = from.join(joinName, JoinType.LEFT);
            if (fetchJoin) {
                from.fetch(joinName, JoinType.LEFT);
            }
            mapJoin.put(joinTable, theJoin);
        }
        return mapJoin;
    }

    private <C> long count(List<TableCriteria<C>> filteredColumns,
                           List<String> lstJoin) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = builder.createQuery(Long.class);
        Root<T> root = cq.from(voClass);
        Map<String, From> mapJoin = buildJoinMap(root, lstJoin, false);

        cq.select(builder.count(root));
        cq.where(buildPredicates(filteredColumns, root, mapJoin));

        long result = em.createQuery(cq).getSingleResult();

        return result;
    }

    private <C> Predicate[] buildPredicates(List<TableCriteria<C>> filteredColumns, Root<T> root, Map<String, From> mapJoin) {
        List<Predicate> lstPredicates = new ArrayList<>();
        CriteriaBuilder builder = em.getCriteriaBuilder();
        for (TableCriteria<C> tableCriteria : filteredColumns) {
            From from = root;
            String attributeName = tableCriteria.getAttributeName();
            if (tableCriteria.getAttributeName().contains(".")) {
                from = mapJoin.get(attributeName.substring(0, attributeName.lastIndexOf(".")));
                attributeName = attributeName.substring(attributeName.lastIndexOf(".") + 1, attributeName.length());
            }
            Comparable comparable;
            TableCriteria.Operator operator = tableCriteria.getOperator();
            Object value = tableCriteria.getValue();
            switch (operator) {
                case eq:
                    lstPredicates.add(builder.equal(from.get(attributeName), value));
                    break;
                case ne:
                    lstPredicates.add(builder.notEqual(from.get(attributeName), value));
                    break;
                case le:
                    comparable = (Comparable) value;
                    lstPredicates.add(builder.lessThanOrEqualTo(from.<Comparable>get(attributeName), comparable));
                    break;
                case lt:
                    comparable = (Comparable) value;
                    lstPredicates.add(builder.lessThan(from.<Comparable>get(attributeName), comparable));
                    break;
                case ge:
                    comparable = (Comparable) value;
                    lstPredicates.add(builder.greaterThanOrEqualTo(from.<Comparable>get(attributeName), comparable));
                    break;
                case gt:
                    comparable = (Comparable) value;
                    lstPredicates.add(builder.greaterThan(from.<Comparable>get(attributeName), comparable));
                    break;
                case like_begin:
                    lstPredicates.add(builder.like(from.<String>get(attributeName), value.toString() + "%"));
                    break;
                case like_anywhere:
                    lstPredicates.add(builder.like(from.<String>get(attributeName), "%" + value.toString() + "%"));
                    break;
                case like_end:
                    lstPredicates.add(builder.like(from.<String>get(attributeName), "%" + value.toString()));
                    break;
                case ilike_begin:
                    lstPredicates.add(builder.like(builder.lower(from.<String>get(attributeName)), value.toString()
                            .toLowerCase() + "%"));
                    break;
                case ilike_anywhere:
                    lstPredicates.add(builder.like(builder.lower(from.<String>get(attributeName)), "%" + value
                            .toString().toLowerCase() + "%"));
                    break;
                case ilike_end:
                    lstPredicates.add(builder.like(builder.lower(from.<String>get(attributeName)), "%" + value
                            .toString().toLowerCase()));
                    break;
                case is_null:
                    lstPredicates.add(builder.isNull(from.get(attributeName)));
                    break;
                case is_not_null:
                    lstPredicates.add(from.get(attributeName).isNotNull());
                    break;
                case in:
                    lstPredicates.add(from.get(attributeName).in(value));
                    break;
                case not_in:
                    lstPredicates.add(from.get(attributeName).in(value).not());
                    break;
                default:

            }
        }
        Predicate[] predicates = new Predicate[]{};

        predicates = lstPredicates.toArray(predicates);
        return predicates;
    }

    /**
     * This method doesn't work in Hibernate. Hibernate can't reuse predicates
     * and root. Maybe because alias generation conflict.
     * @param records
     * @return
     */
//    private long count(Predicate[] predicates, Root root) {
//        CriteriaBuilder builder = em.getCriteriaBuilder();
//        CriteriaQuery<Long> cq = builder.createQuery(Long.class);
//        cq.select(builder.count(root));
//        if (predicates != null && predicates.length > 0) {
//            cq.where(predicates);
//        }
//        return em.createQuery(cq).getSingleResult();
//    }
    public List<T> insert(List<T> records) {
        try {
            em = factory.createEntityManager();
            em.getTransaction().begin();
            for (T record : records) {
                em.persist(record);
            }
            em.getTransaction().commit();
            em.close();
        } catch (Exception ex) {
            if (em.isOpen()) {
                em.getTransaction().rollback();
                em.close();
            }
            throw ex;
        }
        return records;
    }

    public T insert(T record) {
        try {
            em = factory.createEntityManager();
            em.getTransaction().begin();
            em.persist(record);
            em.getTransaction().commit();
            em.close();
        } catch (Exception ex) {
            if (em.isOpen()) {
                em.getTransaction().rollback();
                em.close();
            }
            throw ex;
        }
        return record;
    }

    public List<T> delete(List<T> records) {
        em = factory.createEntityManager();
        em.getTransaction().begin();
        for (T record : records) {
            em.remove(em.merge(record));
        }
        em.getTransaction().commit();
        em.close();
        return records;
    }

    public T delete(T record) {
        em = factory.createEntityManager();
        em.getTransaction().begin();
        em.remove(record);
        em.getTransaction().commit();
        em.close();
        return record;
    }

    public List<T> update(List<T> records) {
        /**
         * This list will hold new object returned from merge execution. The new
         * object should have higher version than the old one
         */
        List<T> result = new ArrayList<>();
        em = factory.createEntityManager();
        em.getTransaction().begin();
        for (T record : records) {
            result.add(em.merge(record));
        }
        em.getTransaction().commit();
        em.close();
        return result;
    }

    public T update(T record) {
        em = factory.createEntityManager();
        em.getTransaction().begin();
        record = em.merge(record);
        em.getTransaction().commit();
        em.close();
        return record;
    }

    public List<T> initRelationship(List<T> records, String joinTable) {
        em = factory.createEntityManager();
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<T> cq = builder.createQuery(voClass);
        Root<T> root = cq.from(voClass);
        Join theJoin = root.join(joinTable, JoinType.LEFT);
        root.fetch(joinTable, JoinType.LEFT);
        cq.select(root);
        cq.where(root.in(records));
        TypedQuery<T> typedQuery = em.createQuery(cq);
        List<T> result = typedQuery.getResultList();
        em.close();
        return result;
    }

    public T initRelationship(T record, String joinTable) {
        em = factory.createEntityManager();
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<T> cq = builder.createQuery(voClass);
        Root<T> root = cq.from(voClass);
        Join theJoin = root.join(joinTable, JoinType.LEFT);
        root.fetch(joinTable, JoinType.LEFT);
        cq.select(root);
        cq.where(builder.equal(root, record));
        TypedQuery<T> typedQuery = em.createQuery(cq);
        T result = typedQuery.getSingleResult();
        em.close();
        return result;
    }
}
