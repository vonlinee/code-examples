public class TEst {
    public static void main(String[] args) {
        new Son();
    }

}

class GrandPa {

    public GrandPa() {
        System.out.println(this);
    }
}

class Father extends GrandPa {
    public Father() {
    }
}

class Son extends Father {
    public Son() {
        System.out.println("Son => " + this);
    }
}