package com.scenario_projects.mq_back_stage.endpoints;

public class MarketsEndpoints extends BaseEndpoints {

    public static final String getAllMarkets = baseURL + "markets";

    public static String getMarketsByTokenSymbol(String tokenSymbol) {
        return baseURL + "markets?token=" + tokenSymbol;
    }

    public static String getMarketByMarketId(int marketId) {
        return baseURL + "markets/" + marketId;
    }
}
