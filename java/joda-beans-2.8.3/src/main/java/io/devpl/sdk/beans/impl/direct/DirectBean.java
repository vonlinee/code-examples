package io.devpl.sdk.beans.impl.direct;

import io.devpl.sdk.beans.Bean;

/**
 * A bean implementation designed for use by the code generator.
 * <p>
 * It used to be mandatory for code generated beans to extend this class.
 * Now, beans can simply implement the {@code Bean} interface.
 */
public abstract class DirectBean implements Bean {

    @Override
    public String getName() {
        return "DirectBean[]";
    }

    @Override
    public abstract DirectBean clone();

}
