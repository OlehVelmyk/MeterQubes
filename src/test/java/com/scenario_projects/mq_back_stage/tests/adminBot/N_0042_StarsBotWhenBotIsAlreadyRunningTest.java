package com.scenario_projects.mq_back_stage.tests.adminBot;

import com.scenario_projects.mq_back_stage.actioHelpers.GetParametersFromResponses;
import com.scenario_projects.mq_back_stage.actioHelpers.ResponseBody;
import com.scenario_projects.mq_back_stage.dataProvider.MarketId;
import com.scenario_projects.mq_back_stage.dataProvider.Token;
import com.scenario_projects.mq_back_stage.endpoints.AdminAndBotEndpoints;
import com.scenario_projects.mq_back_stage.logging.CustomReporter;
import com.scenario_projects.mq_back_stage.model.BotModel;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class N_0042_StarsBotWhenBotIsAlreadyRunningTest {
    private double minPrice = 0.000000000006;
    private double maxPrice = 0.00000000003;
    private double priceGap = 0.000000000009;
    private int expendInventory = 1;

    @BeforeMethod
    public void startBot() {
        BotModel botModel = new BotModel(minPrice, maxPrice, priceGap, expendInventory);
        JSONObject requestParams = new JSONObject()
                .put("minPrice", BotModel.getMinPrice())
                .put("maxPrice", BotModel.getMaxPrice())
                .put("priceGap", BotModel.getPriceGap())
                .put("expandInventory", BotModel.getExpandInventory());

        RequestSpecification request = RestAssured.given()
                .header("Content-Type", "application/json")
                .header("Authorization", Token.getAdminToken())
                .body(requestParams.toString());

        Response response = request.post(AdminAndBotEndpoints.startLiqudityBot(MarketId.marketId));
        ResponseBody.GetResponseBodyAndStatusCode(response, 204);
    }

    @BeforeMethod(dependsOnMethods = "startBot")
    public void getBotData() {
        RequestSpecification request = RestAssured.given()
                .header("Accept", "application/json")
                .header("Authorization", Token.getAdminToken());

        Response response = request.get(AdminAndBotEndpoints.getBotDataByMarketId(MarketId.marketId));
        ResponseBody.GetResponseBodyAndStatusCode(response, 200);

        JsonPath jsonPathEvaluator = response.jsonPath();
        GetParametersFromResponses getParametersFromResponses = new GetParametersFromResponses();
        getParametersFromResponses.checkIntNewTokenResponse(jsonPathEvaluator, "marketId", MarketId.marketId);

        boolean status = jsonPathEvaluator.getBoolean("isRunning");
        CustomReporter.logAction("'isRunning' parameter received from Response is " + status);
        System.out.println("'isRunning' parameter received from Response is " + status);
        Assert.assertTrue(status);
    }

    @Test
    public void startBotAgain() {
        BotModel botModel = new BotModel(0.1, 0.9, 0.1, 1);
        JSONObject requestParams = new JSONObject()
                .put("minPrice", BotModel.getMinPrice())
                .put("maxPrice", BotModel.getMaxPrice())
                .put("priceGap", BotModel.getPriceGap())
                .put("expandInventory", BotModel.getExpandInventory());

        RequestSpecification request = RestAssured.given()
                .header("Content-Type", "application/json")
                .header("Authorization", Token.getAdminToken())
                .body(requestParams.toString());

        Response response = request.post(AdminAndBotEndpoints.startLiqudityBot(MarketId.marketId));
        ResponseBody.GetResponseBodyAndStatusCode(response, 500);
    }
}
