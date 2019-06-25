package com.scenario_projects.mq_back_stage.endpoints;

public class AdminAndBotEndpoints extends BaseEndpoints {
    public static final String getUsersWallets = baseURL + "admin/users/wallets";
    public static final String addNewToken = baseURL + "admin/token";
    public static final String getAdminTransactionList = baseURL + "admin/transactions/list";
    public static final String getTradeFees = baseURL + "admin/fees";
    public static final String updateTradeFees = baseURL + "admin/fees/trade";
    public static final String getCenterPrice = "https://min-api.cryptocompare.com/data/pricemultifull?fsyms=EMPR&tsyms=ETH";

    public static String getBotDataByMarketId(int marketId) {
        return baseURL + "bots/" + marketId;
    }

    public static String startLiqudityBot(int marketId) {
        return baseURL + "bots/start/" + marketId;
    }

    public static String stopLiqudityBot(int marketId) {
        return baseURL + "bots/stop/" + marketId;
    }
}
