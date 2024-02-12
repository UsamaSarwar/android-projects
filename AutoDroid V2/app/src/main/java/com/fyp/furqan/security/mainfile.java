package com.fyp.furqan.security;
import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class mainfile extends Activity {
    public static int n =0;
    EditText a,b;
Button bk;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);
        a=(EditText)findViewById(R.id.email_id);
        b=(EditText)findViewById(R.id.phone_no);
        bk=(Button)findViewById(R.id.save_confg_btn);
        final SharedPreferences mpref = getSharedPreferences("ConfigDB", Activity.MODE_PRIVATE);
   //    startService(new Intent(getApplicationContext(), MyService.class));
String sa=       mpref.getString("Email", "");
 String sb = mpref.getString("Phone","").toString();
        a.setText(sa);
        b.setText(sb);
      //  Toast.makeText(getApplicationContext(), sa+"  "+sb, Toast.LENGTH_SHORT).show();
        bk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = mpref.edit();
                editor.putString("Email",a.getText().toString());
                editor.putString("Phone",b.getText().toString());
                editor.apply();
                Toast.makeText(mainfile.this, "Saved", Toast.LENGTH_SHORT).show();
        finish();
            }
        });


        ComponentName cn=new ComponentName(this, AdminReceiver.class);
        DevicePolicyManager mgr=
                (DevicePolicyManager)getSystemService(DEVICE_POLICY_SERVICE);

        if (mgr.isAdminActive(cn)) {
            int msgId;


            if (mgr.isActivePasswordSufficient()) {
                msgId=R.string.compliant;
            }
            else {
                msgId=R.string.not_compliant;
            }

             //   Toast.makeText(getApplicationContext(), msgId, Toast.LENGTH_LONG).show();
              //Toast.makeText(this, msgId, Toast.LENGTH_LONG).show();
        }
        else {
            Intent intent=
                    new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
            intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, cn);
            intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,
                    getString(R.string.device_admin_explanation));
            startActivity(intent);
        }


    }


    // Method to stop the service

}