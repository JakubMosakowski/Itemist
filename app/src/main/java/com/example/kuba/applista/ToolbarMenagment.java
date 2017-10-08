package com.example.kuba.applista;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Kuba on 08.10.2017.
 */

public class ToolbarMenagment {
    @Nullable
    public static Toolbar getToolbarView(@NonNull Context context) {
        Activity activity = ((Activity) context);

        int resId = context.getResources().getIdentifier("action_bar", "id", "android");

        Toolbar toolbar = (Toolbar) activity.findViewById(resId);
        if (toolbar == null) {
            toolbar = findToolbar((ViewGroup) activity.findViewById(android.R.id.content));
        }
        return toolbar;
    }

    private static Toolbar findToolbar(@NonNull ViewGroup viewGroup) {
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View view = viewGroup.getChildAt(i);
            if (view.getClass().getName().equals("android.support.v7.widget.Toolbar")
                    || view.getClass().getName().equals("android.widget.Toolbar")) {
                return (Toolbar) view;
            } else if (view instanceof ViewGroup) {
                return findToolbar((ViewGroup) view);
            }
        }
        return null;
    }
}
