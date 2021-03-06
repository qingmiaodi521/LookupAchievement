package com.blackwhite.activities;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BlackWhite on 15/9/11.
 * 类管理工具类
 */
public class ActivitiesCollector {
    public static List<Activity> activities = new ArrayList<>();
    public static  void addActivity(Activity activity)
    {
        activities.add(activity);
    }
    public static void removeActivity(Activity activity)
    {
        activities.remove(activity);
    }
    public static  void removeAllActivities()
    {
        for (Activity activity: activities)
        {
            if (!activity.isFinishing()) {
                activity.finish();
            }

        }
    }

}
