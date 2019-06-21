package com.scenario_projects.mq_back_stage.tests.adminBot;

import com.scenario_projects.mq_back_stage.actioHelpers.GetParametersFromResponses;
import com.scenario_projects.mq_back_stage.actioHelpers.ResponseBody;
import com.scenario_projects.mq_back_stage.dataProvider.MarketId;
import com.scenario_projects.mq_back_stage.dataProvider.Token;
import com.scenario_projects.mq_back_stage.endpoints.AdminAndBotEndpoints;
import com.scenario_projects.mq_back_stage.logging.CustomReporter;
import com.scenario_projects.mq_back_stage.logging.TestListener;
import com.scenario_projects.mq_back_stage.model.BotModel;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners({TestListener.class})
public class N_0040_StartAndStopBotTest {
    private double minPrice = 0.000000000006;
    private double maxPrice = 0.00000000003;
    private double priceGap = 0.000000000009;
    private int expendInventory = 1;

    @Test
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

    @Test(dependsOnMethods = "startBot")
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

        getParametersFromResponses.getBotDataParameters(jsonPathEvaluator, "minPrice", BotModel.getMinPrice());
        getParametersFromResponses.getBotDataParameters(jsonPathEvaluator, "maxPrice", BotModel.getMaxPrice());
        getParametersFromResponses.getBotDataParameters(jsonPathEvaluator, "priceGap", BotModel.getPriceGap());

        int expandInventory = jsonPathEvaluator.getInt("expandInventory");
        CustomReporter.logAction("'expandInventory' parameter received from Response is " + expandInventory);
        System.out.println("'expandInventory' parameter received from Response is " + expandInventory);
        Assert.assertEquals(expandInventory, BotModel.getExpandInventory());
    }

    @Test(dependsOnMethods = "getBotData")
    public void stopBot() {
        RequestSpecification request = RestAssured.given()
                .header("Accept", "application/json")
                .header("Authorization", Token.getAdminToken());

        Response response = request.patch(AdminAndBotEndpoints.stopLiqudityBot(MarketId.marketId));
        ResponseBody.GetResponseBodyAndStatusCode(response, 204);
    }

    @AfterMethod
    public void getBotDataAgain() {
        RequestSpecification request = RestAssured.given()
                .header("Accept", "application/json")
                .header("Authorization", Token.getAdminToken());

        Response response = request.get(AdminAndBotEndpoints.getBotDataByMarketId(MarketId.marketId));
        ResponseBody.GetResponseBodyAndStatusCode(response, 200);

        JsonPath jsonPathEvaluator = response.jsonPath();
        GetParametersFromResponses getParametersFromResponses = new GetParametersFromResponses();
        getParametersFromResponses.checkIntNewTokenResponse(jsonPathEvaluator, "marketId", MarketId.marketId);

        boolean status = jsonPathEvaluator.getBoolean("isRunning");
        CustomReporter.logAction("Status parameter received from Response is " + status);
        System.out.println("Status parameter received from Response is " + status);
        Assert.assertFalse(status);
    }
}
