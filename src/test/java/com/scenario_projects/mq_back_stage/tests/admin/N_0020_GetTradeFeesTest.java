package com.scenario_projects.mq_back_stage.tests.admin;

import com.scenario_projects.mq_back_stage.actioHelpers.GetTradeFeesHelper;
import org.testng.annotations.Test;

public class N_0020_GetTradeFeesTest {

    @Test
    public void getTradeFees() {
        GetTradeFeesHelper getTradeFeesHelper = new GetTradeFeesHelper();
        getTradeFeesHelper.getTradeFees();
    }
}
