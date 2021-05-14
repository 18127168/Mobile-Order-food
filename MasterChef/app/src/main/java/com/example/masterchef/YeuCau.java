package com.example.masterchef;

import com.google.firebase.database.DatabaseReference;

public class YeuCau {
    private int status, table;
    private String date;
    private DatabaseReference ref;

    public YeuCau(){
        this.status = -1;
        this.table = -1;
        this.date = "";
    }



    public void setRef(DatabaseReference s) {
        this.ref = s;
    }
    public DatabaseReference getRef() {
        return this.ref;
    }

    public void setStatus(int a){this.status = a;}
    public int getStatus(){return status;}
    public void setTable(int a){this.table = a;}
    public int getTable(){return table;}
    public void setdate(String a){ this.date = a; }
    public String getdate(){return date;}
}