package com.scenario_projects.mq_back_stage.tests.admin;

import com.scenario_projects.mq_back_stage.actioHelpers.GenerateRandomFeeRate;
import com.scenario_projects.mq_back_stage.actioHelpers.GetParametersFromResponses;
import com.scenario_projects.mq_back_stage.actioHelpers.ResponseBody;
import com.scenario_projects.mq_back_stage.actioHelpers.UpdateTradeFeesHelper;
import com.scenario_projects.mq_back_stage.dataProvider.Token;
import com.scenario_projects.mq_back_stage.endpoints.AdminAndBotEndpoints;
import com.scenario_projects.mq_back_stage.logging.TestListener;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners({TestListener.class})
public class N_0021_UpdateTradeFeesTest {
    private double asMakerFeeRate = Double.parseDouble(GenerateRandomFeeRate.generateFeeRate());
    private double asTakerFeeRate = Double.parseDouble(GenerateRandomFeeRate.generateFeeRate());

    @Test
    public void updateTradeFees() {
        UpdateTradeFeesHelper updateTradeFeesHelper = new UpdateTradeFeesHelper();
        updateTradeFeesHelper.updateTradeFees(asMakerFeeRate, asTakerFeeRate, 204);
    }

    @AfterMethod
    public void getTradeFees() {
        RequestSpecification request = RestAssured.given()
                .header("Accept", "application/json")
                .header("Authorization", Token.getAdminToken());

        Response response = request.get(AdminAndBotEndpoints.getTradeFees);
        ResponseBody.GetResponseBodyAndStatusCode(response, 200);

        JsonPath jsonPathEvaluator = response.jsonPath();
        GetParametersFromResponses getParameters = new GetParametersFromResponses();
        Double asMakerFeeRateFromResponse = getParameters.getTradeFeesResponse(jsonPathEvaluator, "asMakerFeeRate");
        Double asTakerFeeRateFromResponse = getParameters.getTradeFeesResponse(jsonPathEvaluator, "asTakerFeeRate");

        Assert.assertEquals(asMakerFeeRateFromResponse, asMakerFeeRate);
        Assert.assertEquals(asTakerFeeRateFromResponse, asTakerFeeRate);
    }
}
