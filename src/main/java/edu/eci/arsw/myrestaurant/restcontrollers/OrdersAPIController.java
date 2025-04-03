/*
 * Copyright (C) 2016 Pivotal Software, Inc.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package edu.eci.arsw.myrestaurant.restcontrollers;

import edu.eci.arsw.myrestaurant.beans.impl.BasicBillCalculator;
import edu.eci.arsw.myrestaurant.beans.impl.BillWithTaxesCalculator;
import edu.eci.arsw.myrestaurant.model.Order;
import edu.eci.arsw.myrestaurant.services.RestaurantOrderServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/orders")
public class OrdersAPIController {
    @Autowired
    RestaurantOrderServices orderServices;
    @Autowired
    BasicBillCalculator billCalculator;

    @Autowired
    BillWithTaxesCalculator taxesCalculator;

    @GetMapping
    public List<Map<String, Object>> getAllOrders() {
        List<Order> orders = orderServices.getTableOrders().values().stream().collect(Collectors.toList());
        return orders.stream().map(order -> {
            Map<String, Object> orderMap = order.toMap();
            orderMap.put("total", billCalculator.calculateBill(order, orderServices.getProductsMap()));
            //orderMap.put("totalWithTaxes", taxesCalculator.calculateBill(order, orderServices.getProductsMap())); Me sale error
            return orderMap;
        }).collect(Collectors.toList());
    }
}