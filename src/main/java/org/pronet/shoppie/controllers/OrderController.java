package org.pronet.shoppie.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OrderController {
    @GetMapping("/order-cart")
    public String orderPage() {
        return "/order/order";
    }
}
