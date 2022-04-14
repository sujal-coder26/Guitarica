package com.web.guitarapp.controller;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import com.web.guitarapp.entities.Order;
import com.web.guitarapp.service.PaymentService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PaymentController {

    PaymentService service;

    public static final String SUCCESS = "payMoney/paymentSuccess";
    public static final String CANCEL = "payMoney/failure";

    @GetMapping("/payment")
    public String paymentDo(){
        return "payment";
    }

    @PostMapping("/payMoney")
    public String paymentDataMapping(@ModelAttribute("order") Order order) {
        System.out.println(order.getPrice());
        System.out.println(order.getCurrency());
        System.out.println(order.getIntent());
        System.out.println(order.getDescription());
        System.out.println(order.getMethod());
        System.out.println("http://localhost:8080/"+CANCEL);
        System.out.println("http://localhost:8080/"+SUCCESS);
        try {
            Payment paymentData = service.createPaymentService(order.getPrice(), order.getCurrency(), order.getMethod(),
                    order.getIntent(), order.getDescription(), "http://localhost:8080/"+CANCEL,
                    "http://localhost:8080/"+SUCCESS);

            for(Links link:paymentData.getLinks()) {
                if(link.getRel().equals("approval_url")) {
                    return "redirect:"+link.getHref();
                }
            }

        } catch (PayPalRESTException e) {
            e.printStackTrace();
        }
        return "redirect:/";
    }

    @GetMapping(value = CANCEL)
    public String cancelPay() {
        return "failure";
    }

    @GetMapping(value = SUCCESS)
    public String successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId) {
        try {
            Payment payment = service.processPayment(paymentId, payerId);
            System.out.println(payment.toJSON());
            if (payment.getState().equals("approved")) {
                return "paymentSuccess";
            }
        } catch (PayPalRESTException e) {
            System.out.println(e.getMessage());
        }
        return "redirect:/";
    }

}
