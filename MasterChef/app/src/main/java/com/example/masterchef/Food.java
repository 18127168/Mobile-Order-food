package com.example.masterchef;

public class Food{
    private String FlagName;
    private String Tenmon;
    private int TimeToFinish,ID,giatien;

    public Food(){
        this.FlagName = "";
        this.Tenmon = "";
        this.TimeToFinish = 0;
        this.ID = 0;
        this.giatien = 0;
    }

    public Food(String flagname,String tenmon,int timetofinish,int ID,int giatien){
        this.FlagName = flagname;
        this.Tenmon = tenmon;
        this.TimeToFinish = timetofinish;
        this.ID= ID;
        this.giatien = giatien;
    }

    public void setgiatien(int a){
        giatien = a;
    }
    public int getGiatien(){return giatien;}
    public void setID(int a){
        ID = a;
    }
    public int getID(){return ID;}
    public void setFlagName(String a){
        FlagName = a;
    }
    public String getFlagName(){return FlagName;}
    public void setTenmon(String a){
    Tenmon = a;
    }
    public String getTenmon(){return Tenmon;}
    public void setTimeToFinish(int a){
        TimeToFinish = a;
    }
    public int getTimeToFinish(){return TimeToFinish;}
}
