package com.scenario_projects.mq_back_stage.actioHelpers;

import com.scenario_projects.mq_back_stage.dataProvider.Signature;
import com.scenario_projects.mq_back_stage.dataProvider.Token;
import com.scenario_projects.mq_back_stage.dataProvider.WalletAddress;
import com.scenario_projects.mq_back_stage.endpoints.BaseEndpoints;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;

public class AuthorizationHelper {

    public void authorization() {

        RunJSFiles runFileJS = new RunJSFiles();
        //runFileJS.runJSfile("/home/ubuntu/.nvm/versions/node/v10.15.2/bin/node src/test/java/resources/calculateSignature.js");
        runFileJS.runJSfile("src/test/java/resources/node src/test/java/resources/calculateSignature.js");
        //runFileJS.runJSfile("src\\test\\java\\resources\\node.exe src\\test\\java\\resources\\calculateSignature.js");

        JSONObject requestParams = new JSONObject()
                .put("publicAddress", WalletAddress.publicAddress)
                .put("signature", Signature.getSignature());

        RequestSpecification request = RestAssured.given()
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .body(requestParams.toString());

        Response response = request.post(BaseEndpoints.auth);
        ResponseBody.GetResponseBodyAndStatusCode(response, 200);

        Token token = new Token();
        token.setToken(response.getBody().asString());
    }
}
