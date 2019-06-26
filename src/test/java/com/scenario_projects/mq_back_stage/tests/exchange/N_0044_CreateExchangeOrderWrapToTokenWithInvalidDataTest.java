package com.scenario_projects.mq_back_stage.tests.exchange;

import com.scenario_projects.mq_back_stage.actioHelpers.*;
import com.scenario_projects.mq_back_stage.dataProvider.Token;
import com.scenario_projects.mq_back_stage.endpoints.ExchangeEndpoints;
import com.scenario_projects.mq_back_stage.logging.TestListener;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners({TestListener.class})
public class N_0044_CreateExchangeOrderWrapToTokenWithInvalidDataTest {

    @BeforeClass
    public void findUserByPublicAddressAndAuthorization() {
        FindUserByPublicAddressHelper findUserByPublicAddressHelper = new FindUserByPublicAddressHelper();
        findUserByPublicAddressHelper.findUserByPublicAddress();

        AuthorizationHelper authorizationHelper = new AuthorizationHelper();
        authorizationHelper.authorization();
    }

    @Test
    public void createExchangeOrder() {
        JSONObject requestParams = new JSONObject()
                .put("giveTokenId", 1)
                .put("giveAmount", "0.005")
                .put("receiveTokenId", 12)
                .put("receiveAmount", "0.01");

        RequestSpecification request = RestAssured.given()
                .header("Content-Type", "application/json")
                .header("Authorization", Token.getToken())
                .body(requestParams.toString());

        Response response = request.post(ExchangeEndpoints.createExchangeOrder);
        ResponseBody.GetResponseBodyAndStatusCode(response, 400);
    }
}
