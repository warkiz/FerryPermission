package com.warkiz.ferrypermission;

/**
 * for the combined result which from all permission requesting.
 */
public interface CombinedResultListener {
    /**
     * the result callback for the permission has combined
     *
     * @param permission the info for all permission combined
     *                   include name: all permission name
     *                   isGranted: all permission is granted
     *                   shouldShowRequestPermissionRationale: at least one permission shouldShowRequestPermissionRationale.
     */
    void result(Permission permission);
}