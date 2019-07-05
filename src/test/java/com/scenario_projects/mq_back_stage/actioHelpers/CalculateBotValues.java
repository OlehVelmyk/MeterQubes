package com.scenario_projects.mq_back_stage.actioHelpers;

import com.scenario_projects.mq_back_stage.dataProvider.BotValues;
import com.scenario_projects.mq_back_stage.logging.CustomReporter;

public class CalculateBotValues {
    private double minPrice;
    private double maxPrice;
    private double priceGap;

    public void calculateBotValues() {

        minPrice = BotValues.getCenterPrice() / 4;
        BotValues.setMinPrice(minPrice);
        CustomReporter.logAction("minPrice = " + minPrice);
        System.out.println("minPrice = " + minPrice);

        maxPrice = BotValues.getCenterPrice() * 2;
        BotValues.setMaxPrice(maxPrice);
        CustomReporter.logAction("maxPrice = " + maxPrice);
        System.out.println("maxPrice = " + maxPrice);

        priceGap = BotValues.getCenterPrice() / 5;
        BotValues.setPriceGap(priceGap);
        CustomReporter.logAction("priceGap = " + priceGap);
        System.out.println("priceGap = " + priceGap);
    }
}
