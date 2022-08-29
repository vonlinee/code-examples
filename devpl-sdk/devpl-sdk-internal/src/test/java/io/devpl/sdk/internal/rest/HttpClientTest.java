package io.devpl.sdk.internal.rest;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;

public class HttpClientTest {


    public static void main(String[] args) {
        HttpClient.Builder builder = HttpClient.newBuilder();
        builder.version(HttpClient.Version.HTTP_2);
        HttpClient httpClient = builder.build();
    }

}
