package com.fdms.controller;


import com.fdms.configuration.CustomUserDetails;
import com.fdms.dto.RiderEntityDto;
import com.fdms.entity.RiderEntity;
import com.fdms.repository.RiderRepository;
import com.fdms.services.RiderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/rider")
public class RiderController {

    @Autowired
    private RiderService riderService;

    @GetMapping("/")
    public String home(){


        return "rider/home";
    }

    @RequestMapping("/updateInfo")
    public String updateRiderInfo(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
        RiderEntity rider = riderService.updateRiderInfo(userDetails.getUsername());
        System.out.println("gating info..");
        model.addAttribute("rider", rider);
        return "rider/update";
    }

    @RequestMapping("riderUpdating")
    public String riderUpdating(@ModelAttribute RiderEntityDto riderRequestDto)
    {
        System.out.println("in rider updating");
        System.out.println(riderRequestDto);
        riderService.riderEdit(riderRequestDto);
        return "redirect:/";
    }

    @GetMapping("/readyToPick")
    public String readyToPickOrder(){


        return "rider/readyToPickOrder";
    }

    @GetMapping("/orderHistory")
    public String orderHistory(){


        return "rider/orderHistory";
    }
}



