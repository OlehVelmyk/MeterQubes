package com.scenario_projects.mq_back_stage.tests.trade;

import com.scenario_projects.mq_back_stage.actioHelpers.AuthorizationHelper;
import com.scenario_projects.mq_back_stage.actioHelpers.FindUserByPublicAddressHelper;
import com.scenario_projects.mq_back_stage.actioHelpers.ResponseBody;
import com.scenario_projects.mq_back_stage.dataProvider.MarketId;
import com.scenario_projects.mq_back_stage.dataProvider.Token;
import com.scenario_projects.mq_back_stage.endpoints.TradeAndOrderBookEndpoints;
import com.scenario_projects.mq_back_stage.logging.CustomReporter;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class N_0008_GetUserTradeHistory {

    @BeforeMethod
    public void findUserByPublicAddressAndAuthorization() {
        FindUserByPublicAddressHelper findUserByPublicAddressHelper = new FindUserByPublicAddressHelper();
        findUserByPublicAddressHelper.findUserByPublicAddress();

        AuthorizationHelper authorizationHelper = new AuthorizationHelper();
        authorizationHelper.authorization();
    }

    @Test
    public void getUserTradeHistory() {
        RequestSpecification request = RestAssured.given()
                .header("Accept", "application/json")
                .header("Authorization", Token.getToken());

        Response response = request.get(TradeAndOrderBookEndpoints.getUserTradeHistoryByMarketId(MarketId.marketId));
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
