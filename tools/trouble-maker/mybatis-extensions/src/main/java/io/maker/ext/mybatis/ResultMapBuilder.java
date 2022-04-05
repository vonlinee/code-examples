package io.maker.ext.mybatis;

import org.apache.ibatis.javassist.bytecode.Descriptor;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.session.Configuration;

import java.util.ArrayList;

public class ResultMapBuilder {

    public ResultMapBuilder(Configuration configuration) {
        ResultMap.Builder builder = new ResultMap.Builder(configuration, "", String.class, new ArrayList<>());
    }

    public ResultMapping mapping(Configuration configuration) {
        return null;
    }
}
