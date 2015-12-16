package us.newberg.revolution;

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

    /**
     * Stops the timer without stopping the motors
     */
    synchronized public void Terminate() { _running.set(false); }

    /**
     * Set the timer delay
     *
     * @param millis Delay in milliseconds
     */
    synchronized public void SetDelay(long millis) { _millis.set(millis); }
    synchronized public boolean GetRunning() { return _running.get(); }

    @Override public void run()
    {
        if (_target == null)
            return;

        if (_millis.get() <= 0)
            return;

        _running.set(true);

        long sleepTime = _millis.get() / 100;
        boolean sleeping = true;

        while (sleeping)
        {
            try
            {
                sleep(sleepTime);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }

            _millis.set(_millis.get() - sleepTime);

            if (_millis.get() <= 0)
                sleeping = false;

            if (!_running.get())
                return;
        }

        _target.SetBackRightSpeed(0);
        _target.SetBackLeftSpeed(0);
        _target.SetFrontLeftSpeed(0);
        _target.SetFrontRightSpeed(0);
    }
}
