package com.example.masterchef;

public class Food{
    private String FlagName;
    private String Tenmon;
    private int TimeToFinish,ID,giatien;
    private String Idnguyenlieu,soluongnguyenlieu;

    public Food(){
        this.FlagName = "";
        this.Tenmon = "";
        this.TimeToFinish = 0;
        this.ID = 0;
        this.giatien = 0;
        this.Idnguyenlieu = "" ;
        this.soluongnguyenlieu = "";
    }

    public Food(String flagname,String tenmon,int timetofinish,int ID,int giatien,String idnguyenlieu,String soluongnguyenlieu){
        this.FlagName = flagname;
        this.Tenmon = tenmon;
        this.TimeToFinish = timetofinish;
        this.ID= ID;
        this.giatien = giatien;
        this.Idnguyenlieu = idnguyenlieu;
        this.soluongnguyenlieu = soluongnguyenlieu;
    }
    public void setIdnguyenlieu(String a){this.Idnguyenlieu = a;}
    public String getIdnguyenlieu(){return Idnguyenlieu;}
    public void setsoluongnguyenlieu(String a){this.soluongnguyenlieu = a;}
    public String getsoluongnguyenlieu(){return soluongnguyenlieu;}
    public void setgiatien(int a){ giatien = a; }
    public int getGiatien(){return giatien;}
    public void setID(int a){ ID = a; }
    public int getID(){return ID;}
    public void setFlagName(String a){ FlagName = a; }
    public String getFlagName(){return FlagName;}
    public void setTenmon(String a){ Tenmon = a; }
    public String getTenmon(){return Tenmon;}
    public void setTimeToFinish(int a){ TimeToFinish = a; }
    public int getTimeToFinish(){return TimeToFinish;}
}