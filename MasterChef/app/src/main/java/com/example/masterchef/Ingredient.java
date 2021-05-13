package com.example.masterchef;

public class Ingredient {
    private String FlagName, Ten;
    private int IDNguyenLieu, Soluongtonkho, Gia;


    public void setIdnguyenlieu(int a){this.IDNguyenLieu = a;}
    public int getIdnguyenlieu(){return this.IDNguyenLieu;}
    public void setSoluongtonkho(int a){this.Soluongtonkho = a;}
    public int getSoluongtonkho(){return Soluongtonkho;}
    public void setGia(int a){ Gia = a; }
    public int getGia(){return Gia;}
    public void setFlagName(String a){ FlagName = a; }
    public String getFlagName(){return FlagName;}
    public void setTen(String a){ Ten = a; }
    public String getTen(){return Ten;}
}