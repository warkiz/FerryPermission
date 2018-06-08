package com.warkiz.ferrypermission;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Size;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class FerryPermissionFra extends Fragment {
    private static final int PERMISSIONS_REQUEST_CODE = 54;
    private PureResultListener mPureResultListener;
    private EachResultListener mEachResultListener;
    private CombinedResultListener mCombinedResultListener;

    public FerryPermissionFra() {
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //all permission is granted
        boolean isAllGranted = true;
        //at least one permission is denied but will ask again.
        boolean shouldShowRequestPermissionRationale = false;
        if (PERMISSIONS_REQUEST_CODE == requestCode) {
            for (int i = 0; i < permissions.length; i++) {
                //one of the permissions is Granted
                boolean oneIsGranted = false;
                //one of the permissions is denied but will ask again
                boolean oneShouldShowPermissionRationale = false;
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    //one of all permission is granted
                    oneIsGranted = true;
                } else {
                    //all permission was denied if one is rejected.
                    isAllGranted = false;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        //check the permission will be never asked again or not.
                        if (shouldShowRequestPermissionRationale(permissions[i])) {
                            //at least one permission is denied but will ask again.
                            shouldShowRequestPermissionRationale = true;
                            //one of the permissions is denied but will ask again
                            oneShouldShowPermissionRationale = true;
                        }
                    }
                }
                //check each permission's result
                eachResult(new Permission(permissions[i], oneIsGranted, oneShouldShowPermissionRationale));
            }
            //check the permission whether is granted or denied.
            pureResult(isAllGranted);
            //check the combined result for all permission
            combinedResult(new Permission(permissions, isAllGranted, shouldShowRequestPermissionRationale));
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    /**
     * only when the target is over than Marshmallow (api level == 23) ferry permission to handle this, else let system to handle.
     */
    @TargetApi(Build.VERSION_CODES.M)
    void requestPermissions(@NonNull @Size(min = 1) String... permission) {
        List<String> deniedList = null;
        for (String perm : permission) {
            if (ContextCompat.checkSelfPermission(getActivity(), perm) != PackageManager.PERMISSION_GRANTED) {
                if (deniedList == null) {
                    deniedList = new ArrayList<>();
                }
                deniedList.add(perm);
            } else {
                //check each permission's result
                eachResult(new Permission(perm, true, false));
            }
        }
        if (deniedList == null || deniedList.size() == 0) {
            //check the permission whether is granted or denied.
            pureResult(true);// all permission are granted
            //check the combined result for all permission
            combinedResult(new Permission(permission, true, false));
            return;
        }
        requestPermissions((String[]) deniedList.toArray(new String[0]), PERMISSIONS_REQUEST_CODE);
    }


    private void pureResult(boolean isGranted) {
        if (mPureResultListener != null) {
            mPureResultListener.result(isGranted);
        }
    }

    private void combinedResult(Permission permission) {
        if (mCombinedResultListener != null) {
            mCombinedResultListener.result(permission);
        }
    }

    private void eachResult(Permission permission) {
        if (mEachResultListener != null) {
            mEachResultListener.result(permission);
        }
    }

    /******************listener******************/

    public void setPureResultListener(PureResultListener listener) {
        this.mPureResultListener = listener;
    }

    public void setCombinedResultListener(CombinedResultListener listener) {
        this.mCombinedResultListener = listener;
    }

    public void setEachResultListener(EachResultListener listener) {
        this.mEachResultListener = listener;
    }


}
