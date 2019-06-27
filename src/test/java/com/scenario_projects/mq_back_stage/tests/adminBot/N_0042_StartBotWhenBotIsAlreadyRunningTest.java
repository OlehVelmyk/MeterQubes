package com.scenario_projects.mq_back_stage.tests.adminBot;

import com.scenario_projects.mq_back_stage.actioHelpers.*;
import com.scenario_projects.mq_back_stage.dataProvider.BotValues;
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
import org.testng.annotations.*;

@Listeners(TestListener.class)
public class N_0042_StartBotWhenBotIsAlreadyRunningTest {

    @BeforeClass
    public void startBot() {
        //Get center price
        GetCenterPriceHelper getCenterPriceHelper = new GetCenterPriceHelper();
        getCenterPriceHelper.getCenterPrice();

        //Calculate max price, max price and price gap
        CalculateBotValues calculateBotValues = new CalculateBotValues();
        calculateBotValues.calculateBotValues();

        BotModel botModel = new BotModel(BotValues.getMinPrice(), BotValues.getMaxPrice(), BotValues.getPriceGap(), BotValues.getExpendInventory());
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

    @BeforeClass(dependsOnMethods = "startBot")
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
        BotModel botModel = new BotModel(BotValues.getMinPrice(), BotValues.getMaxPrice(), BotValues.getPriceGap(), BotValues.getExpendInventory());
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

    @AfterClass
    public void stopBot() {
        RequestSpecification request = RestAssured.given()
                .header("Accept", "application/json")
                .header("Authorization", Token.getAdminToken());

        Response response = request.patch(AdminAndBotEndpoints.stopLiqudityBot(MarketId.marketId));
        ResponseBody.GetResponseBodyAndStatusCode(response, 204);
    }
}
