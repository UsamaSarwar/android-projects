package com.example.huntingsparrow.cgpacalculator;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends Activity {
    protected EditText Sub1;
    protected EditText Sub2;
    protected EditText Sub3;
    protected EditText Sub4;
    protected EditText Sub5;
    protected EditText Sub6;
    protected EditText Sub7;
    public double result;
    protected EditText Cr1;
    protected EditText Cr2;
    protected EditText Cr3;
    protected EditText Cr4;
    protected EditText Cr5;
    protected EditText Cr6;
    protected EditText Cr7;
public double calculate_result(int [] sbmarks ,int [] sbcredit)
{
    int [] total_credit=new int[7];
    int [] total_marks=new int[7];
    float [] gpa =new float[7];
double cgpa;
    cgpa = 0f;
    float hp = 0f;
    int TC=0;
    for(int i=0;i<7;i++)
    {
        if(sbcredit[i] > 0) {
            total_credit[i] = sbcredit[i];
            total_marks[i] = (sbcredit[i] * sbmarks[i]) / total_credit[i];
        TC+=total_credit[i];
            if (total_marks[i] >= 90) {
                gpa[i] = 4;

            } else if (total_marks[i] >= 85 && total_marks[i] < 90) {

                gpa[i] = 3.7f;
            } else if (total_marks[i] >= 80 && total_marks[i] < 85) {
                gpa[i] = 3.3f;
            } else if (total_marks[i] >= 75 && total_marks[i] < 80) {
                gpa[i] = 3.00f;
            } else if (total_marks[i] >= 70 && total_marks[i] < 75) {
                gpa[i] = 2.7f;
            } else if (total_marks[i] >= 65 && total_marks[i] < 70) {
                gpa[i] = 2.3f;
            } else if (total_marks[i] >= 60 && total_marks[i] < 65) {
                gpa[i] = 2.00f;
            } else if (total_marks[i] >= 55 && total_marks[i] < 60) {
                gpa[i] = 1.7f;
            } else if (total_marks[i] >= 50 && total_marks[i] < 55) {
                gpa[i] = 1.3f;
            } else if (total_marks[i] < 50) {
                gpa[i] = 0f;
            }
            hp+=gpa[i]*total_credit[i];
        }

        }
cgpa = hp/TC;


   return cgpa;
}


protected  int [] sub = new int[7];
    protected  int [] cr = new int[7];
public static String r;
protected Button  submit;
protected TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        text=(TextView)findViewById((R.id.CGP));
        Sub1 = (EditText)findViewById(R.id.sub3mar);
        Sub2 = (EditText)findViewById(R.id.sub4mar);
        Sub3 = (EditText)findViewById(R.id.sub5mar);
        Sub4 = (EditText)findViewById(R.id.sub6mar);
        Sub5 = (EditText)findViewById(R.id.sub7mar);
        Sub6 = (EditText)findViewById(R.id.sub8mar);
        Sub7 = (EditText)findViewById(R.id.sub9mar);

        Cr1 = (EditText)findViewById(R.id.sub3Credit);
        Cr2 = (EditText)findViewById(R.id.sub4Credit);
        Cr3 = (EditText)findViewById(R.id.sub5Credit);
        Cr4 = (EditText)findViewById(R.id.sub6Credit);
        Cr5 = (EditText)findViewById(R.id.sub7Credit);
        Cr6 = (EditText)findViewById(R.id.sub8Credit);
        Cr7 = (EditText)findViewById(R.id.sub9Credit);

        
        submit = (Button) findViewById(R.id.buttonSubimit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
try {
    sub[0] =Integer.valueOf(Sub1.getText().toString());
    sub[1] = Integer.valueOf(Sub2.getText().toString());
    sub[2] = Integer.valueOf(Sub3.getText().toString());
    sub[3] = Integer.valueOf(Sub4.getText().toString());
    sub[4] = Integer.valueOf(Sub5.getText().toString());
    sub[5] = Integer.valueOf(Sub6.getText().toString());
    sub[6] = Integer.valueOf(Sub7.getText().toString());
////////////////////////////////////////////////////////////////////

    cr[0] = Integer.valueOf(Cr1.getText().toString());
    cr[1] = Integer.valueOf(Cr2.getText().toString());
    cr[2] = Integer.valueOf(Cr3.getText().toString());
    cr[3] = Integer.valueOf(Cr4.getText().toString());
    cr[4] = Integer.valueOf(Cr5.getText().toString());
    cr[5] = Integer.valueOf(Cr6.getText().toString());
    cr[6] = Integer.valueOf(Cr7.getText().toString());
}
catch (NumberFormatException e) {

}

 result = calculate_result(sub,cr);
 r =  String.valueOf(result);
                text.setText(r);

            }
        });

    }
//Initialization


//coding




}
