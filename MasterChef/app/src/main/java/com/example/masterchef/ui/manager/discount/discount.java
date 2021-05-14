package com.example.masterchef.ui.manager.discount;
public class discount {
    String Makhuyenmai;
    String Ngaybatdau;
    String ngayketthuc;
    int Mota;
    public discount(){
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

    public discount(String ma, String ngaybatdau, int mota, String ngayketthuc) {
        this.Makhuyenmai = ma;
        this.Ngaybatdau=ngaybatdau;
        this.ngayketthuc=ngayketthuc;
        this.Mota = mota;
    }
    public discount(String ma,int mota,String ngaybatdau,String ngayketthuc){
        this.Makhuyenmai = ma;
        this.Ngaybatdau=ngaybatdau;
        this.ngayketthuc=ngayketthuc;
        this.Mota = mota;

    }
    public String getMakhuyenmai() {
        return Makhuyenmai;
    }

    public String getNgaybatdau() {
        return Ngaybatdau;
    }

    public String getNgayketthuc() {
        return ngayketthuc;
    }

    public int getMota() {
        return Mota;
    }
}