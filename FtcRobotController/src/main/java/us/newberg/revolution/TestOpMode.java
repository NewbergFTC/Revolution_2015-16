package us.newberg.revolution;

import com.qualcomm.ftccommon.DbgLog;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

/**
 * revolution-2015-16
 * Created by Garrison Peacock on 9/21/15.
 */
public class TestOpMode extends OpMode
{
    @Override
    public void init()
    {
        DbgLog.msg("Init!");
    }

    @Override
    public void loop()
    {
        DbgLog.msg("Loop!");
    }
}
