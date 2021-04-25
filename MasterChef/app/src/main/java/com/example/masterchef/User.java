package com.example.masterchef;
import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class User {
    public String user,pass,hoten,ngaysinh,cmnd,sdt,loai,dantoc,ngayvao,diachi;
    public int ban;
    public User() {
        this.user = "";
        this.pass = "";
        this.hoten ="";
        this.ngaysinh = "";
        this.cmnd = "";
        this.sdt ="";
        this.loai = "";
        this.dantoc = "";
        this.ngayvao ="";
        this.diachi = "";
        this.ban = -1;
    }
    public User(String user,String pass){ // quanly
        this.user = user;
        this.pass = pass;
        this.hoten ="";
        this.ngaysinh = "";
        this.cmnd = "";
        this.sdt ="";
        this.loai = "NhanVien";
        this.dantoc = "";
        this.ngayvao ="";
        this.diachi = "";
        this.ban = -1;
    }

    public User(String user,String pass,String hoten,String ngaysinh,String cmnd,String sdt,String loai,String dantoc,String ngayvao,String diachi,int ban){ // quanly
        this.user = user;
        this.pass = pass;
        this.hoten =hoten;
        this.ngaysinh = ngaysinh;
        this.cmnd = cmnd;
        this.sdt =sdt;
        this.loai = loai;
        this.dantoc = dantoc;
        this.ngayvao =ngayvao;
        this.diachi = diachi;
        this.ban = ban;
    }
    public User(String user,String pass,int ban){ // khachhang
        this.user = user;
        this.pass = pass;
        this.hoten ="";
        this.ngaysinh = "";
        this.cmnd = "";
        this.sdt ="";
        this.loai = "QuanLy";
        this.dantoc = "";
        this.ngayvao ="";
        this.diachi = "";
        this.ban = ban;

    }
    public User(String user,String pass,String loai){
        this.user = user;
        this.pass = pass;
        this.hoten ="";
        this.ngaysinh = "";
        this.cmnd = "";
        this.sdt ="";
        this.loai = loai;
        this.dantoc = "";
        this.ngayvao ="";
        this.diachi = "";
        this.ban = -1;
    }

    public String getUser() {
        return user;
    }

    public String getPass() {
        return pass;
    }

    public String getHoten() {
        return hoten;
    }

    public String getNgaysinh() {
        return ngaysinh;
    }

    public String getCmnd() {
        return cmnd;
    }

    public String getSdt() {
        return sdt;
    }

    public String getLoai() {
        return loai;
    }

    public String getDantoc() {
        return dantoc;
    }

    public String getNgayvao() {
        return ngayvao;
    }

    public String getDiachi() {
        return diachi;
    }

    public int getBan() {
        return ban;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public void setNgaysinh(String ngaysinh) {
        this.ngaysinh = ngaysinh;
    }

    public void setCmnd(String cmnd) {
        this.cmnd = cmnd;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public void setLoai(String loai) {
        this.loai = loai;
    }

    public void setDantoc(String dantoc) {
        this.dantoc = dantoc;
    }

    public void setNgayvao(String ngayvao) {
        this.ngayvao = ngayvao;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public void setBan(int ban) {
        this.ban = ban;
    }
}