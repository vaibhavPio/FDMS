package com.fdms.services;

import com.fdms.dto.CustomerEntityDto;
import com.fdms.dto.RiderEntityDto;
import com.fdms.entity.*;
import com.fdms.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;


@Service
public class AdminServicesImpl implements AdminServices{

    @Autowired
    private BaseLoginRepository baseLoginRepository;
    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private RequestAndSuggestionRepository suggestionRepository;
    @Autowired
    private EmailService emailService;
    @Autowired
    private OwnerRepository ownerRepository;
    @Autowired
    private RiderRepository riderRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public AdminEntity createAdmin(AdminEntity admin) {

        BaseLoginEntity baseLogin = new BaseLoginEntity();
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        admin.setRole("ROLE_ADMIN");
        baseLogin.setPassword(admin.getPassword());
        baseLogin.setRole(admin.getRole());
        baseLogin.setName(admin.getName());
        baseLogin.setEmail(admin.getEmail());
        baseLogin.setApprovedStatus(true);
        baseLoginRepository.save(baseLogin);

        return adminRepository.save(admin);


    }

    @Override
    public boolean checkEmail(String email) {
        return adminRepository.existsByEmail(email);
    }

    @Override
    public void getPendingRequests(Model model)
    {
        List<BaseLoginEntity> baseLoginEntities = baseLoginRepository.getPendingRequests();
        model.addAttribute("baseLoginEntities", baseLoginEntities);
    }
    @Override
    public void delete(Integer id) {
        BaseLoginEntity baseLogin = baseLoginRepository.findById(id).get();
        String  myRole= baseLogin.getRole();
        if(myRole.contains("ROLE_RIDER"))
        {
            RiderEntity rider=riderRepository.findByEmail(baseLogin.getEmail());

            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(rider.getEmail());
            mailMessage.setSubject("Account request Rejected.!");
            mailMessage.setText("Hi "+ rider.getName() + " Your Account request for Rider Register is Rejected by Admin Sorry for inconvenience..!");
            emailService.sendEmail(mailMessage);

            int myId = rider.getRiderId();
            riderRepository.deleteById(myId);
        }else
        {
            OwnerEntity owner =ownerRepository.findByEmail(baseLogin.getEmail());

            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(owner.getEmail());
            mailMessage.setSubject("Account request Rejected.!");
            mailMessage.setText("Hi "+ owner.getOwnerName() + " Your Account request for Restaurant Register is Rejected by Admin Sorry for inconvenience..!");
            emailService.sendEmail(mailMessage);

            int mId= owner.getRestaurantId();
            ownerRepository.deleteById(mId);
        }
        baseLoginRepository.deleteById(id);
    }

    @Override
    public void giveApproval(Integer id) {
        BaseLoginEntity baseLogin =baseLoginRepository.findById(id).get();
        String myRole= baseLogin.getRole();
        if(myRole.contains("ROLE_RIDER"))
        {
            RiderEntity rider = riderRepository.findByEmail(baseLogin.getEmail());
            rider.setApprovedStatus(true);
            rider.setActivationStatus(true);
            riderRepository.save(rider);

            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(rider.getEmail());
            mailMessage.setSubject("Congratulation your Account is Approved!");
            mailMessage.setText("Hi "+ rider.getName() + " Your Account request for Rider Register is Approved by Admin Now you can Login And Enjoy the App..!");
            emailService.sendEmail(mailMessage);

        } else
        {
            OwnerEntity owner = ownerRepository.findByEmail(baseLogin.getEmail());
            owner.setApprovedStatus(true);
            ownerRepository.save(owner);


            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(owner.getEmail());
            mailMessage.setSubject("Congratulation your Account is Approved!");
            mailMessage.setText("Hi "+ owner.getOwnerName() + " Your Account request for Restaurant Register is Approved by Admin Now you can Login And Enjoy the App..!");
            emailService.sendEmail(mailMessage);
        }
        baseLogin.setApprovedStatus(true);
        baseLoginRepository.save(baseLogin);
    }

    @Override
    public void getAllRiders(Model model)
    {
        List<RiderEntity> riderEntities = riderRepository.findAllApproved();
        model.addAttribute("riderEntities", riderEntities);
    }

    @Override
    public void getAllCustomer(Model model) {

        List<CustomerEntity> customerEntities = customerRepository.findAll();
        model.addAttribute("customerEntities",customerEntities);
    }

    @Override
    public void getAllOwner(Model model) {
        List<OwnerEntity> ownerEntities = ownerRepository.findAllApproved();
        model.addAttribute("ownerEntities",ownerEntities);

    }

