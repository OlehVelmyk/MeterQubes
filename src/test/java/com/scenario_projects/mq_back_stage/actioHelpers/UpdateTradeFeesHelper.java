package com.scenario_projects.mq_back_stage.actioHelpers;

import com.scenario_projects.mq_back_stage.dataProvider.Token;
import com.scenario_projects.mq_back_stage.endpoints.AdminAndBotEndpoints;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;

public class UpdateTradeFeesHelper {

    public Response updateTradeFees(double asMakerFeeRate, double asTakerFeeRate, int status) {
        JSONObject requestParams = new JSONObject()
                .put("asMakerFeeRate", String.valueOf(asMakerFeeRate))
                .put("asTakerFeeRate", String.valueOf(asTakerFeeRate));

        RequestSpecification request = RestAssured.given()
                .header("Content-Type", "application/json")
                .header("Authorization", Token.getAdminToken())
                .body(requestParams.toString());

        Response response = request.patch(AdminAndBotEndpoints.updateTradeFees);
        ResponseBody.GetResponseBodyAndStatusCode(response, status);

        return response;
    }
}
