package us.newberg.revolution;

import com.qualcomm.ftccommon.DbgLog;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

import us.newberg.revolution.opmodes.RevOpMode;

/**
 * Revolution 2015-2016
 * FTC team 9474
 */
public class DriveTimer extends Thread
{
    final private RevOpMode _target;
    final private AtomicBoolean _running;
    final private AtomicLong _millis;

    public DriveTimer(RevOpMode target, long millis)
    {
        super();

        _target = target;
        _millis = new AtomicLong(millis);
        _running = new AtomicBoolean(false);
    }

    synchronized public void Terminate()
    {
        _running.set(false);
    }

    synchronized public void SetDelay(long millis)
    {
        _millis.set(millis);
    }

    synchronized public boolean GetRunning() { return _running.get(); }

    @Override
    public void run()
    {
        if (_target == null)
            return;

        if (_millis.get() <= 0)
            return;

        _running.set(true);

        long sleepTime = _millis.get() / 10;

        while (_running.get())
        {
            try
            {
                sleep(sleepTime);
            } catch (InterruptedException e)
            {
                e.printStackTrace();
                DbgLog.error("DriveTimer sleep was interrupted");
            }

            _millis.set(_millis.get() - sleepTime);

            if (_millis.get() <= 0)
                _running.set(false);
        }

        _target.SetWriteMode();
        _target.SetBackRightSpeed(0);
        _target.SetBackLeftSpeed(0);
        _target.SetFrontLeftSpeed(0);
        _target.SetFrontRightSpeed(0);
    }
}
