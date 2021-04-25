package com.example.masterchef;

public class HoaDon{
    private String Trangthai,Hoanthanh,SoLuong,ID;
    private int HoaDonSo,Table;
    public HoaDon(){
        this.HoaDonSo = 0;
        this.Trangthai = "";
        this.Table = 0;
        this.ID = "";
        this.Hoanthanh = "";
        this.SoLuong = "";
    }
    public HoaDon(String trangthai,int table,int Hoadonso,String ID,String Hoanthanh,String Soluong){
        this.Trangthai = trangthai;
        this.Table = table;
        this.HoaDonSo = Hoadonso;
        this.ID = ID;
        this.Hoanthanh = Hoanthanh;
        this.SoLuong = Soluong;
    }
    public void setID(String a){
        ID = a;
    }
    public String getID(){return ID;}

    public void setSoLuong(String a){
        SoLuong = a;
    }
    public String getSoLuong(){return SoLuong;}
    public void setHoanthanh(String a){
        Hoanthanh = a;
    }
    public String getHoanthanh(){return Hoanthanh;}
    public void setTrangthai(String a){
        Trangthai = a;
    }
    public String getTrangthai(){return Trangthai;}
    public void setTable(int a){
        Table = a;
    }
    public int getTable(){return Table;}
    public void setHoaDonSo(int a){
        HoaDonSo = a;
    }
    public int getHoaDonSo(){return HoaDonSo;}
}
