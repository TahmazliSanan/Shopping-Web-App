package org.pronet.shoppie.services;

import org.pronet.shoppie.entities.OrderRequest;

public interface OrderService {
    void saveOrder(Long userId, OrderRequest orderRequest);
}
