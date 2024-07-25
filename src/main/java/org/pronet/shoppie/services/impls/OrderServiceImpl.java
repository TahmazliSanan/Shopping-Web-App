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

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
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
            order.setUser(cart.getUser());
            order.setProduct(cart.getProduct());
            order.setOrderDate(LocalDate.now());
            order.setPrice(cart.getProduct().getDiscountPrice());
            order.setQuantity(cart.getQuantity());
            order.setStatus(OrderStatus.IN_PROGRESS.getName());
            order.setPaymentType(orderRequest.getPaymentType());

            OrderAddress orderAddress = getOrderAddress(orderRequest);

            order.setOrderAddress(orderAddress);
            orderRepository.save(order);
        }
    }

    @Override
    public List<Order> getList() {
        return orderRepository.findAll();
    }

    @Override
    public List<Order> getListByUser(Long id) {
        return orderRepository.findByUserId(id);
    }

    @Override
    public Boolean updateOrderStatus(Long id, String status) {
        Optional<Order> foundedOrder = orderRepository.findById(id);
        if (foundedOrder.isPresent()) {
            Order order = foundedOrder.get();
            order.setStatus(status);
            orderRepository.save(order);
            return true;
        }
        return false;
    }

    private static OrderAddress getOrderAddress(OrderRequest orderRequest) {
        OrderAddress orderAddress = new OrderAddress();
        orderAddress.setFirstName(orderRequest.getFirstName());
        orderAddress.setLastName(orderRequest.getLastName());
        orderAddress.setEmail(orderRequest.getEmail());
        orderAddress.setMobileNumber(orderRequest.getMobileNumber());
        orderAddress.setAddress(orderRequest.getAddress());
        orderAddress.setCity(orderRequest.getCity());
        orderAddress.setState(orderRequest.getState());
        orderAddress.setPinCode(orderRequest.getPinCode());
        return orderAddress;
    }
}
