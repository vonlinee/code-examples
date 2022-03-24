package cn.example.graphql.createschema;

import graphql.schema.GraphQLSchema;
import graphql.schema.StaticDataFetcher;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;

import java.io.File;

public final class SchemaCreator {

    SchemaGenerator schemaGenerator = new SchemaGenerator();
    SchemaParser schemaParser = new SchemaParser();

    public GraphQLSchema create(File graphqls) {
        TypeDefinitionRegistry typeRegistry = schemaParser.parse(graphqls);
        RuntimeWiring wiring = buildRuntimeWiring();
        return schemaGenerator.makeExecutableSchema(typeRegistry, wiring);
    }

    public GraphQLSchema create(String schema) {
        TypeDefinitionRegistry typeRegistry = schemaParser.parse(schema);
        RuntimeWiring wiring = buildRuntimeWiring();
        return schemaGenerator.makeExecutableSchema(typeRegistry, wiring);
    }

    private RuntimeWiring buildRuntimeWiring() {
        return RuntimeWiring.newRuntimeWiring()
                .type("Result", typeWiring -> typeWiring
                        //定义Field
                        .dataFetcher("name", new StaticDataFetcher("AAA"))
                ).build();
    }
}