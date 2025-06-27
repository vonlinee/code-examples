package org.sqltemplate;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.HashBasedTable;
import org.apache.commons.collections4.set.ListOrderedSet;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Record1;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.SelectQuery;
import org.jooq.SelectSelectStep;
import org.jooq.impl.DSL;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@EntityScan
public class Test {

    @JsonProperty(namespace = "")
    private String name;

    public static void main(String[] args) {

        DSLContext context = DSL.using(SQLDialect.DEFAULT);

        SelectQuery<Record> records = context.selectQuery();


        SelectSelectStep<Record1<Integer>> record1s = context.selectOne();

        Result<Record1<Integer>> result = context.selectCount().fetch();

        // Result<Record> result = context.selectFrom("t").where("id = 1").fetch();

        System.out.println(result);

        HashBasedTable.create();

        ListOrderedSet<String> set;

        HashBasedTable<Object, Object, Object> table = HashBasedTable.create();


    }
}
