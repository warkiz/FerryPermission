package com.warkiz.ferrypermission;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;

public class FerryPermission {
    private static final String FRAGMENT_TAG = "Fragment_PiPermission";
    private Context mContext;
    private String[] mPermissions;
    private FerryPermissionFra mPiPermissionFra;

    public FerryPermission(@NonNull Context context) {
        this.mContext = context;
    }

    /**
     * request one permission
     * <p>
     * after, call one method #observe,#observeCombined,#observeEach
     * together to handle to result.
     */
    public FerryPermission request(@NonNull String permissions) {
        this.mPermissions = new String[1];
        this.mPermissions[0] = permissions;
        return this;
    }


    /**
     * request one or more permission
     * <p>
     * after, call method #observe,#observeCombined,#observeEach
     * together to handle to result.
     */
    public FerryPermission request(@NonNull String... permissions) {
        this.mPermissions = permissions;
        return this;
    }

    /**
     * call this to handle the permission'result together,
     * the result just provide the info that the permission is granted or not,
     * if just want to know the permissions are never ask again or not ,could call{@link #observeCombined} instead.
     * <p>
     * this method would be calling after {@link #request(String) {@link #request(String...)}} called.
     * <p>
     *
     * @param listener this listener will callback only one time ignore the count of permissions requesting.
     */
    public void observe(@NonNull PureResultListener listener) {
        if (needFerry()) {
            mPiPermissionFra.setPureResultListener(listener);
            mPiPermissionFra.requestPermissions(mPermissions);
        }
    }

    /**
     * call this to handle the permission'result for all permission you request{@link #request(String) }{@link #request(String) },
     * the result will provide the info that if the permission/s is never asking again,
     * if just want to know the permissions are granted or not,could call{@link #observe} instead.
     * <p>
     * this method would be calling after  #request(String) #request(String...) called.
     * <p>
     *
     * @param listener this listener will callback only one time ignore the count of permissions requesting.
     */
    public void observeCombined(@NonNull CombinedResultListener listener) {
        if (needFerry()) {
            mPiPermissionFra.setCombinedResultListener(listener);
            mPiPermissionFra.requestPermissions(mPermissions);
        }
    }

    /**
     * call this to handle the own result for each permission you requesting,
     * the result will provide the info that if the permission/s is never asking again,
     * if just want to know the permissions are granted or not,could call{@link #observe} instead.
     * <p>
     * this method would be calling after {@link #request} called.
     * <p>
     *
     * @param listener this listener will callback many times depended on the count of permissions requesting.
     */
    public void observeEach(@NonNull EachResultListener listener) {
        if (needFerry()) {
            mPiPermissionFra.setEachResultListener(listener);
            mPiPermissionFra.requestPermissions(mPermissions);
        }
    }

    private boolean needFerry() {
        for (String perm : mPermissions) {
            if (ActivityCompat.checkSelfPermission(mContext, perm) != PackageManager.PERMISSION_GRANTED) {
                initPiPermissionFragment(mContext);
                return true;
            }
        }
        return false;
    }

    private void initPiPermissionFragment(Context context) {
        mPiPermissionFra = findPiPermissionFragment(context);
        if (mPiPermissionFra == null) {
            mPiPermissionFra = new FerryPermissionFra();
            FragmentManager fragmentManager = ((Activity) context).getFragmentManager();
            fragmentManager.beginTransaction().add(mPiPermissionFra, FRAGMENT_TAG).commitAllowingStateLoss();
            fragmentManager.executePendingTransactions();
        }
    }

    private FerryPermissionFra findPiPermissionFragment(Context context) {
        return (FerryPermissionFra) ((Activity) context).getFragmentManager().findFragmentByTag(FRAGMENT_TAG);
    }

}
