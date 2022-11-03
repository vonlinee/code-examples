package io.devpl.sdk;

import io.devpl.sdk.http.Browser;

public class TestHttpClient {


    public static void main(String[] args) {

        Browser browser = new Browser();


        browser.send("http://httpbin.org/get", null, null, null);
    }
}
