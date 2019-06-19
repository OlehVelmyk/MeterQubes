package com.scenario_projects.mq_back_stage.endpoints;

public class BaseEndpoints {
    public static final String baseURL = "https://mq-back-stage.scenario-projects.com/api/v1/";

    public static final String auth = baseURL + "auth";

    //data endpoint
    public static final String getRelayer = baseURL + "data/relayer";

    //Ethereum endpoints
    public static final String getFeesWallets = baseURL + "eth/fees-wallet";
}
