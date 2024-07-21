package org.pronet.shoppie.services.impls;

import org.pronet.shoppie.entities.Cart;
import org.pronet.shoppie.entities.Order;
import org.pronet.shoppie.entities.OrderAddress;
import org.pronet.shoppie.entities.OrderRequest;
import org.pronet.shoppie.repositories.CartRepository;
import org.pronet.shoppie.repositories.OrderRepository;
import org.pronet.shoppie.services.OrderService;
import org.pronet.shoppie.utils.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CartRepository cartRepository;

    @Override
    public void saveOrder(Long userId, OrderRequest orderRequest) {
        List<Cart> cartList = cartRepository.findByUserId(userId);
        for (Cart cart : cartList) {
            Order order = new Order();
            order.setOrderId(UUID.randomUUID().toString());
            cart.setUser(cart.getUser());
            order.setProduct(cart.getProduct());
            order.setOrderDate(new Date());
            order.setPrice(cart.getProduct().getDiscountPrice());
            order.setQuantity(cart.getQuantity());
            order.setStatus(OrderStatus.IN_PROGRESS.getName());
            order.setPaymentType(orderRequest.getPaymentType());

            OrderAddress orderAddress = getOrderAddress(orderRequest);

            order.setOrderAddress(orderAddress);
            orderRepository.save(order);
        }
    }

    private static OrderAddress getOrderAddress(OrderRequest orderRequest) {
        OrderAddress orderAddress = new OrderAddress();
        orderAddress.setFirstName(orderRequest.getFirstName());
        orderAddress.setLastName(orderRequest.getLastName());
        orderAddress.setEmail(orderRequest.getEmail());
        orderAddress.setMobileNumber(orderRequest.getMobileNo());
        orderAddress.setAddress(orderRequest.getAddress());
        orderAddress.setCity(orderRequest.getCity());
        orderAddress.setState(orderRequest.getState());
        orderAddress.setPinCode(orderRequest.getPinCode());
        return orderAddress;
    }
}
