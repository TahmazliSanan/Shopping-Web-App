package org.pronet.shoppie.services;

import jakarta.mail.MessagingException;
import org.pronet.shoppie.entities.Order;
import org.pronet.shoppie.entities.OrderRequest;
import org.springframework.data.domain.Page;

import java.io.UnsupportedEncodingException;

public interface OrderService {
    void saveOrder(Long userId, OrderRequest orderRequest) throws MessagingException, UnsupportedEncodingException;
    Order getByOrderId(String orderId);
    Page<Order> getList(Integer pageNumber, Integer pageSize);
    Page<Order> searchOrder(Long id, Integer pageNumber, Integer pageSize, String character);
    Page<Order> getListByUser(Long id, Integer pageNumber, Integer pageSize);
    Order updateOrderStatus(Long id, Integer status) throws MessagingException, UnsupportedEncodingException;
}
