package com.scenario_projects.mq_back_stage.endpoints;

public class UsersEndpoints extends BaseEndpoints {

    public static final String createUser = baseURL + "users";

    public static String findUserByPublicAddress(String publicAddress) {
        return baseURL + "users?publicAddress=" + publicAddress;
    }

    public static String findAndUpdateUserByUserId(int userId) {
        return baseURL + "users/" + userId;
    }
}
