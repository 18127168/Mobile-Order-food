package com.example.masterchef;

import android.app.Application;
import java.util.List;

public class SelectedFood extends Application {

    public class Food{
        String IdFood;
        int quanity;

        public Food(String Food, int num) {
            IdFood = Food;
            quanity = num;
        }
    }

    private List<Food> Foods;

    public List<Food> getSelectedFood() {
        return Foods;
    }

    public boolean decreaseStock(String food) {
        for (int i = 0; i < Foods.size(); i++){
            if (Foods.get(i).IdFood.equals(food)) {
                if (Foods.get(i).quanity == 1) {
                    return true;
                } else {
                    Foods.get(i).quanity -= 1;
                }
            }
        }

        return false;
    }

    public void addSelectedFood(String food) {
        boolean duplicate = true;

        for (int i = 0; i < Foods.size(); i++){
            if (Foods.get(i).IdFood.equals(food))
                Foods.get(i).quanity += 1;
        }

        if (!duplicate) {
            Food NewFood = new Food(food, 1);
            Foods.add(NewFood);
        }
    }
}
