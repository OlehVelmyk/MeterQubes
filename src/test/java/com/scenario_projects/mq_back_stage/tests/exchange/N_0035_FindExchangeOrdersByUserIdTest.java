package com.scenario_projects.mq_back_stage.tests.exchange;

import com.scenario_projects.mq_back_stage.actioHelpers.FindUserByPublicAddressHelper;
import com.scenario_projects.mq_back_stage.actioHelpers.ResponseBody;
import com.scenario_projects.mq_back_stage.dataProvider.Token;
import com.scenario_projects.mq_back_stage.dataProvider.UserId;
import com.scenario_projects.mq_back_stage.endpoints.ExchangeEndpoints;
import com.scenario_projects.mq_back_stage.logging.CustomReporter;
import com.scenario_projects.mq_back_stage.logging.TestListener;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import java.util.List;

@Listeners(TestListener.class)
public class N_0035_FindExchangeOrdersByUserIdTest {

    @BeforeMethod
    public void findUserByPublicAddress() {
        FindUserByPublicAddressHelper findUserByPublicAddressHelper = new FindUserByPublicAddressHelper();
        findUserByPublicAddressHelper.findUserByPublicAddress();
    }

    @Test
    public void getExchangeOrderDataByTokenId() {
        RequestSpecification request = RestAssured.given()
                .header("Accept", "application/json")
                .header("Authorization", Token.getAdminToken());

        Response response = request.get(ExchangeEndpoints.findExchangeOrdersByUserId(UserId.getUserId()));
        ResponseBody.GetResponseBodyAndStatusCode(response, 200);

        JsonPath jsonPathEvaluator = response.jsonPath();
        List<Object> exchangeTokenId = jsonPathEvaluator.getList("id");
        CustomReporter.logAction("Token id from Response is " + "'" + exchangeTokenId + "'");
        System.out.println("Token id from Response is " + "'" + exchangeTokenId + "'");
        Assert.assertNotNull(exchangeTokenId);
    }
}
