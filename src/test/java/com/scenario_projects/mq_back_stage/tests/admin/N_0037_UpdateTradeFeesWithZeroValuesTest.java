package com.scenario_projects.mq_back_stage.tests.admin;

import com.scenario_projects.mq_back_stage.actioHelpers.UpdateTradeFeesHelper;
import com.scenario_projects.mq_back_stage.logging.CustomReporter;
import com.scenario_projects.mq_back_stage.logging.TestListener;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners({TestListener.class})
public class N_0037_UpdateTradeFeesWithZeroValuesTest {
    private double asMakerFeeRate = 0;
    private double asTakerFeeRate = 0;

    @Test
    public void updateTradeFees() {
        UpdateTradeFeesHelper updateTradeFeesHelper = new UpdateTradeFeesHelper();
        Response response = updateTradeFeesHelper.updateTradeFees(asMakerFeeRate, asTakerFeeRate, 400);

        JsonPath jsonPathEvaluator = response.jsonPath();
        String errorMessage = jsonPathEvaluator.getString("message");
        CustomReporter.logAction("Error message  from Response is " + "'" + errorMessage + "'");
        System.out.println("Error message from Response is " + "'" + errorMessage + "'");
        Assert.assertEquals(errorMessage, "Fees should be positive and not zero");
    }
}
