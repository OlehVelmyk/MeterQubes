package com.scenario_projects.mq_back_stage.tests.dashboard;

import com.scenario_projects.mq_back_stage.actioHelpers.GetParametersFromResponses;
import com.scenario_projects.mq_back_stage.actioHelpers.ResponseBody;
import com.scenario_projects.mq_back_stage.endpoints.DashboardAndFavoritesEndpoints;
import com.scenario_projects.mq_back_stage.logging.TestListener;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(TestListener.class)
public class N_0012_GetLandingDashboardDataTest {

    @Test
    public void getLandingDashboardData() {
        RequestSpecification request = RestAssured.given()
                .header("Accept", "application/json");

        Response response = request.get(DashboardAndFavoritesEndpoints.getLandingDashboardData);
        ResponseBody.GetResponseBodyAndStatusCode(response, 200);

        JsonPath jsonPathEvaluator = response.jsonPath();
        GetParametersFromResponses getParameters = new GetParametersFromResponses();

        getParameters.getParameterFromList(jsonPathEvaluator, "token");
        getParameters.getParameterFromList(jsonPathEvaluator, "lastPrice");
        getParameters.getParameterFromList(jsonPathEvaluator, "changeRate");
    }
}
