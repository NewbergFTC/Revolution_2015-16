package us.newberg.revolution;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;

import com.qualcomm.ftcrobotcontroller.FtcRobotControllerActivity;

import java.util.ArrayList;

/**
 * Revolution 2015-2016
 * FTC team 9474
 */
public class RevCamera
{
    private static ArrayList<Bitmap> _photos = new ArrayList<Bitmap>();

    public static void AddPhoto(Bitmap bitmap)
    {
        _photos.add(bitmap);
    }

    public static Bitmap[] GetPhotos()
    {
        return _photos.toArray(new Bitmap[_photos.size()]);
    }

    // No idea if this will work
    public static void TakePhoto()
    {
        Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (takePicture.resolveActivity(FtcRobotControllerActivity.activity.getPackageManager()) != null)
            FtcRobotControllerActivity.activity.startActivityForResult(takePicture, FtcRobotControllerActivity.REQUEST_IMAGE_CAPTURE);
    }
}
