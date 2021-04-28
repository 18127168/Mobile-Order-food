package com.example.masterchef;

public class HoaDon{
    private String Hoanthanh,SoLuong,ID,Date,notes,Table, PhucVu;
    private int HoaDonSo,Trangthai;
    public HoaDon(){
        this.HoaDonSo = 0;
        this.Trangthai = 0;
        this.Table = "";
        this.ID = "";
        this.Hoanthanh = "";
        this.SoLuong = "";
        this.Date = "";
        this.notes = "";
        this.PhucVu = "";
    }
    public HoaDon(int trangthai,String table,int Hoadonso,String ID,String Hoanthanh,String Soluong,String Date,String notes, String PhucVu){
        this.Trangthai = trangthai;
        this.Table = table;
        this.HoaDonSo = Hoadonso;
        this.ID = ID;
        this.Hoanthanh = Hoanthanh;
        this.SoLuong = Soluong;
        this.Date = Date;
        this.notes = notes;
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
    public void setnotes(String a){
        notes = a;
    }
    public String getnotes(){return notes;}

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
    public void setTable(String a){
        Table = a;
    }
    public String getTable(){return Table;}
    public void setHoaDonSo(int a){
        HoaDonSo = a;
    }
    public int getHoaDonSo(){return HoaDonSo;}
    public String getPhucVu() {return PhucVu;}
    public void setPhucVu(String pv) {
        this.PhucVu = pv;
    }
}
