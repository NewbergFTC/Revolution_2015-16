package us.newberg.revolution;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicBoolean;
import us.newberg.revolution.opmodes.RevOpMode;

public class ServoTimer extends Thread
{
  private final RevOpMode _target;
  private final AtomicLong _millis;
  private final AtomicBoolean _running;

  public ServoTimer(RevOpMode target, long millis)
  {
    super();

    _target = target;
    _millis = new AtomicLong(millis);
    _running = new AtomicBoolean(false);
  }

  synchronized public void Terminate() { _running.set(false); }
  synchronized public boolean GetRunning() { return _running.get(); }
  synchronized public void SetDelay(long millis) { _millis.set(millis); }

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

    _target.SetLeftStickPosition(0);
    _target.SetRightStickPosition(0.45f);
  }
}
