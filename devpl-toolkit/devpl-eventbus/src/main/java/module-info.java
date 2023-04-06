module devpl.eventbus {
    requires java.logging;
    opens org.greenrobot.eventbus;
    opens org.greenrobot.eventbus.extension;
    exports org.greenrobot.eventbus;
}