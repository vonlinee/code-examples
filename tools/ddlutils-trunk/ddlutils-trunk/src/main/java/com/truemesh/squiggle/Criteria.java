package com.truemesh.squiggle;

import com.truemesh.squiggle.output.Output;
import com.truemesh.squiggle.output.Outputable;
import com.truemesh.squiggle.output.ToStringer;

/**
 * @author <a href="joe@truemesh.com">Joe Walnes</a>
 */
public abstract class Criteria implements Outputable {

    @Override
    public abstract void write(Output out);

    @Override
    public String toString() {
        return ToStringer.toString(this);
    }

    protected String quote(String s) {
        if (s == null) return "null";
        StringBuilder str = new StringBuilder();
        str.append('\'');
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '\\'
                    || s.charAt(i) == '\"'
                    || s.charAt(i) == '\'') {
                str.append('\\');
            }
            str.append(s.charAt(i));
        }
        str.append('\'');
        return str.toString();
    }
}
