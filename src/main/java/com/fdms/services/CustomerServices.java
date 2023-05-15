package com.fdms.services;

import com.fdms.entity.*;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

public interface CustomerServices {

     ModelAndView displayRegistration(ModelAndView modelAndView, CustomerEntity customerEntity);

     ModelAndView registerUser(ModelAndView modelAndView, CustomerEntity customerEntity);
     ModelAndView confirmUserAccount(ModelAndView modelAndView,String confirmationToken);

     ModelAndView loginUser(ModelAndView modelAndView, String email, String password);

    List<OwnerEntity> getRestaurant(Model model);

    List<FoodEntity> foodFromRestaurant(int id);

    void requestToAdmin(String username, RequestAndSuggestion suggestion);

    FoodEntity placeOrder(int id);

    void confirmOrders(String userName,PlaceOrder order);

    List<PlaceOrder> getMyOrders(String userName,Model model);

    List<PlaceOrder> listAll(String username);
}
