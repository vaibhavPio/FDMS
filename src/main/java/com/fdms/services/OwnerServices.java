package com.fdms.services;

import com.fdms.entity.FoodEntity;
import com.fdms.entity.OwnerEntity;
import com.fdms.entity.PlaceOrder;
import com.fdms.entity.RequestAndSuggestion;
import org.springframework.ui.Model;


public interface OwnerServices {

     OwnerEntity createOwner(OwnerEntity owner);

   boolean checkEmail(String email);


     void addFoodToList(String email, FoodEntity foodEntity);

     void   getFoodList(String email,Model model);

     void deleteFood(int id);

    void foodEdit(FoodEntity food);

    FoodEntity editFood(int id);

    void requestToAdmin(String userName,RequestAndSuggestion complaints);

    void allOrders(String username, Model model);

    void updateOrderInfo(int id, String orderStatus);

    void orderHistory(String username, Model model);

//    PlaceOrder updateOrderInformation(int id);
}
