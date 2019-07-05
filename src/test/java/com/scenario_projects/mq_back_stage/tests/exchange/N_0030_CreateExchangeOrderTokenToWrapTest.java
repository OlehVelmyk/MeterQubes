package com.scenario_projects.mq_back_stage.tests.exchange;

import com.scenario_projects.mq_back_stage.actioHelpers.*;
import com.scenario_projects.mq_back_stage.dataProvider.ExchangeOrderId;
import com.scenario_projects.mq_back_stage.dataProvider.Token;
import com.scenario_projects.mq_back_stage.dataProvider.TransactionHash;
import com.scenario_projects.mq_back_stage.endpoints.ExchangeEndpoints;
import com.scenario_projects.mq_back_stage.logging.CustomReporter;
import com.scenario_projects.mq_back_stage.logging.TestListener;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(TestListener.class)
public class N_0030_CreateExchangeOrderTokenToWrapTest {
    private int tokenId = 12;
    private double tokenValue = 22;
    private double wethValue;

    @BeforeClass
    public void findUserByPublicAddressAndAuthorization() {
        FindUserByPublicAddressHelper findUserByPublicAddressHelper = new FindUserByPublicAddressHelper();
        findUserByPublicAddressHelper.findUserByPublicAddress();

        AuthorizationHelper authorizationHelper = new AuthorizationHelper();
        authorizationHelper.authorization();
    }

    @BeforeClass(dependsOnMethods = "findUserByPublicAddressAndAuthorization")
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

        wethValue = receivePriceToWETH;
    }

    @Test
    public void createExchangeOrder() {
        JSONObject requestParams = new JSONObject()
                .put("giveTokenId", 12)
                .put("giveAmount", String.valueOf(tokenValue))
                .put("receiveTokenId", 1)
                .put("receiveAmount", String.valueOf(tokenValue * wethValue));

        System.out.println("tokenValue * wethValue = " + tokenValue * wethValue);

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
        runFileJS.runJSfile("src/test/java/resources/node src/test/java/resources/swapOnFront.js");
    }

    @Test(dependsOnMethods = "createExchangeOrder")
    public void sendExchangeIdAndTransactionHashToBack() {

        JSONObject requestParams = new JSONObject()
                .put("exchangeOrderId", ExchangeOrderId.getExchangeOrderID())
                .put("txHash", TransactionHash.getHash().replace("8659", GenerateRandomDigits.generateRandomDigits()));

        RequestSpecification request = RestAssured.given()
                .header("Content-Type", "application/json")
                .header("Authorization", Token.getToken())
                .body(requestParams.toString());

        Response response = request.post(ExchangeEndpoints.handleExchangeOrderTransactionHash);
        ResponseBody.GetResponseBodyAndStatusCode(response, 204);
    }
}
