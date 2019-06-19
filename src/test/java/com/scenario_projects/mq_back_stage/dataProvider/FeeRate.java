package com.scenario_projects.mq_back_stage.dataProvider;

public class FeeRate {
    private static double asMakerFeeRate ;
    private static double asTakerFeeRate ;

    public static double getAsMakerFeeRate() {
        return asMakerFeeRate;
    }

    public static double getAsTakerFeeRate() {
        return asTakerFeeRate;
    }

    public static void setAsMakerFeeRate(double asMakerFeeRate) {
        FeeRate.asMakerFeeRate = asMakerFeeRate;
    }

    public static void setAsTakerFeeRate(double asTakerFeeRate) {
        FeeRate.asTakerFeeRate = asTakerFeeRate;
    }
}
