package com.scenario_projects.mq_back_stage.tests.admin;

import com.scenario_projects.mq_back_stage.actioHelpers.GetParametersFromResponses;
import com.scenario_projects.mq_back_stage.actioHelpers.ResponseBody;
import com.scenario_projects.mq_back_stage.dataProvider.DateProvider;
import com.scenario_projects.mq_back_stage.dataProvider.Token;
import com.scenario_projects.mq_back_stage.endpoints.AdminAndBotEndpoints;
import com.scenario_projects.mq_back_stage.model.TokenModel;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class N_0025_AddNewTokenWithNotUniqueBaseTokenUrlTest {

    @BeforeMethod
    public void addNewToken() {
        TokenModel tokenModel = new TokenModel(2, "TEST" + DateProvider.currentTime(),
                "0xdaf16fad57bc1d43a57695d1e724013" + DateProvider.currentTime(), 18, "My token",
                "https://ropsten.etherscan.io", "0.20000", "1.01",
                8, 5, new String[]{"LIMIT"});

        JSONObject requestParams = new JSONObject()
                .put("amountDecimals", TokenModel.getAmountDecimals())
                .put("baseToken", TokenModel.getBaseToken())
                .put("baseTokenAddress", TokenModel.getBaseTokenAddress())
                .put("baseTokenDecimals", TokenModel.getBaseTokenDecimals())
                .put("baseTokenName", TokenModel.getBaseTokenName())
                .put("baseTokenProjectUrl", TokenModel.getBaseTokenProjectUrl())
                .put("marketOrderMaxSlippage", TokenModel.getMarketOrderMaxSlippage())
                .put("minOrderSize", TokenModel.getMinOrderSize())
                .put("priceDecimals", TokenModel.getPriceDecimals())
                .put("pricePrecision", TokenModel.getPricePrecision())
                .put("supportedOrderTypes", TokenModel.getSupportedOrderTypes());

        RequestSpecification request = RestAssured.given()
                .header("Content-Type", "application/json")
                .header("Authorization", Token.getAdminToken())
                .body(requestParams.toString());

        Response response = request.post(AdminAndBotEndpoints.addNewToken);
        ResponseBody.GetResponseBodyAndStatusCode(response, 200);

        JsonPath jsonPathEvaluator = response.jsonPath();

        GetParametersFromResponses getParameters = new GetParametersFromResponses();
        getParameters.getOrderCreateResponse(jsonPathEvaluator,"id");
        getParameters.checkStringNewTokenResponse(jsonPathEvaluator, "baseTokenAddress", TokenModel.getBaseTokenAddress());
    }

    @Test
    public void checkAddNewTokenWithNotUniqueBaseTokenUrl() {
        JSONObject requestParams = new JSONObject()
                .put("amountDecimals", TokenModel.getAmountDecimals())
                .put("baseToken", "TEST" + DateProvider.currentTime())
                .put("baseTokenAddress", TokenModel.getBaseTokenAddress())
                .put("baseTokenDecimals", TokenModel.getBaseTokenDecimals())
                .put("baseTokenName", TokenModel.getBaseTokenName())
                .put("baseTokenProjectUrl", TokenModel.getBaseTokenProjectUrl())
                .put("marketOrderMaxSlippage", TokenModel.getMarketOrderMaxSlippage())
                .put("minOrderSize", TokenModel.getMinOrderSize())
                .put("priceDecimals", TokenModel.getPriceDecimals())
                .put("pricePrecision", TokenModel.getPricePrecision())
                .put("supportedOrderTypes", TokenModel.getSupportedOrderTypes());

        RequestSpecification request = RestAssured.given()
                .header("Content-Type", "application/json")
                .header("Authorization", Token.getAdminToken())
                .body(requestParams.toString());

        Response response = request.post(AdminAndBotEndpoints.addNewToken);
        ResponseBody.GetResponseBodyAndStatusCode(response, 500);
    }
}
