package com.fdms.controller;

import com.fdms.configuration.CustomUserDetails;
import com.fdms.configuration.UserDetailsServiceImpl;
import com.fdms.entity.FoodEntity;
import com.fdms.entity.PlaceOrder;
import com.fdms.entity.RequestAndSuggestion;
import com.fdms.entity.RiderEntity;
import com.fdms.repository.FoodRepository;
import com.fdms.repository.PlaceOrderRepository;
import com.fdms.services.OwnerServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;


@Controller
@RequestMapping("/owner")
public class OwnerController {
    @Autowired
    private PlaceOrderRepository placeOrderRepository;
    @Autowired
    private FoodRepository foodRepository;
    @Autowired
    private OwnerServices ownerServices;

    @Autowired
    public UserDetailsServiceImpl userDetailsService;

    @GetMapping("/")
    public String home() {
        return "owner/home";
    }


    @RequestMapping("/addFoodList")
    public String addFoodInList() {
        return "owner/addFood";
    }


    @GetMapping("/addNewFood")
    public String addFoodToList(@AuthenticationPrincipal CustomUserDetails customUserDetails, @ModelAttribute FoodEntity foodEntity, HttpSession session) {
        boolean check = foodRepository.existsByFoodName(foodEntity.getFoodName());
        if (check) {
            session.setAttribute("msg", foodEntity.getFoodName() + " is Already exist..");
        } else {

            if (true) {
                ownerServices.addFoodToList(customUserDetails.getUsername(), foodEntity);
                session.setAttribute("msg", foodEntity.getFoodName() + " added Successfully..!");
            } else {
                session.setAttribute("msg", " Something is Wrong.. ");
            }
        }
        return "redirect:/owner/addFoodList";
    }


    @RequestMapping("/getFoodList")
    public String getAllFoodList(@AuthenticationPrincipal CustomUserDetails customUserDetails, Model model) {
        ownerServices.getFoodList(customUserDetails.getUsername(), model);
        return "owner/getAllFoodList";
    }

    @RequestMapping("/deleteFood/{id}")
    public String deleteFood(@PathVariable("id") int id) {
        ownerServices.deleteFood(id);
        return "redirect:/owner/getFoodList";
    }

    @RequestMapping("/demo")
    public String demo() {
        return "demo";
    }

    @RequestMapping("/editFood{id}")
    public String editFood(@PathVariable("id") int id, Model model) {
        FoodEntity fl = ownerServices.editFood(id);
        model.addAttribute("food", fl);
        return "owner/editFood";
    }

    @RequestMapping(value = "foodEditing")
    public String foodEditing(@ModelAttribute FoodEntity food) {
        ownerServices.foodEdit(food);
        return "redirect:/owner/getFoodList";
    }

    @RequestMapping("/orderRequest")
    public String orderRequests(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
        ownerServices.allOrders(userDetails.getUsername(), model);
        return "owner/orderRequest";
    }

    @RequestMapping("/adminRequest")
    public String ownerRequest() {

        return "owner/adminRequest";
    }

    @RequestMapping("/requestToAdmin")
    public String requestToAdmin(@AuthenticationPrincipal CustomUserDetails customUserDetails, @ModelAttribute RequestAndSuggestion suggestion, HttpSession session) {

        if (suggestion.getRequest().isEmpty()) {
            session.setAttribute("msg", "write your response first..");
        } else {
            session.setAttribute("msg", "Thank you for your response");
            ownerServices.requestToAdmin(customUserDetails.getUsername(), suggestion);
        }
        return "redirect:/owner/adminRequest";
    }


    @RequestMapping("/updateOrderInfo/{id}/{orderStatus}")
    public String updateOrderInfo(@PathVariable("id") int id, @PathVariable("orderStatus") String orderStatus) {
        ownerServices.updateOrderInfo(id, orderStatus);
        return "demo";
    }


//        @RequestMapping("/updateOrderInfo{id}")
//    public String updateOrderInfo( @PathVariable(value = "id") int id, Model model){
//
//           PlaceOrder order = ownerServices.updateOrderInformation(id);
//
//                model.addAttribute("order",order);
//
//        return "demo";
//    }

    @RequestMapping("/orderHistory")
    public String orderHistory(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
        {
            ownerServices.orderHistory(userDetails.getUsername(), model);

            return "owner/orderHistory";
        }

    }
}
