package com.fdms.services;
import com.fdms.dto.CustomerEntityDto;
import com.fdms.dto.RiderEntityDto;
import com.fdms.entity.AdminEntity;
import com.fdms.entity.CustomerEntity;
import com.fdms.entity.OwnerEntity;
import com.fdms.entity.RiderEntity;
import org.springframework.ui.Model;

public interface AdminServices {


    AdminEntity createAdmin(AdminEntity admin);

    boolean checkEmail(String email);

    void getPendingRequests(Model model);

    void giveApproval(Integer id);

    void delete(Integer id);

    void getAllRiders(Model model);

    RiderEntity updateRider(int id);

    void getAllCustomer(Model model);

    void getAllOwner(Model model);

    void deleteRider(Integer id);

    void deleteOwner(int id);

    void deleteCustomer(Integer id);

    void riderEdit(RiderEntityDto riderRequestDto);

    OwnerEntity updateOwner(int id);

    void ownerEdit(OwnerEntity owner);

    CustomerEntity updateCustomer(int id);

    void customerEdit(CustomerEntityDto customerRequestDto);

    void changeStatus(int id);

    void changeRiderStatus(int id);

    void activateOwnerStatus(int id);

    void deactivateOwnerStatus(int id);

    void  allNotDoneResponse(String userName,Model model);

    void removeRequest(int id);
}
