package kg.attractor.java.homework;

import java.util.*;
import java.util.stream.Collectors;

import com.google.gson.Gson;

import kg.attractor.java.homework.domain.Order;
import kg.attractor.java.homework.domain.Item;
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

    //----------------------------------------------------------------------
    //------Реализация ваших методов должна быть ниже этой линии   ------
    //----------------------------------------------------------------------

    // Наполните этот класс решением домашнего задания.
    // Вам необходимо создать все необходимые методы
    // для решения заданий из домашки :)
    // вы можете добавлять все необходимые imports
    //

    public Map<String, Integer> soldItemsCount() {
        Map<String, Integer> result = new HashMap<>();

        for (Order o : orders) {
            for (Item i : o.getItems()) {
                result.merge(i.getName(), i.getAmount(), Integer::sum);
            }
        }
        return result;
    }


    public String richestCustomer() {
        return totalByCustomer().entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
    }

    public String poorestCustomer() {
        return totalByCustomer().entrySet().stream()
                .min(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
    }


    public Map<String, Double> totalByCustomer() {
        return orders.stream()
                .collect(Collectors.groupingBy(
                        o -> o.getCustomer().getFullName(),
                        Collectors.summingDouble(Order::getTotal)
                ));
    }


    public Map<String, List<Order>> ordersByCustomerName() {
        return orders.stream()
                .collect(Collectors.groupingBy(
                        o -> o.getCustomer().getFullName()
                ));
    }


    public List<Order> ordersBetween(double min, double max) {
        return orders.stream()
                .filter(o -> o.getTotal() > min && o.getTotal() < max)
                .collect(Collectors.toList());
    }


    public double totalIncome() {
        return orders.stream()
                .mapToDouble(Order::getTotal)
                .sum();
    }

    public Set<String> uniqueSortedEmails() {
        Set<String> emails = new TreeSet<>();
        orders.forEach(o -> emails.add(o.getCustomer().getEmail()));
        return emails;
    }


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

}
