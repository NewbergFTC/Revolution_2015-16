package us.newberg.revolution;

import android.content.Context;
import android.content.Intent;
import android.hardware.Camera;

import com.qualcomm.ftcrobotcontroller.FtcRobotControllerActivity;

import java.util.ArrayList;

/**
 * Revolution 2015-2016
 * FTC team 9474
 */
public class CameraHandler implements Camera.PictureCallback
{
    private static CameraHandler INSTANCE;
    public static ArrayList<byte[]> pictures = new ArrayList<byte[]>();

    private Context _context;

    public CameraHandler()
    {

    }

    @Override
    public void onPictureTaken(byte[] data, Camera camera)
    {
        pictures.add(data);
    }

    public void SetContext(Context context)
    {
        _context = context;
    }

    public void TakePicture()
    {
        Intent intent = new Intent();
        intent.setAction(FtcRobotControllerActivity.REQUEST_IMAGE_CAPTURE);

        _context.startActivity(intent);
    }

    public static ArrayList<byte[]> GetPictures()
    {
        return pictures;
    }

    public static int GetPicCount()
    {
        return pictures.size();
    }

    public static byte[] GetLastPic()
    {
        return pictures.get(pictures.size() - 1);
    }

    public static CameraHandler GetInstance()
    {
        if (INSTANCE == null)
            INSTANCE = new CameraHandler();

        return INSTANCE;
    }
}
