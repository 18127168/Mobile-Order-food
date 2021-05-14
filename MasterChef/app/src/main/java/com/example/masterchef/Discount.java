package com.example.masterchef;

public class Discount {
    String Makhuyenmai;
    String Ngaybatdau;
    String ngayketthuc;
    int Mota;
    public Discount(){
    }

    public void setMakhuyenmai(String ma) {
        this.Makhuyenmai = ma;
    }

    public void setNgaybatdau(String ngaybatdau) {
        this.Ngaybatdau = ngaybatdau;
    }

    public void setNgayketthuc(String ngayketthuc) {
        this.ngayketthuc = ngayketthuc;
    }

    public void setMota(int mota) {
        this.Mota = mota;
    }

    public Discount(String ma, String ngaybatdau, int mota, String ngayketthuc) {
        this.Makhuyenmai = ma;
        this.Ngaybatdau=ngaybatdau;
        this.ngayketthuc=ngayketthuc;
        this.Mota = mota;
    }

    public String getMakhuyenmai() {
        return this.Makhuyenmai;
    }

    public String getNgaybatdau() {
        return this.Ngaybatdau;
    }

    public String getNgayketthuc() {
        return this.ngayketthuc;
    }

    public int getMota() {
        return this.Mota;
    }
}