package com.fyp.furqan.security;

import android.app.admin.DeviceAdminReceiver;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Furqan on 2/22/2016.
 */

public class AdminReceiver extends DeviceAdminReceiver {

    @Override
    public void onEnabled(Context ctxt, Intent intent) {
        ComponentName cn=new ComponentName(ctxt, AdminReceiver.class);
        DevicePolicyManager mgr=
                (DevicePolicyManager)ctxt.getSystemService(Context.DEVICE_POLICY_SERVICE);

        mgr.setPasswordQuality(cn,
                DevicePolicyManager.PASSWORD_QUALITY_ALPHANUMERIC);

        onPasswordChanged(ctxt, intent);
    }

    @Override
    public void onPasswordChanged(Context ctxt, Intent intent) {
        DevicePolicyManager mgr=
                (DevicePolicyManager)ctxt.getSystemService(Context.DEVICE_POLICY_SERVICE);
        int msgId;

        if (mgr.isActivePasswordSufficient()) {
            msgId=R.string.compliant;
        }
        else {
            msgId=R.string.not_compliant;
        }

        Toast.makeText(ctxt, msgId, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPasswordFailed(Context ctxt, Intent intent) {
        if(mainfile.n==2){

            Intent i= new Intent(ctxt.getApplicationContext(),MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ctxt.startActivity(i);
        }
        else {
            mainfile.n++;
        }



        String tag="tag";
        Log.v(tag, "this massage from error = " + mainfile.n);
    }

    @Override
    public void onPasswordSucceeded(Context ctxt, Intent intent) {


        String tag="tag";
        Log.v(tag, "this massage from success");
    }
}