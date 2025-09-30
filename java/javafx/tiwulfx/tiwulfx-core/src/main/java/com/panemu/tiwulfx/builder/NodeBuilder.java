package com.panemu.tiwulfx.builder;

import javafx.scene.Node;

public interface NodeBuilder<T extends Node> {

    T build();
}
