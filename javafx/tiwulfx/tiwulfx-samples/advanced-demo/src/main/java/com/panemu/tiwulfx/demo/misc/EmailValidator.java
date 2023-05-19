package com.panemu.tiwulfx.demo.misc;

import com.panemu.tiwulfx.common.Validator;

public class EmailValidator implements Validator<String> {

    static final String ATOM = "[a-z0-9!#$%&'*+/=?^_`{|}~-]";
    static final String DOMAIN = "(" + ATOM + "+(\\." + ATOM + "+)+";
    static final String IP_DOMAIN = "\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\]";
    static final String PATTERN =
            "^" + ATOM + "+(\\." + ATOM + "+)*@"
            + DOMAIN
            + "|"
            + IP_DOMAIN
            + ")$";

    @Override
    public String validate(String value) {
        if (value.matches(PATTERN)) {
            return null;
        }
        return "Please provide a valid email. Ex: john.doe@panemu.com";
    }
}
