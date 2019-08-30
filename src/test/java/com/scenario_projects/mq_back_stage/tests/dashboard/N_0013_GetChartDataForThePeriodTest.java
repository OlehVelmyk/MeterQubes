package com.scenario_projects.mq_back_stage.tests.dashboard;

import com.scenario_projects.mq_back_stage.actioHelpers.GetParametersFromResponses;
import com.scenario_projects.mq_back_stage.actioHelpers.ResponseBody;
import com.scenario_projects.mq_back_stage.dataProvider.MarketId;
import com.scenario_projects.mq_back_stage.endpoints.DashboardAndFavoritesEndpoints;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;

public class N_0013_GetChartDataForThePeriodTest {

    @Test
    public void getChartDataForThePeriod() {
        RequestSpecification request = RestAssured.given()
                .header("Accept", "application/json");

        Response response = request.get(DashboardAndFavoritesEndpoints.getChartDataForThePeriod(MarketId.marketId));
        ResponseBody.GetResponseBodyAndStatusCode(response, 200);

        JsonPath jsonPathEvaluator = response.jsonPath();
        GetParametersFromResponses getParameters = new GetParametersFromResponses();

        getParameters.getParameterFromList(jsonPathEvaluator, "close");
        getParameters.getParameterFromList(jsonPathEvaluator, "open");
        getParameters.getParameterFromList(jsonPathEvaluator, "low");
        getParameters.getParameterFromList(jsonPathEvaluator, "high");
        getParameters.getParameterFromList(jsonPathEvaluator, "time");
        getParameters.getParameterFromList(jsonPathEvaluator, "volume");
    }
}
