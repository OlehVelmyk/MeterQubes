package com.scenario_projects.mq_back_stage.tests.markets;

import com.scenario_projects.mq_back_stage.actioHelpers.ResponseBody;
import com.scenario_projects.mq_back_stage.endpoints.MarketsEndpoints;
import com.scenario_projects.mq_back_stage.logging.TestListener;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners({TestListener.class})
public class N_0004_FindAllMarketsTest {

    @Test
    public void getAllMarkets() {
        RequestSpecification request = RestAssured.given()
                .header("Accept", "application/json");

        Response response = request.get(MarketsEndpoints.getAllMarkets);
        ResponseBody.GetResponseBodyAndStatusCode(response, 200);
    }
}
