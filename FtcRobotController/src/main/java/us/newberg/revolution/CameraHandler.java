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
// TODO(Peacock): Make this work, somehow
public class CameraHandler implements Camera.PictureCallback
{
  private static CameraHandler INSTANCE;

  public final ArrayList<byte[]> _pictures;
  private Context _context;

  public CameraHandler()
  {
    _pictures = new ArrayList<byte[]>();
    _context = null;
  }

  @Override public void onPictureTaken(byte[] data, Camera camera)
  {
    _pictures.add(data);
  }

  public void SetContext(Context context) { _context = context; }

  public void TakePicture()
  {
    Intent intent = new Intent();
    intent.setAction(FtcRobotControllerActivity.REQUEST_IMAGE_CAPTURE);
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

    _context.startActivity(intent);
  }

  public ArrayList<byte[]> GetPictures() { return _pictures; }

  public int GetPicCount() { return _pictures.size(); }

  public byte[] GetLastPic() { return _pictures.get(_pictures.size() - 1); }

  public static CameraHandler GetInstance()
  {
    if (INSTANCE == null)
      INSTANCE = new CameraHandler();

    return INSTANCE;
  }
}
