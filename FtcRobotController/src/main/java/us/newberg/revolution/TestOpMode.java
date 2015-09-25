package us.newberg.revolution;

import com.qualcomm.ftccommon.DbgLog;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * revolution-2015-16
 * Created by Garrison Peacock on 9/21/15.
 */
public class TestOpMode extends OpMode
{
    DcMotor motor;

    @Override
    public void init()
    {
        motor = hardwareMap.dcMotor.get("mot");

        DbgLog.msg("Init!");
    }

    @Override
    public void loop()
    {
        motor.setPower(1);

        DbgLog.msg("Loop!");
    }
}
