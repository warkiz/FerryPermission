package com.warkiz.ferrypermission;

import android.annotation.NonNull;

/**
 * the info for each/combined permission/s
 */
public class Permission {
    public String name;//permission's name
    public boolean isGranted;
    public boolean shouldShowRequestPermissionRationale;

    public Permission(@NonNull String name, boolean isGranted, boolean shouldShowRequestPermissionRationale) {
        this.name = name;
        this.isGranted = isGranted;
        this.shouldShowRequestPermissionRationale = shouldShowRequestPermissionRationale;
    }

    public Permission(@NonNull String[] nameArr, boolean isGranted, boolean shouldShowRequestPermissionRationale) {
        this.name = combinedName(nameArr);
        this.isGranted = isGranted;
        this.shouldShowRequestPermissionRationale = shouldShowRequestPermissionRationale;
    }

    private String combinedName(String[] nameArr) {
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        for (int i = 0; i < nameArr.length; i++) {
            builder.append("\"").append(nameArr[i]);
            if (i == nameArr.length - 1) {
                builder.append("\"");
                break;
            }
            builder.append("\",");
        }
        builder.append("]");
        return builder.toString();
    }

}