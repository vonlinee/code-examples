package io.devpl.codegen.fxui.common.go;

// import com.sun.jna.Structure;

// public class GoString extends Structure {
public class GoString  {

    public String str;
    public long length;

    public GoString() {
    }

    public GoString(String str) {
        this.str = str;
        this.length = str.length();
    }

    // @Override
    // protected List<String> getFieldOrder() {
    //     List<String> fields = new ArrayList<>();
    //     fields.add("str");
    //     fields.add("length");
    //     return fields;
    // }
    //
    // public static class ByValue extends GoString implements Structure.ByValue {
    //
    //     public ByValue() {
    //     }
    //
    //     public ByValue(String str) {
    //         super(str);
    //     }
    // }
    //
    // public static class ByReference extends GoString implements Structure.ByReference {
    //
    //     public ByReference() {
    //     }
    //
    //     public ByReference(String str) {
    //         super(str);
    //     }
    // }
}