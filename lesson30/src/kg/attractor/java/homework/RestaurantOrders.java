package kg.attractor.java.homework;

import java.util.*;
import java.util.stream.Collectors;

import com.google.gson.Gson;

import kg.attractor.java.homework.domain.Order;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class RestaurantOrders {
    // Этот блок кода менять нельзя! НАЧАЛО!
    private List<Order> orders;

    private RestaurantOrders(String fileName) {
        var filePath = Path.of("data", fileName);
        Gson gson = new Gson();
        try {
            orders = List.of(gson.fromJson(Files.readString(filePath), Order[].class));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static RestaurantOrders read(String fileName) {
        var ro = new RestaurantOrders(fileName);
        ro.getOrders().forEach(Order::calculateTotal);
        return ro;
    }

    public List<Order> getOrders() {
        return orders;
    }
    // Этот блок кода менять нельзя! КОНЕЦ!


    public Map<String, Order> bestAndWorstHomeDelivery() {
        Order max = null;
        Order min = null;

        for (Order o : orders) {
            if (!o.isHomeDelivery()) continue;

            if (max == null || o.getTotal() > max.getTotal()) {
                max = o;
            }
            if (min == null || o.getTotal() < min.getTotal()) {
                min = o;
            }
        }

        Map<String, Order> result = new HashMap<>();
        result.put("max", max);
        result.put("min", min);
        return result;
    }


    public List<Order> homeDeliveryOrders() {
        return orders.stream()
                .filter(Order::isHomeDelivery)
                .collect(Collectors.toList());
    }


    public List<Order> topMostExpensive(int n) {
        return orders.stream()
                .sorted((a, b) -> Double.compare(b.getTotal(), a.getTotal()))
                .limit(n)
                .collect(Collectors.toList());
    }

    public List<Order> topCheapest(int n) {
        return orders.stream()
                .sorted(Comparator.comparingDouble(Order::getTotal))
                .limit(n)
                .collect(Collectors.toList());
    }


    public void printOrders() {
        orders.forEach(o ->
                System.out.printf(
                        "%s | total: %.2f | home: %s%n",
                        o.getCustomer().getEmail(),
                        o.getTotal(),
                        o.isHomeDelivery()
                )
        );
    }
    //----------------------------------------------------------------------
    //------   Реализация ваших методов должна быть ниже этой линии   ------
    //----------------------------------------------------------------------

    // Наполните этот класс решением домашнего задания.
    // Вам необходимо создать все необходимые методы
    // для решения заданий из домашки :)
    // вы можете добавлять все необходимые imports
    //
}
