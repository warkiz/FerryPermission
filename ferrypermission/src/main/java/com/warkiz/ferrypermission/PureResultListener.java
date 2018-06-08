package com.warkiz.ferrypermission;

/**
 * for the result callback
 */
public interface PureResultListener {
    /**
     * the requesting permission/s result.
     *
     * @param isGranted true if the permission is granted, otherwise is denied.
     */
    void result(boolean isGranted);
}