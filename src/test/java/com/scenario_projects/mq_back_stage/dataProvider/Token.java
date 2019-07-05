package com.scenario_projects.mq_back_stage.dataProvider;

public class Token {
    private static String token;

    private static String adminToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9." +
            "eyJwYXlsb2FkIjp7ImlkIjoxLCJwdWJsaWNBZGRyZXNzIjoiMHg5NjI0MTE4NmNhNGFhNzlmNWJkNTkzYTAyYzI5YTM4ZGE3ODUyNTFlIn0sImlhdCI6MTU1NTQyMzE2Mn0." +
            "lVzu2XbZ98Cj73wOW7phe5py-LT_uq1uFlaQpjsYqho";

    public void setToken(String token) {
        this.token = token;
    }

    public static String getToken() {
        return token;
    }

    public static String getAdminToken() {
        return adminToken;
    }
}
