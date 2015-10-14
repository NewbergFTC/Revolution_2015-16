package us.newberg.revolution.opmodes;

import android.graphics.Bitmap;

import us.newberg.revolution.RevCamera;

/**
 * Revolution 2015-2016
 * FTC team 9474
 */
public class TestOpMode extends RevOpMode
{
    private boolean _first;

    @Override
    public void Initialize()
    {
        _first = true;
    }

    @Override
    public void Update()
    {
        if (_first)
        {
            // Drive forward at full speed for 10 seconds
            TimedDrive(1.0f, 1.0f, 10000);

            _first = false;
        }

        if (gamepad1.a)
        {
            RevCamera.TakePhoto();

            Bitmap[] photos = RevCamera.GetPhotos();
            Bitmap bitmap = photos[photos.length - 1];

            int center = bitmap.getPixel(bitmap.getWidth() / 2, bitmap.getHeight() / 2);

            telemetry.addData("Center pixel", String.valueOf(center));
        }
    }
}
