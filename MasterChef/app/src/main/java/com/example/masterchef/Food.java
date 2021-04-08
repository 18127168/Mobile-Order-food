package com.example.masterchef;

class Food{
    private String FlagName;
    private String Tenmon,Trangthai;
    private int Table,TimeToFinish,HoaDonSo;
    public Food(){
        this.FlagName = "";
        this.Table = 0;
        this.Tenmon = "";
        this.Trangthai = "";
        this.TimeToFinish = 0;
        this.HoaDonSo = 0;
    }

    public Food(String flagname,int table,String tenmon,String trangthai,int timetofinish,int donthux){
        this.FlagName = flagname;
        this.Table = table;
        this.Tenmon = tenmon;
        this.Trangthai = trangthai;
        this.TimeToFinish = timetofinish;
        this.HoaDonSo = donthux;
    }

    public void setFlagName(String a){
        FlagName = a;
    }
    public String getFlagName(){return FlagName;}
    public void setTable(int a){
        Table=a;
    }
    public int getTable(){return Table;}
    public void setTenmon(String a){
    Tenmon = a;
    }
    public String getTenmon(){return Tenmon;}
    public void setTrangthai(String a){
        Trangthai = a;
    }
    public String getTrangthai(){return Trangthai;}
    public void setTimeToFinish(int a){
        TimeToFinish = a;
    }
    public int getTimeToFinish(){return TimeToFinish;}
    public void setHoaDonSo(int a){
        HoaDonSo = a;
    }
    public int getHoaDonSo(){return HoaDonSo;}
}
