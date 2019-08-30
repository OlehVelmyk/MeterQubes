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

public class N_0011_GetTradeDashboardDataByMarketIdTest {

    @Test
    public void getTradeDashboardDataByMarketId() {
        RequestSpecification request = RestAssured.given()
                .header("Accept", "application/json");

        Response response = request.get(DashboardAndFavoritesEndpoints.getTradeDashboardData(MarketId.marketId));
        ResponseBody.GetResponseBodyAndStatusCode(response, 200);

        JsonPath jsonPathEvaluator = response.jsonPath();
        GetParametersFromResponses getParameters = new GetParametersFromResponses();

        getParameters.getParameterFromString(jsonPathEvaluator, "lastPrice");
        getParameters.getParameterFromString(jsonPathEvaluator, "firstPrice");
        getParameters.getParameterFromString(jsonPathEvaluator, "changeRate");
        getParameters.getParameterFromString(jsonPathEvaluator, "priceChange");
        getParameters.getParameterFromString(jsonPathEvaluator, "highPrice");
        getParameters.getParameterFromString(jsonPathEvaluator, "lowPrice");
    }
}
