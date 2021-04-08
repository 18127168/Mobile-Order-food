package com.example.masterchef;

class HoaDon{
    private String Trangthai;
    private int HoaDonSo,Table,ID;
    public HoaDon(){
        this.HoaDonSo = 0;
        this.Trangthai = "";
        this.Table = 0;
        this.ID = 0;
    }
    public HoaDon(String trangthai,int table,int Hoadonso,int ID){
        this.Trangthai = trangthai;
        this.Table = table;
        this.HoaDonSo = Hoadonso;
        this.ID= ID;
    }
    public void setID(int a){
        ID = a;
    }
    public int getID(){return ID;}
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
