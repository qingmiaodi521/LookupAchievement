package com.blackwhite.lookupachievement;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BlackWhite on 15/9/11.
 */
public class ActivitiyCollector  {
    public static List<Activity> activities = new ArrayList<>();
    public static  void addAcitivity(Activity activity)
    {
        activities.add(activity);
    }
    public static void removeActivitiy(Activity activity)
    {
        activities.remove(activity);
    }
    public static  void removeAllAcitivity()
    {
        for (Activity activity: activities)
        {
            if (!activity.isFinishing()) {
                activity.finish();
            }

        }
    }

}
