package com.example.masterchef.ui.manager.Managestaff;
public class staff {
    private String stt;
    private String name;
    private String type;

    public staff(String stt, String name, String type){
        this.stt=stt;
        this.name=name;
        this.type=type;
    }

    public String getStt() {
        return stt;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }
}
