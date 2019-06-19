package com.scenario_projects.mq_back_stage.model;

public class BotModel {
    private static double minPrice;
    private static double maxPrice;
    private static double priceGap;
    private static int expandInventory;

    public BotModel(double minPrice, double maxPrice, double priceGap, int expandInventory) {
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.priceGap = priceGap;
        this.expandInventory = expandInventory;
    }

    public static double getMinPrice() {
        return minPrice;
    }

    public static double getMaxPrice() {
        return maxPrice;
    }

    public static double getPriceGap() {
        return priceGap;
    }

    public static int getExpandInventory() {
        return expandInventory;
    }
}
