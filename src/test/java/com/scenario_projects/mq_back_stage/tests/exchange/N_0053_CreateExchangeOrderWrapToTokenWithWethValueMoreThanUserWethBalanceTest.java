package com.scenario_projects.mq_back_stage.tests.exchange;

import com.scenario_projects.mq_back_stage.actioHelpers.AuthorizationHelper;
import com.scenario_projects.mq_back_stage.actioHelpers.FindUserByPublicAddressHelper;
import com.scenario_projects.mq_back_stage.actioHelpers.ResponseBody;
import com.scenario_projects.mq_back_stage.dataProvider.Token;
import com.scenario_projects.mq_back_stage.endpoints.ExchangeEndpoints;
import com.scenario_projects.mq_back_stage.logging.CustomReporter;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class N_0053_CreateExchangeOrderWrapToTokenWithWethValueMoreThanUserWethBalanceTest {
    private int tokenId = 12;
    private double wethValue = 100;
    private double tokenValue;

    @BeforeMethod
    public void findUserByPublicAddressAndAuthorization() {
        FindUserByPublicAddressHelper findUserByPublicAddressHelper = new FindUserByPublicAddressHelper();
        findUserByPublicAddressHelper.findUserByPublicAddress();

        AuthorizationHelper authorizationHelper = new AuthorizationHelper();
        authorizationHelper.authorization();
    }

    @BeforeMethod(dependsOnMethods = "findUserByPublicAddressAndAuthorization")
    public void getExchangeTokenDataByTokenId() {
        RequestSpecification request = RestAssured.given()
                .header("Accept", "application/json")
                .header("Authorization", Token.getToken());

        Response response = request.get(ExchangeEndpoints.getExchangeTokenDataByTokenId(tokenId));
        ResponseBody.GetResponseBodyAndStatusCode(response, 200);

        JsonPath jsonPathEvaluator = response.jsonPath();
        int exchangeTokenId = jsonPathEvaluator.get("id");
        CustomReporter.logAction("Token id from Response is " + "'" + exchangeTokenId + "'");
        System.out.println("Token id from Response is " + "'" + exchangeTokenId + "'");
        Assert.assertEquals(exchangeTokenId, tokenId);

        double receivePriceToWETH = Double.parseDouble(jsonPathEvaluator.getString("currentPriceToWETH"));
        CustomReporter.logAction("receivePriceToWETH from Response is " + "'" + receivePriceToWETH + "'");
        System.out.println("receivePriceToWETH from Response is " + "'" + receivePriceToWETH + "'");
        Assert.assertNotEquals(receivePriceToWETH, 0);

        tokenValue = receivePriceToWETH;
    }

    @Test
    public void createExchangeOrder() {
        JSONObject requestParams = new JSONObject()
                .put("giveTokenId", 1)
                .put("giveAmount", String.valueOf(wethValue))
                .put("receiveTokenId", 12)
                .put("receiveAmount", String.valueOf(wethValue/tokenValue));

        RequestSpecification request = RestAssured.given()
                .header("Content-Type", "application/json")
                .header("Authorization", Token.getToken())
                .body(requestParams.toString());

        Response response = request.post(ExchangeEndpoints.createExchangeOrder);
        ResponseBody.GetResponseBodyAndStatusCode(response, 400);
    }
}
