package com.fyp.furqan.security;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.media.Image;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends Activity implements SurfaceHolder.Callback
{
    String photoFile;
    //a variable to store a reference to the Image View at the main.xml file
    private ImageView iv_image;
    //a variable to store a reference to the Surface View at the main.xml file
    private SurfaceView sv;
    Image data;

    //a bitmap to display the captured image
    private Bitmap bmp;

    //Camera variables
    //a surface holder
    private SurfaceHolder sHolder;
    //a variable to control the camera
    private Camera mCamera;
    //the camera parameters
    private Parameters parameters;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activit_main);

        //get the Image View at the main.xml file
        iv_image = (ImageView) findViewById(R.id.imageView);

        //get the Surface View at the main.xml file
        sv = (SurfaceView) findViewById(R.id.surfaceView);

        //Get a surface
        sHolder = sv.getHolder();

        //add the callback interface methods defined below as the Surface View callbacks
        sHolder.addCallback(this);

        //tells Android that this surface will have its data constantly replaced
        sHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

    }

    @Override
    public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3)
    {
        //get camera parameters
     //   mCamera.setDisplayOrientation(result);

        parameters = mCamera.getParameters();
       parameters.setRotation(270);

        //set camera parameters

        mCamera.setParameters(parameters);
        mCamera.startPreview();

        //sets what code should be executed after the picture is taken
        Camera.PictureCallback mCall = new Camera.PictureCallback()
        {
            @Override
            public void onPictureTaken(byte[] data, Camera camera)
            {
                //decode the data obtained by the camera into a Bitmap
                bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
                //set the iv_image
                iv_image.setImageBitmap(bmp);
                File pictureFileDir = getDir();

                if (!pictureFileDir.exists() && !pictureFileDir.mkdirs()) {

                    Log.d("TAG", "Can't create directory to save image.");
                    return;

                }
               SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddhhmmss");
                String date = dateFormat.format(new Date());
                photoFile = "myphoto_" + date + ".jpg";

                String filename = pictureFileDir.getPath() + File.separator + photoFile;
                Intent I = new Intent(getApplicationContext(),Email_Activity.class);
                File pictureFile = new File(filename);

                try {
                    FileOutputStream fos = new FileOutputStream(pictureFile);
                    fos.write(data);
                    fos.close();
                   // Toast.makeText(getApplicationContext(),""+filename+"  ",Toast.LENGTH_LONG).show();
                   I.putExtra("abc",filename);
//Log.d("hahahah",filename);

                   startActivity(I);
                    finish();
                } catch (Exception error) {
                    Log.d("TAG", "File" + filename + "not saved: "
                            + error.getMessage());

                }
            }
        };

        mCamera.takePicture(null, null, mCall);
    }
    private File getDir() {
        File sdDir = Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        return new File(sdDir, "furqan");
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder)
    {
        // The Surface has been created, acquire the camera and tell it where
        // to draw the preview.
       int currentCameraId = Camera.CameraInfo.CAMERA_FACING_FRONT;
        mCamera = Camera.open(currentCameraId);
        try {

            mCamera.setPreviewDisplay(holder);

        } catch (IOException exception) {
            mCamera.release();
            mCamera = null;

        }
    }
    @Override
    public void surfaceDestroyed(SurfaceHolder holder)
    {
        //stop the preview
        mCamera.stopPreview();
        //release the camera
        mCamera.release();
        //unbind the camera from this object
        mCamera = null;
    }
}