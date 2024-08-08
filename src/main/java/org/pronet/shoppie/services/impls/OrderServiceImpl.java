package org.pronet.shoppie.services.impls;

import jakarta.mail.MessagingException;
import org.pronet.shoppie.entities.Cart;
import org.pronet.shoppie.entities.Order;
import org.pronet.shoppie.entities.OrderAddress;
import org.pronet.shoppie.entities.OrderRequest;
import org.pronet.shoppie.repositories.CartRepository;
import org.pronet.shoppie.repositories.OrderRepository;
import org.pronet.shoppie.services.OrderService;
import org.pronet.shoppie.utils.AccountUtils;
import org.pronet.shoppie.utils.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
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
    @Autowired
    private AccountUtils accountUtils;

    @Override
    public void saveOrder(Long userId, OrderRequest orderRequest) throws MessagingException, UnsupportedEncodingException {
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
            Order savedOrder = orderRepository.save(order);
            accountUtils.sendMailForOrderProcess(savedOrder, "Successful!");
        }
    }

    @Override
    public Order getByOrderId(String orderId) {
        return orderRepository.findByOrderId(orderId);
    }

    @Override
    public Page<Order> getList(Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return orderRepository.findAll(pageable);
    }

    @Override
    public Page<Order> getListByUser(Long id, Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return orderRepository.findByUserId(id, pageable);
    }

    @Override
    public Order updateOrderStatus(Long id, Integer status) throws MessagingException,
            UnsupportedEncodingException {
        OrderStatus[] orderStatusValueList = OrderStatus.values();
        String statusResult = null;
        for (OrderStatus orderStatus : orderStatusValueList) {
            if (orderStatus.getId().equals(status)) {
                statusResult = orderStatus.getName();
            }
        }
        return updateOrderStatusResult(id, statusResult);
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

    private Order updateOrderStatusResult(Long id, String statusResult)
            throws MessagingException, UnsupportedEncodingException {
        Optional<Order> foundedOrder = orderRepository.findById(id);
        if (foundedOrder.isPresent()) {
            Order order = foundedOrder.get();
            order.setStatus(statusResult);
            Order updatedOrder = orderRepository.save(order);
            accountUtils.sendMailForOrderProcess(updatedOrder, statusResult);
            return updatedOrder;
        }
        return null;
    }
}
