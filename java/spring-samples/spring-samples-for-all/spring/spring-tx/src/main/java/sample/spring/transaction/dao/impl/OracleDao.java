package sample.spring.transaction.dao.impl;

import sample.spring.transaction.bean.Flow;

import java.util.List;

public interface OracleDao {
    List<Flow> get();
}
