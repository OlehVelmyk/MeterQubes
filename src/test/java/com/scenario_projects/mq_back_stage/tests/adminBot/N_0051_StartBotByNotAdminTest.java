package com.scenario_projects.mq_back_stage.tests.adminBot;

import com.scenario_projects.mq_back_stage.actioHelpers.*;
import com.scenario_projects.mq_back_stage.dataProvider.BotValues;
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

public class N_0051_StartBotByNotAdminTest {

    @BeforeMethod
    public void findUserByPublicAddressAndAuthorization() {
        FindUserByPublicAddressHelper findUserByPublicAddressHelper = new FindUserByPublicAddressHelper();
        findUserByPublicAddressHelper.findUserByPublicAddress();

        AuthorizationHelper authorizationHelper = new AuthorizationHelper();
        authorizationHelper.authorization();
    }

    @Test
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
                .header("Authorization", Token.getToken())
                .body(requestParams.toString());

        Response response = request.post(AdminAndBotEndpoints.startLiqudityBot(MarketId.marketId));
        ResponseBody.GetResponseBodyAndStatusCode(response, 403);

        JsonPath jsonPathEvaluator = response.jsonPath();
        String errorMessage = jsonPathEvaluator.getString("error");
        CustomReporter.logAction("'Error message' parameter received from Response is " + "'" + errorMessage + "'");
        System.out.println("'Error message' parameter received from Response is " + "'" + errorMessage + "'");
        Assert.assertEquals(errorMessage, "Only user with role Admin has access");
    }
}
