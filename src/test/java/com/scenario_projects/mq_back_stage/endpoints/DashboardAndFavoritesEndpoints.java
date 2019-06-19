package com.scenario_projects.mq_back_stage.endpoints;

public class DashboardAndFavoritesEndpoints extends BaseEndpoints {

    //Dashboard endpoints
    public static final String getLandingDashboardData = baseURL + "dashboard/landing";

    //Favorites endpoints
    public static final String getUserFavoritesMarkets = baseURL + "favorites";

    public static String getTradeDashboardData(int marketId) {
        return baseURL + "dashboard/trade/" + marketId;
    }

    public static String getChartDataForThePeriod(int marketId) {
        return baseURL + "chart?marketId=" + marketId;
    }

    public static String addAdnDeleteMarketToUserFavorites(int marketId) {
        return baseURL + "favorites/" + marketId;
    }
}
