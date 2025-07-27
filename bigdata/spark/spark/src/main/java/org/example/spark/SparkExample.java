package org.example.spark;

import org.apache.spark.sql.SparkSession;

public class SparkExample {

    public static void main(String[] args) {
        SparkSession session = SparkSession.builder()
                .appName("Spark Example")
                .getOrCreate();

    }
}
