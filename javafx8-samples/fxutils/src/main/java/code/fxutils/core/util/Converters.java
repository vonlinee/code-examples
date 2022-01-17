package code.fxutils.core.util;

/**
 * @author Evarb
 * @date 2022/1/17
 * @description
 **/
public class Converters {

    @FunctionalInterface
    public interface Converter<F, T> {
        T run(F from);
    }




}