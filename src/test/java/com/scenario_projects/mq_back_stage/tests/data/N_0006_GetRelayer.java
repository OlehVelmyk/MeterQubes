package com.scenario_projects.mq_back_stage.tests.data;

import com.scenario_projects.mq_back_stage.actioHelpers.AuthorizationHelper;
import com.scenario_projects.mq_back_stage.actioHelpers.FindUserByPublicAddressHelper;
import com.scenario_projects.mq_back_stage.actioHelpers.ResponseBody;
import com.scenario_projects.mq_back_stage.dataProvider.Token;
import com.scenario_projects.mq_back_stage.endpoints.BaseEndpoints;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class N_0006_GetRelayer {

    @BeforeMethod
    public void findUserByPublicAddressAndAuthorization() {
        FindUserByPublicAddressHelper findUserByPublicAddressHelper = new FindUserByPublicAddressHelper();
        findUserByPublicAddressHelper.findUserByPublicAddress();

        AuthorizationHelper authorizationHelper = new AuthorizationHelper();
        authorizationHelper.authorization();
    }

    @Test
    public void getMarketsByTokenSymbols() {
        RequestSpecification request = RestAssured.given()
                .header("Accept", "application/json")
                .header("Authorization", Token.getToken());

        Response response = request.get(BaseEndpoints.getRelayer);
        ResponseBody.GetResponseBodyAndStatusCode(response, 200);
    }
}
