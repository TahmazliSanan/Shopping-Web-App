package org.pronet.shoppie.services;

import org.pronet.shoppie.entities.Order;
import org.pronet.shoppie.entities.OrderRequest;

import java.util.List;

public interface OrderService {
    void saveOrder(Long userId, OrderRequest orderRequest);
    List<Order> getList();
    List<Order> getListByUser(Long id);
    Boolean updateOrderStatus(Long id, String status);
}
