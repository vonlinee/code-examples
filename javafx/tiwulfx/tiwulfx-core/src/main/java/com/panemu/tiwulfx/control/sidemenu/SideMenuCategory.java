/*
 * License GNU LGPL
 * Copyright (C) 2013 Amrullah .
 */
package com.panemu.tiwulfx.control.sidemenu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Amrullah
 */
public class SideMenuCategory extends SideMenuItem {

    private List<SideMenuItem> lstMenuItem = new ArrayList<>();

    public SideMenuCategory(String imageStyle, String label) {
        super(imageStyle, label, null);
    }

    public void addMainMenuItem(SideMenuItem... menuItem) {
        Collections.addAll(lstMenuItem, menuItem);
    }

    public List<SideMenuItem> getMenuItems() {
        return lstMenuItem;
    }
}
