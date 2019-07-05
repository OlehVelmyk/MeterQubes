package com.scenario_projects.mq_back_stage.tests.exchange;

import com.scenario_projects.mq_back_stage.actioHelpers.AuthorizationHelper;
import com.scenario_projects.mq_back_stage.actioHelpers.FindUserByPublicAddressHelper;
import com.scenario_projects.mq_back_stage.actioHelpers.GetParametersFromResponses;
import com.scenario_projects.mq_back_stage.actioHelpers.ResponseBody;
import com.scenario_projects.mq_back_stage.dataProvider.ExchangeOrderId;
import com.scenario_projects.mq_back_stage.dataProvider.Token;
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
public class N_0031_GetExchangeOrderDataByOrderIdTest {
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

    @BeforeClass(dependsOnMethods = "getExchangeTokenDataByTokenId")
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
    }

    @Test
    public void getExchangeOrderByOrderId() {
        RequestSpecification request = RestAssured.given()
                .header("Accept", "application/json")
                .header("Authorization", Token.getToken());

        Response response = request.get(ExchangeEndpoints.getExchangeOrderDataByExchangeOrderId(ExchangeOrderId.getExchangeOrderID()));
        ResponseBody.GetResponseBodyAndStatusCode(response, 200);

        JsonPath jsonPathEvaluator = response.jsonPath();
        int exchangeOrderId = jsonPathEvaluator.get("id");
        CustomReporter.logAction("exchangeOrderId  from Response is " + "'" + exchangeOrderId + "'");
        System.out.println("exchangeOrderId from Response is " + "'" + exchangeOrderId + "'");
        Assert.assertEquals(exchangeOrderId, ExchangeOrderId.getExchangeOrderID());
    }
}
