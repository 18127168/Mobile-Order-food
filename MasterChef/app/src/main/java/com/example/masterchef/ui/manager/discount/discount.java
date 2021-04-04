package com.example.masterchef.ui.manager.discount;
public class discount {
    String STT;
    String Ma;
    String Ngayapdung;
    String Mota;

    public discount(String STT, String ma, String ngayapdung, String mota) {
        this.STT = STT;
        this.Ma = ma;
        this.Ngayapdung = ngayapdung;
        this.Mota = mota;
    }

    public String getSTT() {
        return STT;
    }

    public String getMa() {
        return Ma;
    }

    public String getNgayapdung() {
        return Ngayapdung;
    }

    public String getMota() {
        return Mota;
    }
}