package cn.example.graphql.createschema;

import graphql.GraphQL;
import graphql.schema.GraphQLSchema;

import java.io.File;

public class Main {

    public static void main(String[] args) {



        SchemaCreator schemaCreator = new SchemaCreator();
        File schema = null;
        GraphQLSchema graphQLSchema = schemaCreator.create(schema);
        GraphQL graphQL = GraphQL.newGraphQL(graphQLSchema).build();

        Object result = graphQL.execute("query").getData();
        System.out.println(result);
    }
}
