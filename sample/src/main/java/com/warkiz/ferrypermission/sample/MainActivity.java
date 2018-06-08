package com.warkiz.ferrypermission.sample;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.warkiz.ferrypermission.CombinedResultListener;
import com.warkiz.ferrypermission.EachResultListener;
import com.warkiz.ferrypermission.FerryPermission;
import com.warkiz.ferrypermission.Permission;
import com.warkiz.ferrypermission.PureResultListener;

import static android.content.pm.PackageManager.PERMISSION_DENIED;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.observe).setOnClickListener(this);
        findViewById(R.id.observeCombined).setOnClickListener(this);
        findViewById(R.id.observeEach).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.observe:
                observe();
                break;
            case R.id.observeCombined:
                observeCombined();
                break;
            case R.id.observeEach:
                observeEach();
                break;
        }
    }

    private void observeEach() {
        new FerryPermission(this)
                .request(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .observeEach(new EachResultListener() {
                    @Override
                    public void result(Permission permission) {//will callback 2 times depends on requesting permission'count.
                        if (permission.isGranted) {
                            log("Yes, Permission: " + permission.name + " is granted!");
                        } else if (permission.shouldShowRequestPermissionRationale) {
                            log("No, Permission: " + permission.name + " is denied but will ask again.");
                        } else {
                            log("No, Permission: " + permission.name + " is denied and never ask again.");
                        }
                    }
                });

    }

    private void observeCombined() {
        new FerryPermission(this)
                .request(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .observeCombined(new CombinedResultListener() {
                    @Override
                    public void result(Permission permission) {
                        if (permission.isGranted) {
                            //all permission are granted, go ahead.
                            log("All Granted!");
                        } else if (permission.shouldShowRequestPermissionRationale) {
                            //at least one permission is denied without never ask again.
                            log("Denied and will ask again");
                        } else {
                            //at least one permission is denied with never ask again.
                            //at this moment you should go to the settings page.
                            log("Denied and never ask again!");
                            //引导用户至设置页手动授权
                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package", MainActivity.this.getApplicationContext().getPackageName(), null);
                            intent.setData(uri);
                            MainActivity.this.startActivityForResult(intent, PERMISSION_DENIED);
                        }
                    }
                });
    }

    private void observe() {
        new FerryPermission(this)
                .request(Manifest.permission.CAMERA)
                .observe(new PureResultListener() {
                    @Override
                    public void result(boolean isGranted) {
                        if (isGranted) {
                            //got permission, go ahead.
                            log("Granted!");
                        } else {
                            //permission denied.
                            log("Denied!");
                        }
                    }
                });
    }

    private void log(String log) {
        Log.i("FerryPermissionMainA", "log: " + log);
    }

}
