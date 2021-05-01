package com.example.masterchef;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

public class SelectedFood extends Application {

    public static class Food{
        int IdFood;
        int quantity;
        String note;

        public Food() {
            IdFood = 0;
            quantity = 0;
            note = "";
        }


        public Food(int Food, int num) {
            IdFood = Food;
            quantity = num;
            note = "";
        }

        public int getQuantity() { return quantity; }

        public int getIdFood(){ return IdFood; }

        public String getNote() { return note; }

        public void increaseQuantity() { this.quantity += 1; }

        public void decreaseQuantity() { if (this.quantity > 0)     this.quantity -= 1; }
    }

    public static List<Food> Foods = new ArrayList<>();

    public static void clearSelectedFood(){
        Foods = new ArrayList<>();
    }

    public static void addSelectedFood(int food) {
        boolean duplicate = false;

        for (int i = 0; i < Foods.size(); i++){
            if (Foods.get(i).IdFood == food) {
                Foods.get(i).quantity += 1;
                duplicate = true;
            }
        }

        if (!duplicate) {
            Food NewFood = new Food(food, 1);
            Foods.add(NewFood);
        }
    }

    public static void setNote(int ID, String cusNote){
        for (int i = 0; i < Foods.size(); i++){
            if (Foods.get(i).IdFood == ID)
                Foods.get(i).note = cusNote;
        }
    }

    public static void setQuantity(int ID, int quantity) {
        for (int i = 0; i < Foods.size(); i++){
            if (Foods.get(i).IdFood == ID)
                Foods.get(i).quantity = quantity;
        }
    }
}
