package com.scenario_projects.mq_back_stage.tests.exchange;

import com.scenario_projects.mq_back_stage.actioHelpers.*;
import com.scenario_projects.mq_back_stage.dataProvider.ExchangeOrderId;
import com.scenario_projects.mq_back_stage.dataProvider.Token;
import com.scenario_projects.mq_back_stage.dataProvider.TransactionHash;
import com.scenario_projects.mq_back_stage.endpoints.ExchangeEndpoints;
import com.scenario_projects.mq_back_stage.logging.TestListener;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners({TestListener.class})
public class N_0029_CreateExchangeOrderWrapToTokenTest {

    @BeforeMethod
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
        ResponseBody.GetResponseBodyAndStatusCode(response, 200);

        JsonPath jsonPathEvaluator = response.jsonPath();
        GetParametersFromResponses getParameters = new GetParametersFromResponses();
        int exchangeOrderId = getParameters.getOrderCreateResponse(jsonPathEvaluator, "exchangeOrderIdDB");

        ExchangeOrderId exchangeOrderIdValue = new ExchangeOrderId();
        exchangeOrderIdValue.setExchangeOrderID(exchangeOrderId);

        RunJSFiles runFileJS = new RunJSFiles();
        runFileJS.runJSfile("/home/ubuntu/.nvm/versions/node/v10.15.2/bin/node src/test/java/resources/swapOnFront.js");
    }

    @Test(dependsOnMethods = "createExchangeOrder")
    public void sendExchangeIdAndTransactionHashToBack() {

        JSONObject requestParams = new JSONObject()
                .put("exchangeOrderId", ExchangeOrderId.getExchangeOrderID())
                .put("txHash", TransactionHash.getHash());

        RequestSpecification request = RestAssured.given()
                .header("Content-Type", "application/json")
                .header("Authorization", Token.getToken())
                .body(requestParams.toString());

        Response response = request.post(ExchangeEndpoints.handleExchangeOrderTransactionHash);
        ResponseBody.GetResponseBodyAndStatusCode(response, 200);
    }
}
