package com.scenario_projects.mq_back_stage.tests.orders;

import com.scenario_projects.mq_back_stage.actioHelpers.RunJSFiles;
import com.scenario_projects.mq_back_stage.dataProvider.TransactionHash;
import com.scenario_projects.mq_back_stage.endpoints.OrdersEndpoints;
import com.scenario_projects.mq_back_stage.logging.CustomReporter;
import com.scenario_projects.mq_back_stage.logging.TestListener;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(TestListener.class)
public class N_0026_CreateOrderTest {

    @Test
    public void createOrder() throws InterruptedException {

        RunJSFiles runFileJS = new RunJSFiles();
        runFileJS.runJSfile("/home/ubuntu/.nvm/versions/node/v10.15.2/bin/node src/test/java/resources/generateTestOrderPair.js");
        //runFileJS.runJSfile("src\\test\\java\\resources\\node.exe src\\test\\java\\resources\\generateTestOrderPair.js");

        Thread.sleep(2000);

        RequestSpecification request = RestAssured.given();

        Response response = request.get(OrdersEndpoints.transactionStatus + TransactionHash.getHash());
        CustomReporter.logAction("Transaction hash is " + TransactionHash.getHash());
        System.out.println("Transaction hash is " + TransactionHash.getHash());

        int statusCode = response.getStatusCode();
        CustomReporter.logAction("Response body is " + response.body().asString().substring(70, 136));
        System.out.println("Response body: " + response.body().asString().substring(70, 136));

        Assert.assertTrue(response.body().asString().length() >= 0);
        Assert.assertEquals(statusCode, 200);
        Assert.assertEquals(response.body().asString().substring(70, 136), TransactionHash.getHash());
    }
}