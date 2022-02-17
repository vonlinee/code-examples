package code.magicode.generator.lang.type;

public class ReferenceType extends JavaDataType {
    @Override
    public String name() {
        return null;
    }

    @Override
    boolean isPrimitive() {
        return false;
    }

    @Override
    boolean isReference() {
        return false;
    }
}
