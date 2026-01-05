package kg.attractor.java;

import kg.attractor.java.homework.RestaurantOrders;
import kg.attractor.java.homework.domain.Order;

import java.util.List;
import java.util.Map;
import java.util.Set;
public class Main {

    public static void main(String[] args) {


        //ЧТЕНИЕ ЗАКАЗОВ
        var restaurantOrders = RestaurantOrders.read("orders_100.json");
        List<Order> orders = restaurantOrders.getOrders();

        System.out.println("Всего заказов: " + orders.size());
        System.out.println("====================================");


        //ПЕЧАТЬ ВСЕХ ЗАКАЗОВ
            System.out.println("Все заказы:");
        restaurantOrders.printOrders();
        System.out.println("====================================");

    }
}
