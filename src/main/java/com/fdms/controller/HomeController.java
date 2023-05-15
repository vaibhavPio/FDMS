package com.fdms.controller;


import com.fdms.entity.*;
import com.fdms.repository.BaseLoginRepository;
import com.fdms.repository.ConfirmationTokenRepository;
import com.fdms.repository.CustomerRepository;
import com.fdms.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;


@Controller
public class HomeController {

    @Autowired
    private BaseLoginRepository baseLoginRepository;
    @Autowired
    private CustomerServicesImpl customerServicesImpl;

    @Autowired
    private OwnerServices ownerServices;

    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private AdminServices adminServices;

    @Autowired
    private RiderService riderService;
    String l1 = "This email already exists!";
    String l2 = " Register Successfully";
    String l3 = "Something wrong on server/ Check input";
    String l4 = " Wait for Admin Approval";

    @GetMapping("/")
    public String index()
    {
        return "index";
    }


    //rider login
    @GetMapping("/signIn")
    public String myLogin()
    {
        return "myLogin";
    }

    @GetMapping("/myRegistration")
    public String customerRegister()
    {
        return "customer/customerRegister";
    }

    @GetMapping("/adminRegistration")
    public String adminRegistration()
    {
        return "admin/adminRegister";
    }


    BaseLoginEntity baseLogin = new BaseLoginEntity();
    @PostMapping("/createAdmin")
    public String createAdmin(@ModelAttribute @Valid AdminEntity admin, HttpSession session) {

        boolean find =baseLoginRepository.existsByEmail(admin.getEmail());
        if(find)
        {
            session.setAttribute("msg", l1);
        }
        else {
            AdminEntity adminEntity = adminServices.createAdmin(admin);

            if (adminEntity != null) {
                session.setAttribute("msg",admin.getName()+ l2);

            } else {
                session.setAttribute("msg", l3);
            }
        }
        return "redirect:/adminRegistration";
    }


    @GetMapping("/ownerRegistration")
    public String ownerRegistration()
    {
        return "owner/ownerRegister";
    }

    @PostMapping("/createOwner")
    public String createOwner(@ModelAttribute @Valid OwnerEntity owner, HttpSession session) {

        boolean find =baseLoginRepository.existsByEmail(owner.getEmail());
        if(find)
        {
            session.setAttribute("msg", l1);
        }
        else {
            OwnerEntity ownerEntity = ownerServices.createOwner(owner);

            if (ownerEntity != null) {
                session.setAttribute("msg", "Hi "+owner.getOwnerName()+",\r\n" +l4);

            } else {
                session.setAttribute("msg", l3);
            }
        }
        return "redirect:/ownerRegistration";
    }



    @GetMapping("/riderRegister")
    public String register() {
        return "rider/riderRegister";
    }

    @PostMapping("/createRider")
    public String createRider(@ModelAttribute @Valid RiderEntity rider,Errors errors, HttpSession session) {

        if (errors.hasErrors())
        {
            return "redirect:/riderRegister";
        }
        else {
            boolean find = baseLoginRepository.existsByEmail(rider.getEmail());
            if (find) {
                session.setAttribute("msg", l1);
            } else {
                RiderEntity riderEntity = riderService.createRider(rider);

                if (riderEntity != null) {
                    session.setAttribute("msg", "Hi " + rider.getName() + ",\r\n" + l4);

                } else {
                    session.setAttribute("msg", l3);
                }
            }
            return "redirect:/riderRegister";
        }
    }

    @GetMapping(value = "/register")
    public ModelAndView displayRegistration (ModelAndView modelAndView, @Valid CustomerEntity customer)
    {

        return customerServicesImpl.displayRegistration(modelAndView, customer);
    }

    @PostMapping(value = "/register")
    public ModelAndView registerUser ( ModelAndView modelAndView,@Valid CustomerEntity customer)
    {
        return customerServicesImpl.registerUser(modelAndView, customer);
    }

    //account verified
    @RequestMapping(value = "/confirm-account", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView confirmUserAccount (ModelAndView modelAndView, @RequestParam("token") String
            confirmationToken)
    {
        return customerServicesImpl.confirmUserAccount(modelAndView, confirmationToken);

    }


}



