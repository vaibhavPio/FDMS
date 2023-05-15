package com.fdms.controller;


import com.fdms.configuration.CustomUserDetails;
import com.fdms.dto.CustomerEntityDto;
import com.fdms.dto.RiderEntityDto;
import com.fdms.entity.*;
import com.fdms.repository.BaseLoginRepository;
import com.fdms.services.AdminServices;
import com.fdms.services.RiderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/admin")
public class AdminController {

    private static final String GET_ALL_RIDERS="redirect:/admin/getAllRiders";
    private static final String GET_ALL_OWNER= "redirect:/admin/getAllOwner";
    @Autowired
    private AdminServices adminServices;
    @Autowired
    private BaseLoginRepository baseLoginRepository;


    @Autowired
    private RiderService riderService;

    @GetMapping("/")
    public String home()
    {
        return "admin/home";
    }

    @RequestMapping("getPendingRequests")
    public String getPendingRequests(Model model)
    {
        adminServices.getPendingRequests(model);
        return "admin/waitingForApproval";
    }

    @RequestMapping(value = "/delete/{id}")
    public String  delete(@PathVariable(value = "id") Integer id){
        adminServices.delete(id);
        return "redirect:/admin/getPendingRequests";
    }

    @RequestMapping("/giveApproval/{id}")
    public String giveApproval(@PathVariable(value = "id") Integer id)
    {
        adminServices.giveApproval(id);
        return "redirect:/admin/getPendingRequests";
    }


    @RequestMapping("getAllRiders")
    public String getAllRiders(Model model)
    {
        adminServices.getAllRiders(model);
        return "admin/allUserRiders";
    }

    @RequestMapping("/updateRiderInfo{id}")
    public String updateRiderInfo(@PathVariable(value = "id") int id,Model model)
    {
        RiderEntity rider = adminServices.updateRider(id);
        model.addAttribute("rider",rider);
        return "admin/updateRider";
    }

    @RequestMapping(value = "riderEditing")
    public String riderEditing(@ModelAttribute RiderEntityDto riderRequestDto)
    {
        adminServices.riderEdit(riderRequestDto);
        return GET_ALL_RIDERS;
    }

    @RequestMapping("/deleteRider/{id}")
    public String  deleteRider(@PathVariable(value = "id") Integer id)
    {
        adminServices.deleteRider(id);
        return GET_ALL_RIDERS;
    }

    @RequestMapping("getAllCustomer")
    public String getAllCustomer(Model model)
    {
        adminServices.getAllCustomer(model);
        return "admin/allUserCustomer";
    }
    @RequestMapping("/updateCustomerInfo{id}")
    public String updateCustomerInfo(@PathVariable(value = "id") int id,Model model)
    {
        CustomerEntity customer = adminServices.updateCustomer(id);
        model.addAttribute("customer",customer);
        return "admin/updateCustomer";
    }

    @RequestMapping(value = "customerEditing")
    public String customerEditing(@ModelAttribute CustomerEntityDto customerRequestDto)
    {
        adminServices.customerEdit(customerRequestDto);
        return "redirect:/admin/getAllCustomer";
    }

    @RequestMapping("/deleteCustomer/{id}")
    public String  deleteCustomer(@PathVariable(value = "id") int id)
    {
        adminServices.deleteCustomer(id);
        return "redirect:/admin/getAllCustomer";
    }

    @RequestMapping("getAllOwner")
    public String getAllOwner(Model model)
    {
        adminServices.getAllOwner(model);
        return "admin/allUserOwner";
    }

    @RequestMapping("/updateOwnerInfo{id}")
    public String updateOwnerInfo(@PathVariable(value = "id") int id,Model model)
    {
        OwnerEntity owner = adminServices.updateOwner(id);
        model.addAttribute("owner",owner);
        return "admin/updateOwner";
    }

    @RequestMapping(value = "ownerEditing")
    public String ownerEditing(@ModelAttribute OwnerEntity owner)
    {
        adminServices.ownerEdit(owner);
        return GET_ALL_OWNER;
    }

    @RequestMapping("/deleteOwner/{id}")
    public String  deleteOwner(@PathVariable(value = "id") int id)
    {
        adminServices.deleteOwner(id);
        return GET_ALL_OWNER;
    }

    @RequestMapping("/activateStatus/{id}")
    public String changeStatus(@PathVariable (value = "id") int id){

        adminServices.changeStatus(id);
        return GET_ALL_RIDERS;
    }

    @RequestMapping("/deactivateStatus/{id}")
    public String deactivateRiderStatus(@PathVariable (value = "id") int id){

        adminServices.changeRiderStatus(id);
        return GET_ALL_RIDERS;
    }

    @RequestMapping("/activateOwnerStatus/{id}")
    public String activateOwnerStatus(@PathVariable (value = "id") int id){

        adminServices.activateOwnerStatus(id);
        return GET_ALL_OWNER;
    }

    @RequestMapping("/deactivateOwnerStatus/{id}")
    public String deactivateOwnerStatus(@PathVariable (value = "id") int id){

        adminServices.deactivateOwnerStatus(id);
        return GET_ALL_OWNER;
    }

    @RequestMapping("/userResponses")
    public String userResponse(@AuthenticationPrincipal CustomUserDetails customUserDetails , Model model)
    {
        adminServices.allNotDoneResponse(customUserDetails.getUsername(),model);
        return "admin/userResponse";
    }

    @RequestMapping("/removeRequest/{id}")
    public String  updateRequest(@PathVariable int id)
    {
        adminServices.removeRequest(id);
        return "redirect:/admin/userResponses";
    }



}
