package com.example.masterchef;

public class Tables {
    int seats, table_id;

    public  Tables() {
        table_id = 0;
        seats = 0;
    }

    public Tables(int table_id, int seats) {
        this.table_id = table_id;
        this.seats = seats;
    }

    public void setId(int id) { this.table_id = id; }
    public void setSeats(int seats) {
        this.seats = seats;
    }

    public  int getId() {
        return  this.table_id;
    }
    public  int getSeats() {
        return  this.seats;
    }
}
