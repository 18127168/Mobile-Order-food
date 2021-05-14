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
            this.IdFood = 0;
            this.quantity = 0;
            this.note = "";
        }


        public Food(int Food, int num) {
            this.IdFood = Food;
            this.quantity = num;
            this.note = "";
        }

        public Food(int Food, int num, String note){
            this.IdFood = Food;
            this.quantity = num;
            this.note = note;
        }

        public int getQuantity() { return quantity; }
        public int getIdFood(){ return IdFood; }
        public String getNote() { return note; }

        public void increaseQuantity(int num) { this.quantity += num; }

        public void decreaseQuantity() { if (this.quantity > 0)     this.quantity -= 1; }
    }

    public static List<Food> Foods = new ArrayList<>();

    public static void clearSelectedFood(){
        Foods = new ArrayList<>();
    }

    public static void addSelectedFood(int food) {
        int pos = checkExist(food);

        if (pos != -1) {
            Foods.get(pos).quantity += 1;
        } else {
            Food NewFood = new Food(food, 1);
            Foods.add(NewFood);
        }
    }

    public static void addSelectedFoodWithNumAndNote(int food, int num, String note) {
        int pos = checkExist(food);

        if (pos != -1) {
            Foods.get(pos).quantity += num;
            Foods.get(pos).note = note;
        } else {
            Food NewFood = new Food(food, num, note);
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

    public static int checkExist(int food){
        for (int i = 0; i < Foods.size(); i++)
            if (Foods.get(i).IdFood == food)
                return i;
        return -1;
    }
}
