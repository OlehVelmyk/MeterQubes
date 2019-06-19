package com.scenario_projects.mq_back_stage.tests.users;

import com.scenario_projects.mq_back_stage.actioHelpers.AuthorizationHelper;
import com.scenario_projects.mq_back_stage.actioHelpers.FindUserByPublicAddressHelper;
import com.scenario_projects.mq_back_stage.actioHelpers.ResponseBody;
import com.scenario_projects.mq_back_stage.actioHelpers.RunJSFiles;
import com.scenario_projects.mq_back_stage.dataProvider.Signature;
import com.scenario_projects.mq_back_stage.dataProvider.WalletAddress;
import com.scenario_projects.mq_back_stage.endpoints.BaseEndpoints;
import com.scenario_projects.mq_back_stage.logging.TestListener;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners({TestListener.class})
public class N_0036_AuthorizationFailed {

    @BeforeMethod
    public void findUserByPublicAddressAndAuthorization() {
        FindUserByPublicAddressHelper findUserByPublicAddressHelper = new FindUserByPublicAddressHelper();
        findUserByPublicAddressHelper.findUserByPublicAddress();

        AuthorizationHelper authorizationHelper = new AuthorizationHelper();
        authorizationHelper.authorization();
    }

    @Test
    public void authorizationAgain() {
        RunJSFiles runFileJS = new RunJSFiles();
        runFileJS.runJSfile("/home/ubuntu/.nvm/versions/node/v10.15.2/bin/node src/test/java/resources/calculateSignature.js");
        //runFileJS.runJSfile("src\\test\\java\\resources\\node.exe src\\test\\java\\resources\\calculateSignature.js");

        JSONObject requestParams = new JSONObject()
                .put("publicAddress", WalletAddress.publicAddress)
                .put("signature", Signature.getSignature());

        RequestSpecification request = RestAssured.given()
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .body(requestParams.toString());

        Response response = request.post(BaseEndpoints.auth);
        ResponseBody.GetResponseBodyAndStatusCode(response, 500);
    }
}
