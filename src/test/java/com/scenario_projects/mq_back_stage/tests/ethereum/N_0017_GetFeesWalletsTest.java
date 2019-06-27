package com.scenario_projects.mq_back_stage.tests.ethereum;

import com.scenario_projects.mq_back_stage.actioHelpers.AuthorizationHelper;
import com.scenario_projects.mq_back_stage.actioHelpers.FindUserByPublicAddressHelper;
import com.scenario_projects.mq_back_stage.actioHelpers.GetParametersFromResponses;
import com.scenario_projects.mq_back_stage.actioHelpers.ResponseBody;
import com.scenario_projects.mq_back_stage.dataProvider.Token;
import com.scenario_projects.mq_back_stage.endpoints.BaseEndpoints;
import com.scenario_projects.mq_back_stage.logging.TestListener;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(TestListener.class)
public class N_0017_GetFeesWalletsTest {

    @BeforeMethod
    public void findUserByPublicAddressAndAuthorization() {
        FindUserByPublicAddressHelper findUserByPublicAddressHelper = new FindUserByPublicAddressHelper();
        findUserByPublicAddressHelper.findUserByPublicAddress();

        AuthorizationHelper authorizationHelper = new AuthorizationHelper();
        authorizationHelper.authorization();
    }

    @Test
    public void GetFeesWallets() {
        RequestSpecification request = RestAssured.given()
                .header("Accept", "application/json")
                .header("Authorization", Token.getToken());

        Response response = request.get(BaseEndpoints.getFeesWallets);
        ResponseBody.GetResponseBodyAndStatusCode(response, 200);

        JsonPath jsonPathEvaluator = response.jsonPath();

        GetParametersFromResponses getParameters = new GetParametersFromResponses();
        getParameters.getParameterFromString(jsonPathEvaluator, "feesBalance");
        getParameters.getParameterFromString(jsonPathEvaluator, "wallet");
    }
}
