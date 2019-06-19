package com.scenario_projects.mq_back_stage.actioHelpers;

import com.scenario_projects.mq_back_stage.dataProvider.NonceValue;
import com.scenario_projects.mq_back_stage.dataProvider.UserId;
import com.scenario_projects.mq_back_stage.dataProvider.WalletAddress;
import com.scenario_projects.mq_back_stage.endpoints.UsersEndpoints;
import com.scenario_projects.mq_back_stage.logging.CustomReporter;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class FindUserByPublicAddressHelper {

    public void findUserByPublicAddress() {
        RequestSpecification request = RestAssured.given()
                .header("Accept", "application/json");

        Response response = request.get(UsersEndpoints.findUserByPublicAddress(WalletAddress.publicAddress.toLowerCase()));
        ResponseBody.GetResponseBodyAndStatusCode(response, 200);

        JsonPath jsonPathEvaluator = response.jsonPath();
        String nonce = jsonPathEvaluator.get("nonce");
        CustomReporter.logAction("Nonce received from Response is " + "'" + nonce + "'");
        System.out.println("Nonce received from Response is " + "'" + nonce + "'");

        int userId = jsonPathEvaluator.get("id");
        CustomReporter.logAction("User Id from Response is " + "'" + userId + "'");
        System.out.println("User Id from Response is " + "'" + userId + "'");

        NonceValue nonceValue = new NonceValue();
        nonceValue.setNonceValue(nonce);
        nonceValue.writeNonceValueToFile();

        UserId userIdValue = new UserId();
        userIdValue.setUserId(userId);
    }
}
