package com.example.masterchef;
import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class User {
    public String user,pass,hoten,ngaysinh,cmnd,sdt,loai,dantoc,ngayvao,diachi;
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
    }
    public User(String user,String pass){
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

    }
}