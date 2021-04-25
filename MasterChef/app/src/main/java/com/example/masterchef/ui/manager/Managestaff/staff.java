package com.example.masterchef.ui.manager.Managestaff;
public class staff {
    private int idNhanVien;
    private String Name;
    private String Type;
    public staff(){}
    public staff(int ID, String Name, String Type){
        this.idNhanVien=ID;//hay de tao database tu firebase ms hinh nhu co van de cai ID, khong biet luon ong, sao toi cho do null//thoi de tui tao cai ms thu o :(
        this.Name=Name;
        this.Type=Type;
    }
//firebase t dang ep kiieu id la string // t dang thu dua no ve int -.-
    public int getIdNhanVien() {
        return idNhanVien;
    }
    public void setIdNhanVien(int id) { this.idNhanVien = id;
    }

    public String getName() {
        return Name;
    }

    public String getType() {
        return Type;
    }
}
