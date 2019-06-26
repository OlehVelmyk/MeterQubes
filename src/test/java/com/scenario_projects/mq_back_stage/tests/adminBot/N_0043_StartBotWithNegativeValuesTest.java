package com.scenario_projects.mq_back_stage.tests.adminBot;

import com.scenario_projects.mq_back_stage.actioHelpers.ResponseBody;
import com.scenario_projects.mq_back_stage.dataProvider.MarketId;
import com.scenario_projects.mq_back_stage.dataProvider.Token;
import com.scenario_projects.mq_back_stage.endpoints.AdminAndBotEndpoints;
import com.scenario_projects.mq_back_stage.logging.TestListener;
import com.scenario_projects.mq_back_stage.model.BotModel;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners({TestListener.class})
public class N_0043_StartBotWithNegativeValuesTest {
    private double minPrice = -0.1;
    private double maxPrice = -0.9;
    private double priceGap = -0.1;
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
        ResponseBody.GetResponseBodyAndStatusCode(response, 500);
    }
}
