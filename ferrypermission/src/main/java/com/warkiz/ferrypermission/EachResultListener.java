package com.warkiz.ferrypermission;

/**
 * for each permission result'callback you requested
 */
public interface EachResultListener {
    /**
     * the result for each permission ,will be callback many times depended on the permission' count.
     *
     * @param permission the each permission's info.
     *                   include the permission's name, is granted or denied and
     *                   shouldShowRequestPermissionRationale or not,
     */
    void result(Permission permission);
}