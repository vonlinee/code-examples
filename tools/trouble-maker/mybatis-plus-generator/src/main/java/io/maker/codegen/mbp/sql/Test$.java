package io.maker.codegen.mbp.sql;

public class Test$ {

    public static void main(String[] args) {
        SqlStatement statemnt = SqlStatement.select()
                                            .columns("NAME", "AGE", "USERNAME")
                                            .from("orders", "T")
                                            .build();


    }
}
