package org.pronet.shoppie.services;

import jakarta.mail.MessagingException;
import org.pronet.shoppie.entities.Order;
import org.pronet.shoppie.entities.OrderRequest;

import java.io.UnsupportedEncodingException;
import java.util.List;

public interface OrderService {
    void saveOrder(Long userId, OrderRequest orderRequest) throws MessagingException, UnsupportedEncodingException;
    Order getByOrderId(String orderId);
    List<Order> getList();
    List<Order> getListByUser(Long id);
    Order updateOrderStatus(Long id, Integer status) throws MessagingException, UnsupportedEncodingException;
}
