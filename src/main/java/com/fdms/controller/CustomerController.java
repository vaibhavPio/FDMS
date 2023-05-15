package com.fdms.controller;



import com.fdms.configuration.CustomUserDetails;
import com.fdms.entity.*;
import com.fdms.services.CustomerServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@Controller
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private ServletContext servletContext;
    @Autowired
    private CustomerServices customerServices;

    @GetMapping("/")
    public String home()
    {
        return "customer/home";
    }

    @RequestMapping("/getRestaurants")
    public String getRestaurants(Model model){
        List<OwnerEntity> ownerEntityList =  customerServices.getRestaurant(model);
        model.addAttribute("ownerEntityList",ownerEntityList);
        return "customer/restaurantList";
    }

    @RequestMapping("/getFoodFromRestaurant/{id}")
    public String getFoodFromRestaurant(@PathVariable(value = "id") int id, Model model){
        List<FoodEntity> food = customerServices.foodFromRestaurant(id);
        model.addAttribute("food",food);
        return "customer/foodFromRestaurant";
    }


    @RequestMapping("/adminRequest")
    public String customerRequest()
    {
        return "customer/adminRequest";
    }

    @RequestMapping("/requestToAdmin")
    public String  requestToAdmin(@AuthenticationPrincipal CustomUserDetails customUserDetails, @ModelAttribute RequestAndSuggestion suggestion, HttpSession session)
    {
        if(suggestion.getRequest().isEmpty())
        {
            session.setAttribute("msg","write your response first..");
        } else
        {
            session.setAttribute("msg", "Thank you for your response");
            customerServices.requestToAdmin(customUserDetails.getUsername(), suggestion);
        }
        return "redirect:/customer/adminRequest";
    }


    @RequestMapping("/cartList")
    public String cartLIst(){
        return "demo";
    }

    @RequestMapping("/placeOrder{id}")
    public String placeOrder(@PathVariable int id, Model model){
        FoodEntity food = customerServices.placeOrder(id);
        model.addAttribute("food",food);
        return "customer/placeOrders";
    }

    @RequestMapping("confirmOrder")
    public String confirmOrder(@AuthenticationPrincipal CustomUserDetails customUserDetails, @ModelAttribute PlaceOrder order)
    {
        customerServices.confirmOrders(customUserDetails.getUsername(),order);
        return "redirect:/customer/getRestaurants";
    }

    @RequestMapping("/getMyOrders")
    public String getMyOrders(@AuthenticationPrincipal CustomUserDetails customUserDetails, Model model)
    {
        List<PlaceOrder> placeOrders = customerServices.getMyOrders(customUserDetails.getUsername(),model);
        model.addAttribute("placeOrders",placeOrders);
        return "customer/myOrders";
    }


    @GetMapping("createPdf")
    public void exportToPDF(HttpServletResponse response, @AuthenticationPrincipal CustomUserDetails userDetails) throws IOException {
        response.setContentType("application/pdf");

        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=Order history_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);

        List<PlaceOrder> orders = customerServices.listAll(userDetails.getUsername());
        CustomerPDFExporter exporter = new CustomerPDFExporter(orders);
        exporter.export(response);
    }

    /*
    // Using HttpServletResponse
    @GetMapping("createPdf")
    public void exportToPDF(HttpServletResponse response,
                              @RequestParam(defaultValue = DEFAULT_FILE_NAME) String fileName) throws IOException {

        MediaType mediaType = MediaTypeUtils.getMediaTypeForFileName(this.servletContext, fileName);
        System.out.println("fileName: " + fileName);
        System.out.println("mediaType: " + mediaType);

        File file = new File(DIRECTORY + "/" + fileName);

         //Content-Type
        // application/pdf
        response.setContentType(mediaType.getType());

        // Content-Disposition
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file.getName());

        // Content-Length
        response.setContentLength((int) file.length());

        BufferedInputStream inStream = new BufferedInputStream(new FileInputStream(file));
        BufferedOutputStream outStream = new BufferedOutputStream(response.getOutputStream());

        byte[] buffer = new byte[1024];
        int bytesRead = 0;
        while ((bytesRead = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, bytesRead);
        }
        outStream.flush();
        inStream.close();
    }
    */
/*
    @GetMapping("createPdf")
    public ResponseEntity<ByteArrayResource> exportToPDF(
            @RequestParam(defaultValue = DEFAULT_FILE_NAME) String fileName) throws IOException {

        MediaType mediaType = MediaTypeUtils.getMediaTypeForFileName(this.servletContext, fileName);
        System.out.println("fileName: " + fileName);
        System.out.println("mediaType: " + mediaType);

        Path path = Paths.get(DIRECTORY + "/" + DEFAULT_FILE_NAME);
        byte[] data = Files.readAllBytes(path);
        ByteArrayResource resource = new ByteArrayResource(data);

        return ResponseEntity.ok()
                // Content-Disposition
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + path.getFileName().toString())
                // Content-Type
                .contentType(mediaType) //
                // Content-Lengh
                .contentLength(data.length) //
                .body(resource);
    }
    */

    @RequestMapping("/totalOrder")
    public String totalOrder()
    {
        return "demo";
    }


}




