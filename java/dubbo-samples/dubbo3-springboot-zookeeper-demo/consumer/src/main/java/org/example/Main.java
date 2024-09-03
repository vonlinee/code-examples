package org.example;

import org.apache.zookeeper.server.auth.DigestAuthenticationProvider;

import java.security.NoSuchAlgorithmException;

public class Main {

    public static void main(String[] args) {
        try {
            System.out.println(DigestAuthenticationProvider.generateDigest("super:superpw"));
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
