package us.newberg.revolution;

import com.qualcomm.ftccommon.DbgLog;

import us.newberg.revolution.opmodes.RevOpMode;

/**
 * Revolution 2015-2016
 * FTC team 9474
 */
// TODO(Peacock): Test this
public class DriveTimer implements Runnable
{
    final private RevOpMode _target;
    final private long _millis;

    public DriveTimer(RevOpMode target, long millis)
    {
        _target = target;
        _millis = millis;
    }

    @Override
    public void run()
    {
        try
        {
            Thread.sleep(_millis);
        } catch (InterruptedException e)
        {
            e.printStackTrace();
            DbgLog.error("DriveTimer sleep was interrupted");
        }

        _target.SetBackRightSpeed(0);
        _target.SetBackLeftSpeed(0);
        _target.SetFrontLeftSpeed(0);
        _target.SetFrontRightSpeed(0);
    }
}
