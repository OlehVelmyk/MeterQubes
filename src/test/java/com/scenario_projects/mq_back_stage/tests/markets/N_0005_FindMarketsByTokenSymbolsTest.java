package com.scenario_projects.mq_back_stage.tests.markets;

import com.scenario_projects.mq_back_stage.actioHelpers.ResponseBody;
import com.scenario_projects.mq_back_stage.endpoints.MarketsEndpoints;
import com.scenario_projects.mq_back_stage.logging.CustomReporter;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.Test;

public class N_0005_FindMarketsByTokenSymbolsTest {
    private String tokenName = "EMPR";

    @Test
    public void getMarketsByTokenSymbols() {
        RequestSpecification request = RestAssured.given()
                .header("Accept", "application/json");

        Response response = request.get(MarketsEndpoints.getMarketsByTokenSymbol(tokenName));
        ResponseBody.GetResponseBodyAndStatusCode(response, 200);

        JsonPath jsonPathEvaluator = response.jsonPath();
        String tokenId = jsonPathEvaluator.getList("baseToken").toString().replace("[", "").replace("]", "");
        CustomReporter.logAction("Token name received from Response is " + tokenId);
        System.out.println("Token name received from Response is " + tokenId);

        Assert.assertEquals(tokenId, tokenName);
    }
}
