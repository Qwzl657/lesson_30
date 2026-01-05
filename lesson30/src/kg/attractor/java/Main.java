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

        //ТОП 3 САМЫХ ДОРОГИХ
        System.out.println("Топ 3 самых дорогих заказа:");
        restaurantOrders.topMostExpensive(3)
                .forEach(o ->
                        System.out.printf("%.2f | %s%n",
                                o.getTotal(),
                                o.getCustomer().getEmail()
                        )
                );
        System.out.println("====================================");

        //ТОП 3 САМЫХ ДЕШЁВЫХ
        System.out.println("Топ 3 самых дешёвых заказа:");
        restaurantOrders.topCheapest(3)
                .forEach(o ->
                        System.out.printf("%.2f | %s%n",
                                o.getTotal(),
                                o.getCustomer().getEmail()
                        )
                );
        System.out.println("====================================");

        //ЗАКАЗЫ С ДОСТАВКОЙ
        System.out.println("Заказы с доставкой на дом:");
        restaurantOrders.homeDeliveryOrders()
                .forEach(o ->
                        System.out.println(o.getCustomer().getEmail())
                );
        System.out.println("====================================");

        //MAX / MIN ЗАКАЗ НА ДОМ
        System.out.println("Самый дорогой и самый дешёвый заказ на дом:");
        Map<String, Order> homeStats = restaurantOrders.bestAndWorstHomeDelivery();
        System.out.println("MAX: " + homeStats.get("max").getTotal());
        System.out.println("MIN: " + homeStats.get("min").getTotal());
        System.out.println("====================================");
        
        //ЗАКАЗЫ В ДИАПАЗОНЕ
        System.out.println("Заказы от 1000 до 3000:");
        restaurantOrders.ordersBetween(1000, 3000)
                .forEach(o -> System.out.println(o.getTotal()));
        System.out.println("====================================");

        //ОБЩАЯ СУММА
        System.out.println("Общая сумма всех заказов:");
        System.out.println(restaurantOrders.totalIncome());
        System.out.println("====================================");

        //УНИКАЛЬНЫЕ EMAIL
        System.out.println("Уникальные email клиентов:");
        Set<String> emails = restaurantOrders.uniqueSortedEmails();
        emails.forEach(System.out::println);
        System.out.println("====================================");
    }
}
