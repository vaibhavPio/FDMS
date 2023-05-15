package com.fdms.services;

import com.fdms.entity.*;
import com.fdms.repository.*;
import com.lowagie.text.pdf.parser.Word;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.List;


@Service
public class CustomerServicesImpl implements CustomerServices{

    @Autowired
    private BaseLoginRepository baseLoginRepository;

    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;

    @Autowired
    private FoodRepository foodRepository;

    @Autowired
    private PlaceOrderRepository placeOrderRepository;
    @Autowired
    private OwnerRepository ownerRepository;
    @Autowired
    private EmailService emailService;

    @Autowired
    private PlaceOrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    private  static final String CUSTOMER_ERROR = "customer/customerError";



    @Override
    public ModelAndView displayRegistration(ModelAndView modelAndView, CustomerEntity customerEntity) {
        modelAndView.addObject("customer", customerEntity);
        modelAndView.setViewName("customer/register");
        return modelAndView;
    }

    //new register api
    @Override
    public ModelAndView registerUser(ModelAndView modelAndView, CustomerEntity customer)
    {

        BaseLoginEntity baseLogin = new BaseLoginEntity();
        baseLogin.setEmail(customer.getEmail());
        boolean find =baseLoginRepository.existsByEmail(baseLogin.getEmail());
        if(find)
        {
            modelAndView.addObject("message","This email already exists!");
            modelAndView.setViewName(CUSTOMER_ERROR);
        }
        else
        {
            customer.setPassword(passwordEncoder.encode(customer.getPassword()));
            customer.setRole("ROLE_CUSTOMER");
            baseLogin.setPassword(customer.getPassword());
            baseLogin.setRole(customer.getRole());
            baseLogin.setName(customer.getName());
            baseLogin.setApprovedStatus(true);
            baseLoginRepository.save(baseLogin);
            customerRepository.save(customer);

            ConfirmationTokenEntity confirmationTokenEntity = new ConfirmationTokenEntity(customer);
            confirmationTokenRepository.save(confirmationTokenEntity);

            modelAndView.addObject("email", customer.getEmail());
            modelAndView.setViewName("customer/customerSuccessfulRegistration");

            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(customer.getEmail());
            mailMessage.setSubject("Complete Registration!");
            mailMessage.setText("To confirm your account, please click here : "
                    +"http://localhost:8080/confirm-account?token="+ confirmationTokenEntity.getConfirmationToken());
            emailService.sendEmail(mailMessage);

        }
        return modelAndView;
    }

    //account verified
    @Override
    public ModelAndView confirmUserAccount(ModelAndView modelAndView,String confirmationToken)
    {
        ConfirmationTokenEntity token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);

        if(token != null)
        {
            CustomerEntity customerEntity = customerRepository.findByEmailIgnoreCase(token.getCustomer().getEmail());
            customerEntity.setEnabled(true);
            customerRepository.save(customerEntity);
            modelAndView.setViewName("customer/customerAccountVerified");
        }
        else
        {
            modelAndView.addObject("message","The link is invalid or broken!");
            modelAndView.setViewName(CUSTOMER_ERROR);
        }

        return modelAndView;
    }


    public ModelAndView homeForm(ModelAndView modelAndView)
    {
        modelAndView.setViewName("customerLogin");
        return modelAndView;
    }

    @Override
    public ModelAndView loginUser(ModelAndView modelAndView, String email, String password) {

        CustomerEntity info = customerRepository.findByEmailIgnoreCase(email);
        if (info.getPassword().equals(password)) {
            modelAndView.setViewName("success");
            return modelAndView;
        }
        modelAndView.setViewName(CUSTOMER_ERROR);
        return modelAndView;
    }

    @Override
    public List<OwnerEntity> getRestaurant(Model model) {

        return ownerRepository.findAllActivate();

    }
    @Override
    public List<FoodEntity> foodFromRestaurant(int id) {

        return ownerRepository.findById(id).get().getFoodEntities();
    }

    @Override
    public void requestToAdmin(String username, RequestAndSuggestion suggestion) {

        CustomerEntity customer = customerRepository.findByEmailIgnoreCase(username);
        suggestion.setUserName(username);
        suggestion.setName(customer.getName());
        suggestion.setRole(customer.getRole());
        List<RequestAndSuggestion> list = customer.getRequestAndComplaints();
        list.add(suggestion);
        customer.setRequestAndComplaints(list);
        customerRepository.save(customer);
    }

    @Override
    public FoodEntity placeOrder(int id) {
        foodRepository.findById(id).get().setFoodQuantity(1);
        return foodRepository.findById(id).get();
    }

    @Override
    public void confirmOrders(String userName,PlaceOrder order) {
//        int id = order.getFoodId();
//        FoodEntity food = foodRepository.findById(id).get();
//
//        System.out.println(food.getFoodId()+"gating owner");
//       int myid = foodRepository.findOwner(food.getFoodId());
//        System.out.println(myid);
//        OwnerEntity owner = ownerRepository.findById(myid).get();
//        System.out.println(owner+"--------------------------");
//        OwnerEntity owner = food.getOwner();
//        System.out.println("-------------------------------------------");
//        System.out.println(owner.getOwnerName());
//        System.out.println(owner.getRestaurantId());
//        System.out.println("------------------------------------------------");

        CustomerEntity customer = customerRepository.findByEmailIgnoreCase(userName);
        List<PlaceOrder> orderList= customer.getPlaceOrders();
        FoodEntity food = foodRepository.findById(order.getFoodId()).get();
        int ResId = foodRepository.findOwner(food.getFoodId());
        order.setRestaurantId(ResId);
        order.setCustomerAddress(customer.getAddress());
        order.setCustomerName(customer.getName());
        order.setRestaurantName(ownerRepository.findById(ResId).get().getRestaurantName());
        Date date = new Date();
        order.setTotalPrice(order.getFoodPrice()*order.getFoodQuantity());
        order.setOrderStatus("pending");
        order.setDate(date);
        orderList.add(order);
        customer.setPlaceOrders(orderList);
        customerRepository.save(customer);
    }

    @Override
    public List<PlaceOrder> getMyOrders(String userName,Model model) {

        int id = customerRepository.findByEmailIgnoreCase(userName).getCustomerId();
        return placeOrderRepository.findAllOrder(id) ;
    }

    @Override
    public List<PlaceOrder> listAll(String username) {

        CustomerEntity customer = customerRepository.findByEmailIgnoreCase(username);
        return placeOrderRepository.findAllOrder(customer.getCustomerId());

    }


}





