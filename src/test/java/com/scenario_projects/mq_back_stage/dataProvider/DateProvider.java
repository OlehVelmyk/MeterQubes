package com.scenario_projects.mq_back_stage.dataProvider;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateProvider {

    public static String currentTime() {
        Date dNow = new Date();
        SimpleDateFormat ft =
                new SimpleDateFormat("HHmmssSSS");
        return ft.format(dNow);
    }
}
