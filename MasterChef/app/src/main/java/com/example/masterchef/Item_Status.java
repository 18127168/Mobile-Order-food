package com.example.masterchef;

public class Item_Status {
    private int food_id, served, quantity, bill;

    public Item_Status() {
        this.food_id = 0;
        this.served = 0;
        this.quantity = 0;
        this.bill = 0;
    }

    public void setFood_id(int food_id) {
        this.food_id = food_id;
    }

    public void setServed(int served) {
        this.served = served;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getFood_id() {
        return food_id;
    }

    public int getServed() {
        return served;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getBill() {
        return bill;
    }

    public void setBill(int bill) {
        this.bill = bill;
    }
}
