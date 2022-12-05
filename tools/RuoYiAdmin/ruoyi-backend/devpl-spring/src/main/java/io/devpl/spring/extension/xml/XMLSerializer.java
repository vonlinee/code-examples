package io.devpl.spring.extension.xml;

import java.io.File;

public interface XMLSerializer<T> {

    T serialize();

    File deserialize(T dataModel);
}
