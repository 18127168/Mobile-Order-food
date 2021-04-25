package com.example.masterchef.ui.manager.Managestaff;
public class staff {
<<<<<<< HEAD
    private int idNhanVien;
    private String Name;
    private String Type;
    public staff(){}
    public staff(int ID, String Name, String Type){
        this.idNhanVien=ID;//hay de tao database tu firebase ms hinh nhu co van de cai ID, khong biet luon ong, sao toi cho do null//thoi de tui tao cai ms thu o :(
        this.Name=Name;
        this.Type=Type;
=======
    private String stt;
    private String name;
    private String type;

    public staff(String stt, String name, String type){
        this.stt=stt;
        this.name=name;
        this.type=type;
>>>>>>> f08fcb12db275c83128d0ad57a59ec742039f1d0
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
