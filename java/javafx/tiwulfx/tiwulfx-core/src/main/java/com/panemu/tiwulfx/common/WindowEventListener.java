/*
 * Copyright (C) 2018 Panemu.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301  USA
 */
package com.panemu.tiwulfx.common;

import javafx.stage.WindowEvent;

/**
 * This class is used in as parameter for {@link com.panemu.tiwulfx.common.TiwulFXUtil#attachWindowListener(javafx.scene.Node, com.panemu.tiwulfx.common.WindowEventListener) attachWindowListener}.
 * 
 * @author amrul
 * @see TiwulFXUtil#attachWindowListener(javafx.scene.Node, com.panemu.tiwulfx.common.WindowEventListener) 
 */
public abstract class WindowEventListener {
	public void onWindowCloseRequest(WindowEvent event){};
	public void onWindowShowing(WindowEvent event){};
	public void onWindowShown(WindowEvent event){};
	public void onWindowHiding(WindowEvent event){};
	public void onWindowHidden(WindowEvent event){};
}
