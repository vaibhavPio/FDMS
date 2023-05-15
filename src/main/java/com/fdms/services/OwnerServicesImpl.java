package com.fdms.services;


import com.fdms.entity.*;
import com.fdms.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

@Service
public class OwnerServicesImpl implements OwnerServices{

    @Autowired
    private OwnerRepository ownerRepository;
    @Autowired
    private RequestAndSuggestionRepository suggestionRepository;

    @Autowired
    private FoodRepository foodRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private PlaceOrderRepository placeOrderRepository;
    @Autowired
    private BaseLoginRepository baseLoginRepository;

    @Override
    public OwnerEntity createOwner(OwnerEntity owner) {
        BaseLoginEntity baseLogin = new BaseLoginEntity();
        owner.setPassword(passwordEncoder.encode(owner.getPassword()));
        owner.setRole("ROLE_OWNER");
        baseLogin.setPassword(owner.getPassword());
        baseLogin.setRole(owner.getRole());
        baseLogin.setName(owner.getOwnerName());
        baseLogin.setEmail(owner.getEmail());
        baseLoginRepository.save(baseLogin);
        return ownerRepository.save(owner) ;


    }

    @Override
    public boolean checkEmail(String email) {
        return ownerRepository.existsByEmail(email);
    }


    public void addFoodToList(String email, FoodEntity foodEntity)
    {
        OwnerEntity owner= ownerRepository.findByEmail(email);
        List<FoodEntity> foodEntityList = owner.getFoodEntities();
        foodEntityList.add(foodEntity);
//        owner.setFoodEntities(foodEntityList);

//        ownerRepository.save(owner);
        foodEntity.setOwner(owner);
        foodRepository.save(foodEntity);
//
//        foodEntity.setOwner(owner);
//        foodRepository.save(foodEntity);
//        List<FoodEntity> list1 = owner.getFoodEntities();
//        list1.add(foodEntity);
//        owner.setFoodEntities(list1);
//        ownerRepository.save(owner);
    }

    @Override
    public void  getFoodList(String email,Model model) {
        OwnerEntity owner= ownerRepository.findByEmail(email);
        List<FoodEntity> foodList = owner.getFoodEntities();
        model.addAttribute("foodList",foodList);

    }

    @Override
    public void deleteFood(int id) {
        foodRepository.deleteById(id);
    }

    @Override
    public void foodEdit(FoodEntity food) {

        foodRepository.save(food);
    }

    @Override
    public FoodEntity editFood(int id) {
        return foodRepository.findById(id).get();
    }

    @Override
    public void requestToAdmin(String userName,RequestAndSuggestion suggestion) {

        OwnerEntity owner = ownerRepository.findByEmail(userName);
        suggestion.setUserName(userName);
        suggestion.setName(owner.getOwnerName());
        suggestion.setRole(owner.getRole());
        List<RequestAndSuggestion> list = owner.getRequestAndComplaints();
        list.add(suggestion);
        owner.setRequestAndComplaints(list);
        ownerRepository.save(owner);

    }

    @Override
    public void allOrders(String username, Model model) {


        int id = ownerRepository.findByEmail(username).getRestaurantId();
      List<PlaceOrder> orderList=  placeOrderRepository.findAllMyOrder(id);
      model.addAttribute("orderList", orderList);

    }

    @Override
    public void updateOrderInfo(int id, String orderStatus) {
      PlaceOrder order =  placeOrderRepository.findById(id).get();
        System.out.println("inside service >>>>>>>>>>>>>>>>>>>>"+order);
      order.setOrderStatus(orderStatus);
        System.out.println("After save >>>>>>>>>>>>>>>>>>>"+order);
      placeOrderRepository.save(order);
        System.out.println(order);
    }

    @Override
    public void orderHistory(String username, Model model) {

        int id = ownerRepository.findByEmail(username).getRestaurantId();
        List<PlaceOrder> orderList=  placeOrderRepository.findAllMyOrder(id);
        List<PlaceOrder> historyList = new ArrayList<>();
        for (PlaceOrder order : orderList){
            if(order.getOrderStatus() .equals("Completed") || order.getOrderStatus().equals("Cancel")){
                historyList.add(order);
            }
        }
        model.addAttribute("historyList", historyList);


//        int id = ownerRepository.findByEmail(username).getRestaurantId();
//        List<PlaceOrder> historyList=  placeOrderRepository.findAllMyOrder(id);
//        model.addAttribute("historyList", historyList);
    }

//    @Override
//    public PlaceOrder updateOrderInformation(int id) {
//
//        return  placeOrderRepository.findById(id).get();
//
//    }


}
