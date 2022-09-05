package io.devpl.sdk.internal.rest;

import io.devpl.sdk.internal.http.Requests;

import java.io.IOException;
import java.net.http.HttpRequest;

public class HttpClientTest {


    public static void main(String[] args) throws IOException, InterruptedException {

        Requests requests = new Requests();

        HttpRequest request = requests.buildRequest("http://httpbin.org/get", "GET", null, null);

        Requests http = new Requests();

    }

}
