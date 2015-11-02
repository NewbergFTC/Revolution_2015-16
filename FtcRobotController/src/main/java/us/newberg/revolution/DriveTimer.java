package us.newberg.revolution;

import com.qualcomm.ftccommon.DbgLog;

import java.util.concurrent.atomic.AtomicBoolean;

import us.newberg.revolution.opmodes.RevOpMode;

/**
 * Revolution 2015-2016
 * FTC team 9474
 */
public class DriveTimer extends Thread
{
    final private RevOpMode _target;
    final private AtomicBoolean _running;
    private long _millis;

    public DriveTimer(RevOpMode target, long millis)
    {
        super();

        _target = target;
        _millis = millis;
        _running = new AtomicBoolean(false);
    }

    synchronized public void Terminate()
    {
        _running.set(false);
    }

    @Override
    public void run()
    {
        if (_target == null)
            return;

        if (_millis <= 0)
            return;

        _running.set(true);

        while (_running.get())
        {
            try
            {
                sleep(_millis);
            } catch (InterruptedException e)
            {
                e.printStackTrace();
                DbgLog.error("DriveTimer sleep was interrupted");
            }

            _millis -= _millis / 10;

            if (_millis <= 0)
                _running.set(false);
        }

        _target.SetBackRightSpeed(0);
        _target.SetBackLeftSpeed(0);
        _target.SetFrontLeftSpeed(0);
        _target.SetFrontRightSpeed(0);
    }
}
