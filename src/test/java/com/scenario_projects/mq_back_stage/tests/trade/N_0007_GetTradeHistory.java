package com.scenario_projects.mq_back_stage.tests.trade;

import com.scenario_projects.mq_back_stage.actioHelpers.ResponseBody;
import com.scenario_projects.mq_back_stage.dataProvider.MarketId;
import com.scenario_projects.mq_back_stage.endpoints.TradeAndOrderBookEndpoints;
import com.scenario_projects.mq_back_stage.logging.CustomReporter;
import com.scenario_projects.mq_back_stage.logging.TestListener;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners({TestListener.class})
public class N_0007_GetTradeHistory {

    @Test
    public void getTradeHistory() {
        RequestSpecification request = RestAssured.given()
                .header("Accept", "application/json");

        Response response = request.get(TradeAndOrderBookEndpoints.getTradeHistoryByMarketId(MarketId.marketId));
        ResponseBody.GetResponseBodyAndStatusCode(response, 200);

        JsonPath jsonPathEvaluator = response.jsonPath();
        int marketIdFromResponse = Integer.parseInt(jsonPathEvaluator.get("marketId"));
        CustomReporter.logAction("Market Id received from Response is " + marketIdFromResponse);
        System.out.println("Market Id received from Response is " + marketIdFromResponse);
        Assert.assertEquals(marketIdFromResponse, MarketId.marketId);

        String data = jsonPathEvaluator.getMap("data").toString();
        CustomReporter.logAction("Data received from Response is " + data);
        System.out.println("Data received from Response is " + data);
        Assert.assertNotEquals(data, null);
    }
}
