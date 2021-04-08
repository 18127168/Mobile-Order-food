package com.example.masterchef;

import java.util.ArrayList;
import java.util.List;

class MenuWithFoodInFireBase{
    public List<Integer> listFoodInMenu;
    MenuWithFoodInFireBase(){
        listFoodInMenu = new ArrayList<>();
    }
    MenuWithFoodInFireBase(List<Integer> list){
        listFoodInMenu = list;
    }
}
