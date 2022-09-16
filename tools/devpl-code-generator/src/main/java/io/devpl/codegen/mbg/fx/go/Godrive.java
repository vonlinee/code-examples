/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.devpl.codegen.mbg.fx.go;

import com.sun.jna.Library;
import com.sun.jna.Native;

/**
 * @author blinderjay
 */

public interface Godrive extends Library {

    @SuppressWarnings("deprecation")
    Godrive INSTANCE = Native.loadLibrary("quickgoogle", Godrive.class);

    String goauth();
}

