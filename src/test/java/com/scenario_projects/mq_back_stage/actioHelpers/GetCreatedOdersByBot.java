package com.scenario_projects.mq_back_stage.actioHelpers;

import com.google.gson.Gson;
import com.scenario_projects.mq_back_stage.model.OrderBook;

import java.util.ArrayList;
import java.util.List;

public class GetCreatedOdersByBot {
    Gson gson = new Gson();

    public void getCreatedBuyOrdersByBot(ArrayList<Double> buyOrdersPrice, List<Object> buyResponse, double minPrice, double centerPrice) {
        for (int i = 0; i < buyResponse.size(); i++) {
            String toJson = gson.toJson(buyResponse.get(i));
            //System.out.println("toJson = " + toJson);

            OrderBook orderBook = gson.fromJson(toJson, OrderBook.class);
            //System.out.println("orderBook.getPrice() = " + orderBook.getPrice());

            if (minPrice <= orderBook.getPrice() && orderBook.getPrice() < centerPrice) {
                buyOrdersPrice.add(orderBook.getPrice());
            }
        }
    }

    public void getCreatedSellOrdersByBot(ArrayList<Double> sellOrdersPrice, List<Object> sellResponse, double maxPrice, double centerPrice) {
        for (int i = 0; i < sellResponse.size(); i++) {
            String toJson = gson.toJson(sellResponse.get(i));
            //System.out.println("toJson = " + toJson);

            OrderBook orderBook = gson.fromJson(toJson, OrderBook.class);
            //System.out.println("orderBook.getPrice() = " + orderBook.getPrice());

            if (centerPrice < orderBook.getPrice() && orderBook.getPrice() <= maxPrice) {
                sellOrdersPrice.add(orderBook.getPrice());
            }
        }
    }
}
