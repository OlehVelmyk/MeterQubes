package com.scenario_projects.mq_back_stage.tests.users;

import com.scenario_projects.mq_back_stage.actioHelpers.FindUserByPublicAddressHelper;
import org.testng.annotations.Test;

public class N_0001_FindUserByPublicAddressTest {

    @Test
    public void findUserByPublicAddress() {
        FindUserByPublicAddressHelper findUserByPublicAddressHelper = new FindUserByPublicAddressHelper();
        findUserByPublicAddressHelper.findUserByPublicAddress();
    }
}
