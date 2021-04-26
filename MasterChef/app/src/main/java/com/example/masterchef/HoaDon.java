package com.example.masterchef;

public class HoaDon{
    private String Hoanthanh,SoLuong,ID,Date, PhucVu;
    private int HoaDonSo,Table,Trangthai;
    public HoaDon(){
        this.HoaDonSo = 0;
        this.Trangthai = 0;
        this.Table = 0;
        this.ID = "";
        this.Hoanthanh = "";
        this.SoLuong = "";
        this.Date = "";
        this.PhucVu = "";
    }
    public HoaDon(int trangthai,int table,int Hoadonso,String ID,String Hoanthanh,String Soluong,String Date, String PhucVu){
        this.Trangthai = trangthai;
        this.Table = table;
        this.HoaDonSo = Hoadonso;
        this.ID = ID;
        this.Hoanthanh = Hoanthanh;
        this.SoLuong = Soluong;
        this.Date = Date;
        this.PhucVu = PhucVu;
    }
    public void setID(String a){
        ID = a;
    }
    public String getID(){return ID;}
    public void setDate(String a){
        Date = a;
    }
    public String getDate(){return Date;}

    public void setSoLuong(String a){
        SoLuong = a;
    }
    public String getSoLuong(){return SoLuong;}
    public void setHoanthanh(String a){
        Hoanthanh = a;
    }
    public String getHoanthanh(){return Hoanthanh;}
    public void setTrangthai(int a){
        Trangthai = a;
    }
    public int getTrangthai(){return Trangthai;}
    public void setTable(int a){
        Table = a;
    }
    public int getTable(){return Table;}
    public void setHoaDonSo(int a){
        HoaDonSo = a;
    }
    public int getHoaDonSo(){return HoaDonSo;}

    public String getPhucVu() {return PhucVu;}
    public void setPhucVu(String pv) {
        this.PhucVu = pv;
    }
}
