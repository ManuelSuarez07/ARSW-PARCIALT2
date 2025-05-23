package edu.eci.arsw.myrestaurant.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class Order {

    private Map<String, Integer> orderAmountsMap;
    private int tableNumber;

    public Order() {
    }

    public Order(int tableNumber) {
        orderAmountsMap = new ConcurrentHashMap<>();
        this.tableNumber = tableNumber;
    }

    public Map<String, Integer> getOrderAmountsMap() {
        return orderAmountsMap;
    }

    public void setOrderAmountsMap(Map<String, Integer> orderAmountsMap) {
        this.orderAmountsMap = orderAmountsMap;
    }

    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public void addDish(String p, int amount) {
        if (!orderAmountsMap.containsKey(p)) {
            orderAmountsMap.put(p, amount);
        } else {
            int previousAmount = orderAmountsMap.get(p);
            orderAmountsMap.put(p, previousAmount + amount);
        }
    }

    @JsonIgnore
    public Set<String> getOrderedDishes() {
        return orderAmountsMap.keySet();
    }

    public int getDishOrderedAmount(String p) {
        if (!orderAmountsMap.containsKey(p)) {
            return 0;
        } else {
            return orderAmountsMap.get(p);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Table " + tableNumber + "\n");
        getOrderedDishes().forEach((p) -> {
            sb.append(p).append(" x ").append(orderAmountsMap.get(p)).append("\n");
        });
        return sb.toString();
    }

    public Map<String, Object> toMap() {
        Map<String, Object> orderMap = new HashMap<>();
        orderMap.put("tableNumber", tableNumber);
        orderMap.put("orderAmountsMap", orderAmountsMap);
        return orderMap;
    }
}