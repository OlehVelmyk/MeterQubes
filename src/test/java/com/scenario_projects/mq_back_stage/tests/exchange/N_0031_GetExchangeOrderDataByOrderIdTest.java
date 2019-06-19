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
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners({TestListener.class})
public class N_0031_GetExchangeOrderDataByOrderIdTest {

    @BeforeMethod
    public void findUserByPublicAddressAndAuthorization() {
        FindUserByPublicAddressHelper findUserByPublicAddressHelper = new FindUserByPublicAddressHelper();
        findUserByPublicAddressHelper.findUserByPublicAddress();

        AuthorizationHelper authorizationHelper = new AuthorizationHelper();
        authorizationHelper.authorization();
    }

    @BeforeMethod(dependsOnMethods = "findUserByPublicAddressAndAuthorization")
    public void createExchangeOrder() {

        JSONObject requestParams = new JSONObject()
                .put("giveTokenId", 12)
                .put("giveAmount", "0.012")
                .put("receiveTokenId", 1)
                .put("receiveAmount", "0.006");

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
