package io.maker.ext.mybatis;

import java.util.ArrayList;

public interface MyBatisMapper {
    void getList(String song, ArrayList<String> ids);
}
