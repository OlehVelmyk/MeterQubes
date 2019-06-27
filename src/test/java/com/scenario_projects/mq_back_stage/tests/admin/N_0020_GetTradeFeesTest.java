package com.scenario_projects.mq_back_stage.tests.admin;

import com.scenario_projects.mq_back_stage.actioHelpers.GetTradeFeesHelper;
import com.scenario_projects.mq_back_stage.logging.TestListener;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(TestListener.class)
public class N_0020_GetTradeFeesTest {

    @Test
    public void getTradeFees() {
        GetTradeFeesHelper getTradeFeesHelper = new GetTradeFeesHelper();
        getTradeFeesHelper.getTradeFees();
    }
}
