package com.scenario_projects.mq_back_stage.tests.users;

import com.scenario_projects.mq_back_stage.actioHelpers.AuthorizationHelper;
import com.scenario_projects.mq_back_stage.actioHelpers.FindUserByPublicAddressHelper;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class N_0002_Authorization {

    @BeforeMethod
    public void findUserByPublicAddress() {
        FindUserByPublicAddressHelper findUserByPublicAddressHelper = new FindUserByPublicAddressHelper();
        findUserByPublicAddressHelper.findUserByPublicAddress();
    }

    @Test
    public void authorizationSuccessful() {
        AuthorizationHelper authorizationHelper = new AuthorizationHelper();
        authorizationHelper.authorization();
    }
}
