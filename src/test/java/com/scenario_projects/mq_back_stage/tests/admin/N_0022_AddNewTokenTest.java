package com.scenario_projects.mq_back_stage.tests.admin;

import com.scenario_projects.mq_back_stage.actioHelpers.GetParametersFromResponses;
import com.scenario_projects.mq_back_stage.actioHelpers.GetTradeFeesHelper;
import com.scenario_projects.mq_back_stage.actioHelpers.ResponseBody;
import com.scenario_projects.mq_back_stage.dataProvider.DateProvider;
import com.scenario_projects.mq_back_stage.dataProvider.FeeRate;
import com.scenario_projects.mq_back_stage.dataProvider.Token;
import com.scenario_projects.mq_back_stage.endpoints.AdminAndBotEndpoints;
import com.scenario_projects.mq_back_stage.logging.CustomReporter;
import com.scenario_projects.mq_back_stage.logging.TestListener;
import com.scenario_projects.mq_back_stage.model.TokenModel;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(TestListener.class)
public class N_0022_AddNewTokenTest {

    @BeforeMethod
    public void getTradeFees() {
        GetTradeFeesHelper getTradeFeesHelper = new GetTradeFeesHelper();
        getTradeFeesHelper.getTradeFees();
    }

    @Test()
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
        getParameters.checkIntNewTokenResponse(jsonPathEvaluator, "amountDecimals", TokenModel.getAmountDecimals());
        getParameters.checkStringNewTokenResponse(jsonPathEvaluator, "baseToken", TokenModel.getBaseToken());
        getParameters.checkStringNewTokenResponse(jsonPathEvaluator, "baseTokenAddress", TokenModel.getBaseTokenAddress());
        getParameters.checkIntNewTokenResponse(jsonPathEvaluator, "baseTokenDecimals", TokenModel.getBaseTokenDecimals());
        getParameters.checkStringNewTokenResponse(jsonPathEvaluator, "baseTokenName", TokenModel.getBaseTokenName());
        getParameters.checkStringNewTokenResponse(jsonPathEvaluator, "baseTokenProjectUrl", TokenModel.getBaseTokenProjectUrl());
        getParameters.checkStringNewTokenResponse(jsonPathEvaluator, "marketOrderMaxSlippage", TokenModel.getMarketOrderMaxSlippage());
        getParameters.checkStringNewTokenResponse(jsonPathEvaluator, "minOrderSize", TokenModel.getMinOrderSize());
        getParameters.checkIntNewTokenResponse(jsonPathEvaluator, "priceDecimals", TokenModel.getPriceDecimals());
        getParameters.checkIntNewTokenResponse(jsonPathEvaluator, "pricePrecision", TokenModel.getPricePrecision());

        String supportedOrderTypes = jsonPathEvaluator.getList("supportedOrderTypes").toString().
                replace("[", "").replace("]", "");
        CustomReporter.logAction("'supportedOrderTypes' parameter received from Response is " + supportedOrderTypes);
        System.out.println("'supportedOrderTypes' parameter received from Response is " + supportedOrderTypes);
        Assert.assertEquals(supportedOrderTypes, TokenModel.getSupportedOrderTypesValue());

        getParameters.getFeeRateResponse(jsonPathEvaluator, "asMakerFeeRate", FeeRate.getAsMakerFeeRate());
        getParameters.getFeeRateResponse(jsonPathEvaluator, "asTakerFeeRate", FeeRate.getAsTakerFeeRate());
    }
}
