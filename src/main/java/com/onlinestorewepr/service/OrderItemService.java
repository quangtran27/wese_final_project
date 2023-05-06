package com.onlinestorewepr.service;

import com.onlinestorewepr.dao.OrderItemDAO;
import com.onlinestorewepr.entity.Category;

public class OrderItemService {
    public void delete(int id) {
        OrderItemDAO orderItemDAO =new OrderItemDAO();
        orderItemDAO.delete(id);
    }
}
