package com.example.masterchef;

public class ServeItem {
    private int table, food, bill, quantity;

    public ServeItem() {
        this.table = 0;
        this.food = 0;
        this.bill = 0;
        this.quantity = 0;
    }

    public ServeItem(int table, int food, int bill, int quantity) {
        this.table = table;
        this.food = food;
        this.bill = bill;
        this.quantity = quantity;
    }

    public void setTable(int table) {
        this.table = table;
    }
    public int getTable() {
        return this.table;
    }

    public void setFood(int f) {
        this.food = f;
    }
    public int getFood() {
        return this.food;
    }

    public void setBill(int b) {
        this.bill = b;
    }
    public int getBill() {
        return this.bill;
    }

    public  void setQuantity(int q) {
        this.quantity = q;
    }
    public int getQuantity() {
        return this.quantity;
    }
}
