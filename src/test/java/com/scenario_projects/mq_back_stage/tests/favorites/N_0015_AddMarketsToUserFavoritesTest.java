package com.scenario_projects.mq_back_stage.tests.favorites;

import com.scenario_projects.mq_back_stage.actioHelpers.AuthorizationHelper;
import com.scenario_projects.mq_back_stage.actioHelpers.FindUserByPublicAddressHelper;
import com.scenario_projects.mq_back_stage.actioHelpers.ResponseBody;
import com.scenario_projects.mq_back_stage.dataProvider.MarketId;
import com.scenario_projects.mq_back_stage.dataProvider.Token;
import com.scenario_projects.mq_back_stage.endpoints.DashboardAndFavoritesEndpoints;
import com.scenario_projects.mq_back_stage.logging.CustomReporter;
import com.scenario_projects.mq_back_stage.logging.TestListener;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static com.scenario_projects.mq_back_stage.endpoints.DashboardAndFavoritesEndpoints.getUserFavoritesMarkets;

@Listeners({TestListener.class})
public class N_0015_AddMarketsToUserFavoritesTest {

    @BeforeMethod
    public void findUserByPublicAddressAndAuthorization() {
        FindUserByPublicAddressHelper findUserByPublicAddressHelper = new FindUserByPublicAddressHelper();
        findUserByPublicAddressHelper.findUserByPublicAddress();

        AuthorizationHelper authorizationHelper = new AuthorizationHelper();
        authorizationHelper.authorization();
    }

    @Test
    public void addMarketsToUserFavorites() {
        RequestSpecification request = RestAssured.given()
                .header("Accept", "application/json")
                .header("Authorization", Token.getToken());

        Response response = request.put(DashboardAndFavoritesEndpoints.addAdnDeleteMarketToUserFavorites(MarketId.marketId));
        ResponseBody.GetResponseBodyAndStatusCode(response, 204);
    }

    @AfterMethod
    public void getUserFavoritesMarketsTest() {
        RequestSpecification request = RestAssured.given()
                .header("Accept", "application/json")
                .header("Authorization", Token.getToken());

        Response response = request.get(getUserFavoritesMarkets);
        ResponseBody.GetResponseBodyAndStatusCode(response, 200);

        JsonPath jsonPathEvaluator = response.jsonPath();
        String id = jsonPathEvaluator.getList("id").toString().replace("[", "").replace("]", "");
        CustomReporter.logAction("'Id' parameter received from Response is " + id);
        System.out.println("'Id' parameter received from Response is " + id);
        Assert.assertEquals(Integer.parseInt(id), MarketId.marketId);
    }

    @AfterMethod(dependsOnMethods = "getUserFavoritesMarketsTest")
    public void deleteMarketFromUserFavorites() {

        RequestSpecification request = RestAssured.given()
                .header("Accept", "application/json")
                .header("Authorization", Token.getToken());

        Response response = request.delete(DashboardAndFavoritesEndpoints.addAdnDeleteMarketToUserFavorites(MarketId.marketId));
        ResponseBody.GetResponseBodyAndStatusCode(response, 204);
    }
}
