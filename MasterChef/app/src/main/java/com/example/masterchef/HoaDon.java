package com.example.masterchef;

public class HoaDon{
    private String Hoanthanh, SoLuong, ID,Date, notes, PhucVu;
    private int Table, HoaDonSo, Trangthai;
    boolean ThanhToan;

    public HoaDon(){
        this.HoaDonSo = 0;
        this.Trangthai = 0;
        this.Table = -1;
        this.ID = "";
        this.notes = "";
        this.Hoanthanh = "";
        this.SoLuong = "";
        this.Date = "";
        this.PhucVu = "";
        this.ThanhToan = false;
    }

    public HoaDon(int trangthai,int table,int Hoadonso,String ID,String Hoanthanh,String Soluong,String Date, String note, String PhucVu){
        this.Trangthai = trangthai;
        this.Table = table;
        this.HoaDonSo = Hoadonso;
        this.ID = ID;
        this.notes = note;
        this.PhucVu = PhucVu;
        this.Hoanthanh = Hoanthanh;
        this.SoLuong = Soluong;
        this.Date = Date;
        this.ThanhToan = false;
    }

    public void setID(String a){ ID = a; }
    public void setDate(String a){ Date = a; }
    public void setSoLuong(String a){ SoLuong = a; }
    public void setHoanthanh(String a){ Hoanthanh = a; }
    public void setNotes(String a) { notes = a; }
    public void setTrangthai(int a){ Trangthai = a; }
    public void setTable(int a){ Table = a; }
    public void setPhucVu(String phucVu) { PhucVu = phucVu; }
    public void setHoaDonSo(int a){ HoaDonSo = a; }
    public void setThanhToan(boolean a) { ThanhToan = a; }

    public String getID(){return ID;}
    public String getSoLuong(){return SoLuong;}
    public String getNotes() {return notes; }
    public String getDate(){return Date;}
    public String getHoanthanh(){return Hoanthanh;}
    public String getPhucVu() { return PhucVu; }
    public int getTrangthai(){return Trangthai;}
    public int getTable(){return Table;}
    public int getHoaDonSo(){ return HoaDonSo; }
    public boolean getThanhToan() {return ThanhToan; };
}