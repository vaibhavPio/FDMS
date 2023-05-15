package com.fdms.services;
//
//import com.fdms.entity.CustomerEntity;
//import com.fdms.entity.PlaceOrder;
//import com.fdms.repository.CustomerRepository;
//import com.fdms.repository.PlaceOrderRepository;
//import com.itextpdf.kernel.pdf.PdfDocument;
//import com.itextpdf.kernel.pdf.PdfWriter;
//import com.itextpdf.layout.Document;
//import com.itextpdf.layout.element.Cell;
//import com.itextpdf.layout.element.Paragraph;
//import com.itextpdf.layout.element.Table;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.io.FileNotFoundException;
//import java.util.List;
//
//@Service
//public class CreatePdfFileServiceImpl /*implements CreatePdfFileService*/{}
//
//    @Autowired
//    private CustomerRepository customerRepository;
//    @Autowired
//    private PlaceOrderRepository placeOrderRepository;
//
//
//    public void createPdf(String username){
//        CustomerEntity customer = customerRepository.findByEmailIgnoreCase(username);
//        List <PlaceOrder> orderList = placeOrderRepository.findAllMyOrder(customer.getCustomerId());
//        String filePdf = "C:/Users/VaibhavNawale/Downloads/FDMS FoodHistory.pdf";
//        try {
//            PdfWriter writer = new PdfWriter(filePdf);
//            PdfDocument pdfDocument = new PdfDocument(writer);
//            Document document = new Document(pdfDocument);
//            Paragraph paragraph = new Paragraph("Hello, "+customer.getName()+" this is your Last order history");
//            float[] columWidth = {200f, 200f, 200f, 200f, 200f, 200f, 200f};
//            Table table = new Table(columWidth);
//            table.addCell(new Cell().add("ID"));
//            table.addCell(new Cell().add("Food Name"));
//            table.addCell(new Cell().add("Price"));
//            table.addCell(new Cell().add("Quantity"));
//            table.addCell(new Cell().add("Total Amount"));
//            table.addCell(new Cell().add("Date/Time"));
//            table.addCell(new Cell().add("Food Details"));
//            for(PlaceOrder order:orderList){
//                table.addCell(new Cell().add(String.valueOf(order.getOrderId())));
//                table.addCell(new Cell().add(order.getFoodName()));
//                table.addCell(new Cell().add(String.valueOf(order.getFoodPrice())));
//                table.addCell(new Cell().add(String.valueOf(order.getFoodQuantity())));
//                table.addCell(new Cell().add(String.valueOf(order.getTotalPrice())));
//                table.addCell(new Cell().add(String.valueOf(order.getDate())));
//                table.addCell(new Cell().add(order.getFoodDetails()));
//            }
//            document.add(paragraph);
//            document.add(table);
//            document.close();
//
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//    }
//}
