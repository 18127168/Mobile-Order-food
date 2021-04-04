package com.example.masterchef.ui.manager.Managemenu;
public class Menu {
    private int resourceid;
    private String Name;

    public Menu(int resourceid, String name) {
        this.resourceid = resourceid;
        this.Name = name;
    }

    public int getResourceid() {
        return resourceid;
    }

    public String getName() {
        return Name;
    }
}
