package com.scenario_projects.mq_back_stage.actioHelpers;

import com.scenario_projects.mq_back_stage.dataProvider.FeeRate;
import com.scenario_projects.mq_back_stage.dataProvider.Token;
import com.scenario_projects.mq_back_stage.endpoints.AdminAndBotEndpoints;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class GetTradeFeesHelper {

    public void getTradeFees() {
        RequestSpecification request = RestAssured.given()
                .header("Accept", "application/json")
                .header("Authorization", Token.getAdminToken());

        Response response = request.get(AdminAndBotEndpoints.getTradeFees);
        ResponseBody.GetResponseBodyAndStatusCode(response, 200);

        JsonPath jsonPathEvaluator = response.jsonPath();
        GetParametersFromResponses getParameters = new GetParametersFromResponses();
        Double asMakerFeeRate = getParameters.getTradeFeesResponse(jsonPathEvaluator, "asMakerFeeRate");
        Double asTakerFeeRate = getParameters.getTradeFeesResponse(jsonPathEvaluator, "asTakerFeeRate");

        FeeRate.setAsMakerFeeRate(asMakerFeeRate);
        FeeRate.setAsTakerFeeRate(asTakerFeeRate);
    }
}
