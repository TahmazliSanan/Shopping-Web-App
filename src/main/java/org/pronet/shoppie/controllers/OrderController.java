package org.pronet.shoppie.controllers;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpSession;
import org.pronet.shoppie.entities.Cart;
import org.pronet.shoppie.entities.Order;
import org.pronet.shoppie.entities.OrderRequest;
import org.pronet.shoppie.entities.UserEntity;
import org.pronet.shoppie.services.CartService;
import org.pronet.shoppie.services.OrderService;
import org.pronet.shoppie.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController extends BaseController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;
    @Autowired
    private CartService cartService;

    @Override
    public void getUserDetails(Principal principal, Model model) {
        super.getUserDetails(principal, model);
    }

    private UserEntity getLoggedInUserDetails(Principal principal) {
        if (principal != null) {
            String email = principal.getName();
            return userService.getUserByEmail(email);
        } else {
            return null;
        }
    }

    @GetMapping("/view")
    public String orderPage(Principal principal, Model model) {
        UserEntity user = getLoggedInUserDetails(principal);
        List<Cart> cartList = cartService.getListByUser(user.getId());
        model.addAttribute("cartList", cartList);
        if (!cartList.isEmpty()) {
            Double orderPrice = cartList.get(cartList.size() - 1).getTotalOrderPrice();
            Double totalOrderPrice = cartList.get(cartList.size() - 1).getTotalOrderPrice() + 250 + 100;
            model.addAttribute("orderPrice", orderPrice);
            model.addAttribute("totalOrderPrice", totalOrderPrice);
        }
        return "/order/order";
    }

    @PostMapping("/save")
    public String saveOrder(
            @ModelAttribute OrderRequest orderRequest,
            Principal principal) throws MessagingException, UnsupportedEncodingException {
        UserEntity user = getLoggedInUserDetails(principal);
        orderService.saveOrder(user.getId(), orderRequest);
        return "redirect:/order/success";
    }

    @GetMapping("/success")
    public String successPage() {
        return "/order/success";
    }

    @GetMapping("/my-orders")
    public String myOrdersPage(
            @RequestParam(name = "pageNumber", defaultValue = "0") Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "100") Integer pageSize,
            @RequestParam(defaultValue = "") String character,
            Model model,
            Principal principal) {
        UserEntity user = getLoggedInUserDetails(principal);
        Page<Order> page;
        if (character != null && !character.isEmpty()) {
            page = orderService.searchOrder(pageNumber, pageSize, character.trim());
        } else {
            page = orderService.getListByUser(user.getId(), pageNumber, pageSize);
        }
        List<Order> orderList = page.getContent();
        model.addAttribute("orderList", orderList);
        model.addAttribute("orderListSize", orderList.size());
        model.addAttribute("pageNumber", page.getNumber());
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("totalElements", page.getTotalElements());
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("isFirstPage", page.isFirst());
        model.addAttribute("isLastPage", page.isLast());
        return "/order/my-orders";
    }

    @GetMapping("/update-status")
    public String updateOrderStatusPage(
            @RequestParam Long id,
            @RequestParam Integer status,
            HttpSession session) throws MessagingException, UnsupportedEncodingException {
        Order updatedOrder = orderService.updateOrderStatus(id, status);
        if (!ObjectUtils.isEmpty(updatedOrder)) {
            session.setAttribute("successMessage", "Status is updated successfully!");
        } else {
            session.setAttribute("errorMessage", "Status is not updated!");
        }
        return "redirect:/order/my-orders";
    }
}