    @Override
    public void deleteRider(Integer id) {
        RiderEntity rider= riderRepository.findById(id).get();
        int myId = baseLoginRepository.findByEmail(rider.getEmail()).getBaseLoginId();
        baseLoginRepository.deleteById(myId);
        riderRepository.deleteById(id);
    }

    @Override
    public void deleteOwner(int id) {
        OwnerEntity owner= ownerRepository.findById(id).get();
        int myId = baseLoginRepository.findByEmail(owner.getEmail()).getBaseLoginId();
        baseLoginRepository.deleteById(myId);
        ownerRepository.deleteById(id);
    }

    @Override
    public void deleteCustomer(Integer id) {
        CustomerEntity customer = customerRepository.findById(id).get();
        int myId = baseLoginRepository.findByEmail(customer.getEmail()).getBaseLoginId();
        baseLoginRepository.deleteById(myId);
        customerRepository.deleteById(id);
    }

    @Override
    public RiderEntity updateRider(int id) {
        return   riderRepository.findById(id).get();
    }
    @Override
    public void riderEdit(RiderEntityDto riderRequestDto) {

        RiderEntity rider = new RiderEntity();
        rider.setRiderId(riderRequestDto.getRiderId());
        rider.setActivationStatus(riderRequestDto.isActivationStatus());
        rider.setRole(riderRequestDto.getRole());
        rider.setPassword(riderRequestDto.getPassword());
        rider.setEmail(riderRequestDto.getEmail());
        rider.setAddress(riderRequestDto.getAddress());
        rider.setDateOfBirth(riderRequestDto.getDateOfBirth());
        rider.setGender(riderRequestDto.getGender());
        rider.setName(riderRequestDto.getName());
        rider.setPhoneNumber(riderRequestDto.getPhoneNumber());
        rider.setActivationStatus(riderRequestDto.isActivationStatus());

        riderRepository.save(rider);
    }

    @Override
    public OwnerEntity updateOwner(int id) {
        return ownerRepository.findById(id).get();
    }

    @Override
    public void ownerEdit(OwnerEntity owner) {
        ownerRepository.save(owner);
    }

    @Override
    public CustomerEntity updateCustomer(int id) {
        return customerRepository.findById(id).get();
    }

    @Override
    public void customerEdit(CustomerEntityDto customerRequestDto) {
        CustomerEntity customer = new CustomerEntity();
        customer.setCustomerId(customerRequestDto.getCustomerId());
        customer.setRole(customerRequestDto.getRole());
        customer.setPassword(customerRequestDto.getPassword());
        customer.setEmail(customerRequestDto.getEmail());
        customer.setAddress(customerRequestDto.getAddress());
        customer.setName(customerRequestDto.getName());
        customer.setGender(customerRequestDto.getGender());
        customer.setEnabled(customerRequestDto.isEnabled());
        customer.setDateOfBirth(customer.getDateOfBirth());
        customer.setPhoneNumber(customerRequestDto.getPhoneNumber());
        customer.setTotalOrder(customerRequestDto.getTotalOrder());
        customerRepository.save(customer);
    }

    @Override
    public void changeStatus(int id) {

        RiderEntity rider = riderRepository.findById(id).get();
        rider.setActivationStatus(true);
        riderRepository.save(rider);
    }

    @Override
    public void changeRiderStatus(int id) {

        RiderEntity rider =riderRepository.findById(id).get();
        rider.setActivationStatus(false);
        riderRepository.save(rider);
    }

    @Override
    public void activateOwnerStatus(int id) {

        OwnerEntity owner = ownerRepository.findById(id).get();
        owner.setActivationStatus(true);
        ownerRepository.save(owner);
    }

    @Override
    public void deactivateOwnerStatus(int id) {

        OwnerEntity owner = ownerRepository.findById(id).get();
        owner.setActivationStatus(false);
        ownerRepository.save(owner);
    }

    @Override
    public void allNotDoneResponse(String userName,Model model) {
//       OwnerEntity owner = ownerRepository.findByEmail(userName);
        List<RequestAndSuggestion> suggestionList = suggestionRepository.findAllNotDone();
        model.addAttribute("suggestionList",suggestionList);

    }

    @Override
    public void removeRequest(int id) {

        RequestAndSuggestion suggestion =  suggestionRepository.findById(id).get();
        suggestion.setRequestStatus(true);
        suggestionRepository.save(suggestion);
    }



}
