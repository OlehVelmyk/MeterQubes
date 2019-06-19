package com.scenario_projects.mq_back_stage.dataProvider;

public class UserId {
    private static int userId;

    public static int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
