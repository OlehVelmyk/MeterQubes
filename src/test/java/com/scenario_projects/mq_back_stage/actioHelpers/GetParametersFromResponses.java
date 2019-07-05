package com.scenario_projects.mq_back_stage.actioHelpers;

import com.scenario_projects.mq_back_stage.logging.CustomReporter;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;

import java.text.DecimalFormat;
import java.util.List;

public class GetParametersFromResponses {

    public void getParameterFromList(JsonPath jsonPathEvaluator, String s) {
        String response = jsonPathEvaluator.getList(s).toString();
        CustomReporter.logAction("'" + s + "' " + "parameter received from Response is " + response);
        System.out.println("'" + s + "' " + "parameter received from Response is " + response);
        Assert.assertNotEquals(response, null);
    }

    public void getParameterFromString(JsonPath jsonPathEvaluator, String s) {
        String response = jsonPathEvaluator.getString(s);
        CustomReporter.logAction("'" + s + "' " + "parameter received from Response is " + response);
        System.out.println("'" + s + "' " + "parameter received from Response is " + response);
        Assert.assertNotEquals(response, null);
    }

    public void checkIntNewTokenResponse(JsonPath jsonPathEvaluator, String s, int tokenModel) {
        String response = jsonPathEvaluator.getString(s);
        CustomReporter.logAction("'" + s + "' " + "parameter received from Response is " + response);
        System.out.println("'" + s + "' " + "parameter received from Response is " + response);
        Assert.assertEquals(Integer.parseInt(response), tokenModel);
    }

    public void checkStringNewTokenResponse(JsonPath jsonPathEvaluator, String s, String tokenModel) {
        String response = jsonPathEvaluator.getString(s);
        CustomReporter.logAction("'" + s + "' " + "parameter received from Response is " + response);
        System.out.println("'" + s + "' " + "parameter received from Response is " + response);
        Assert.assertEquals(response, tokenModel);
    }

    public void getUserWalletsResponse(JsonPath jsonPathEvaluator, String s) {
        String response = jsonPathEvaluator.getString(s);
        CustomReporter.logAction("'" + s + "' " + "parameter received from Response is " + response);
        System.out.println("'" + s + "' " + "parameter received from Response is " + response);
        Assert.assertNotNull(response);
    }

    public double getTradeFeesResponse(JsonPath jsonPathEvaluator, String s) {
        String response = jsonPathEvaluator.getString(s);
        CustomReporter.logAction("'" + s + "' " + "parameter received from Response is " + response);
        System.out.println("'" + s + "' " + "parameter received from Response is " + response);
        Assert.assertTrue(Double.parseDouble(response) > 0);
        return Double.parseDouble(response);
    }

    public void getFeeRateResponse(JsonPath jsonPathEvaluator, String s, double feeRate) {
        String response = jsonPathEvaluator.getString(s);
        CustomReporter.logAction("'" + s + "' " + "parameter received from Response is " + response);
        System.out.println("'" + s + "' " + "parameter received from Response is " + response);
        Assert.assertEquals(Double.parseDouble(response), Double.parseDouble(new DecimalFormat("#0.00000").format(feeRate * 0.01)));
    }

    public int getOrderCreateResponse(JsonPath jsonPathEvaluator, String s) {
        int response = jsonPathEvaluator.get(s);
        CustomReporter.logAction("'" + s + "' " + "parameter received from Response is " + response);
        System.out.println("'" + s + "' " + "parameter received from Response is " + response);
        Assert.assertNotEquals(response, 0);
        return response;
    }

    public List<Object> getParametersFromListToList(JsonPath jsonPathEvaluator, String s) {
        List<Object> response = jsonPathEvaluator.getList(s);
        CustomReporter.logAction("'" + s + "' " + "parameter received from Response is " + response);
        System.out.println("'" + s + "' " + "parameter received from Response is " + response);
        Assert.assertNotEquals(response, null);
        return response;
    }

    public void getBotDataParameters(JsonPath jsonPathEvaluator, String botData, double botModel) {
        String response = jsonPathEvaluator.getString(botData);
        CustomReporter.logAction("'" + botData + "' " + "parameter received from Response is " + response);
        System.out.println("'" + botData + "' " + "parameter received from Response is " + response);
        Assert.assertEquals(Double.parseDouble(response), botModel);
    }

    public void getBotDataParametersInCreatedToken(JsonPath jsonPathEvaluator, String botData) {
        String response = jsonPathEvaluator.getString(botData);
        CustomReporter.logAction("'" + botData + "' " + "parameter received from Response is " + response);
        System.out.println("'" + botData + "' " + "parameter received from Response is " + response);
        Assert.assertEquals(Integer.parseInt(response), 0);
    }
}
