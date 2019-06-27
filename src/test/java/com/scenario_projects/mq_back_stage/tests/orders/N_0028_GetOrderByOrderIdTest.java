package com.scenario_projects.mq_back_stage.tests.orders;

import com.scenario_projects.mq_back_stage.actioHelpers.AuthorizationHelper;
import com.scenario_projects.mq_back_stage.actioHelpers.FindUserByPublicAddressHelper;
import com.scenario_projects.mq_back_stage.actioHelpers.ResponseBody;
import com.scenario_projects.mq_back_stage.dataProvider.Token;
import com.scenario_projects.mq_back_stage.endpoints.OrdersEndpoints;
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

@Listeners(TestListener.class)
public class N_0028_GetOrderByOrderIdTest {
    private int orderId = 1;

    @BeforeMethod
    public void findUserByPublicAddressAndAuthorization() {
        FindUserByPublicAddressHelper findUserByPublicAddressHelper = new FindUserByPublicAddressHelper();
        findUserByPublicAddressHelper.findUserByPublicAddress();

        AuthorizationHelper authorizationHelper = new AuthorizationHelper();
        authorizationHelper.authorization();
    }

    @Test
    public void getOrderByOrderId() {
        RequestSpecification request = RestAssured.given()
                .header("Accept", "application/json")
                .header("Authorization", Token.getToken());

        Response response = request.get(OrdersEndpoints.getOrderByOrderId(orderId));
        ResponseBody.GetResponseBodyAndStatusCode(response, 200);

        JsonPath jsonPathEvaluator = response.jsonPath();
        int orderId = jsonPathEvaluator.get("id");
        CustomReporter.logAction("orderId  from Response is " + "'" + orderId + "'");
        System.out.println("orderId from Response is " + "'" + orderId + "'");
        Assert.assertEquals(orderId, orderId);
    }
}
