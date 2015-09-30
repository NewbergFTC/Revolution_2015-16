package us.newberg.revolution;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

/**
 * revolution-2015-16
 * Created by Garrison Peacock on 9/21/15.
 */
public class TestOpMode extends OpMode
{
    private DcMotor motor;

    @Override
    public void init()
    {
        motor = hardwareMap.dcMotor.get("mot");
    }

    @Override
    public void loop()
    {
        float leftY = gamepad1.left_stick_y;
        Range.clip(leftY, -1.0f, 1.0f);

        motor.setPower(leftY);
    }
}
