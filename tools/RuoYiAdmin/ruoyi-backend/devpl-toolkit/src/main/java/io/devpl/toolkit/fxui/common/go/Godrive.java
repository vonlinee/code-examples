package io.devpl.toolkit.fxui.common.go;

import com.sun.jna.Library;
import com.sun.jna.Native;

public interface Godrive extends Library {

    @SuppressWarnings("deprecation")
    Godrive INSTANCE = (Godrive) Native.loadLibrary("quickgoogle", Godrive.class);

    String goauth();
}

