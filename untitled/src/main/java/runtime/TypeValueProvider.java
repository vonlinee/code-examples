package runtime;

import java.util.List;

public interface TypeValueProvider<T> {

    List<?> provide();
}
