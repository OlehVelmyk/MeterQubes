package com.scenario_projects.mq_back_stage.dataProvider;

public class BotValues {
    private static double minPrice;
    private static double maxPrice;
    private static double priceGap;
    private static double centerPrice;
    private static int expendInventory = 1;

    public static double getMinPrice() {
        return minPrice;
    }

    public static double getMaxPrice() {
        return maxPrice;
    }

    public static double getPriceGap() {
        return priceGap;
    }

    public static int getExpendInventory() {
        return expendInventory;
    }

    public static double getCenterPrice() {
        return centerPrice;
    }

    public static void setMinPrice(double minPrice) {
        BotValues.minPrice = minPrice;
    }

    public static void setMaxPrice(double maxPrice) {
        BotValues.maxPrice = maxPrice;
    }

    public static void setPriceGap(double priceGap) {
        BotValues.priceGap = priceGap;
    }

    public static void setCenterPrice(double centerPrice) {
        BotValues.centerPrice = centerPrice;
    }

    public static void setExpendInventory(int expendInventory) {
        BotValues.expendInventory = expendInventory;
    }
}