package com.example.masterchef.ui.manager.Managemenu;

import java.util.List;

public class category {
    private String name;
    private List<Menu> menus;

    public category(String name, List<Menu> menus) {
        this.name = name;
        this.menus = menus;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMenus(List<Menu> menus) {
        this.menus = menus;
    }

    public String getName() {
        return name;
    }

    public List<Menu> getMenus() {
        return menus;
    }
}
