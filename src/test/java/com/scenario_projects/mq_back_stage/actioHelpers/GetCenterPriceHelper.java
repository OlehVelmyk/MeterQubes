package com.scenario_projects.mq_back_stage.actioHelpers;

import com.google.gson.Gson;
import com.scenario_projects.mq_back_stage.dataProvider.BotValues;
import com.scenario_projects.mq_back_stage.endpoints.AdminAndBotEndpoints;
import com.scenario_projects.mq_back_stage.logging.CustomReporter;
import com.scenario_projects.mq_back_stage.model.CenterPrice;
import com.scenario_projects.mq_back_stage.model.Eth;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;

import java.util.Map;

public class GetCenterPriceHelper {

    public void getCenterPrice() {
        RequestSpecification request = RestAssured.given()
                .header("Accept", "application/json");

        Response response = request.get(AdminAndBotEndpoints.getCenterPrice);
        ResponseBody.GetResponseBodyAndStatusCode(response, 200);

        JsonPath jsonPathEvaluator = response.jsonPath();
        Map rawValue = jsonPathEvaluator.getMap("RAW");
        Assert.assertNotEquals(rawValue, null);

        Object empr = rawValue.get("EMPR");

        Gson gson = new Gson();

        String toJson = gson.toJson(empr);

        Eth eth = gson.fromJson(toJson, Eth.class);

        String toJsonAgain = gson.toJson(eth.getEth());

        CenterPrice centerPrice = gson.fromJson(toJsonAgain, CenterPrice.class);

        BotValues.setCenterPrice(centerPrice.getPrice());
        CustomReporter.logAction("Center price = " + BotValues.getCenterPrice());
        System.out.println("Center price = " + BotValues.getCenterPrice());
    }
}
