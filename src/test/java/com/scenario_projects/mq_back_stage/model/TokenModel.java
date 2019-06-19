package com.scenario_projects.mq_back_stage.model;

public class TokenModel {
    private static int amountDecimals;
    private static String baseToken;
    private static String baseTokenAddress;
    private static int baseTokenDecimals;
    private static String baseTokenName;
    private static String baseTokenProjectUrl;
    private static String marketOrderMaxSlippage;
    private static String minOrderSize;
    private static int priceDecimals;
    private static int pricePrecision;
    private static String[] supportedOrderTypes;

    public TokenModel(int amountDecimals, String baseToken, String baseTokenAddress, int baseTokenDecimals, String baseTokenName,
                      String baseTokenProjectUrl, String marketOrderMaxSlippage, String minOrderSize, int priceDecimals, int pricePrecision,
                      String[] supportedOrderTypes) {
        this.amountDecimals = amountDecimals;
        this.baseToken = baseToken;
        this.baseTokenAddress = baseTokenAddress;
        this.baseTokenDecimals = baseTokenDecimals;
        this.baseTokenName = baseTokenName;
        this.baseTokenProjectUrl = baseTokenProjectUrl;
        this.marketOrderMaxSlippage = marketOrderMaxSlippage;
        this.minOrderSize = minOrderSize;
        this.priceDecimals = priceDecimals;
        this.pricePrecision = pricePrecision;
        this.supportedOrderTypes = supportedOrderTypes;
    }

    public static int getAmountDecimals() {
        return amountDecimals;
    }

    public static String getBaseToken() {
        return baseToken;
    }

    public static String getBaseTokenAddress() {
        return baseTokenAddress;
    }

    public static int getBaseTokenDecimals() {
        return baseTokenDecimals;
    }

    public static String getBaseTokenName() {
        return baseTokenName;
    }

    public static String getBaseTokenProjectUrl() {
        return baseTokenProjectUrl;
    }

    public static String getMarketOrderMaxSlippage() {
        return marketOrderMaxSlippage;
    }

    public static String getMinOrderSize() {
        return minOrderSize;
    }

    public static int getPriceDecimals() {
        return priceDecimals;
    }

    public static int getPricePrecision() {
        return pricePrecision;
    }

    public static String[] getSupportedOrderTypes() {
        return supportedOrderTypes;
    }

    public static String getSupportedOrderTypesValue() {
        return supportedOrderTypes[0];
    }
}
