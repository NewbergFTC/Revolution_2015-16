package us.newberg.revolution.opmodes;

import com.peacock.common.math.Util;
import com.qualcomm.ftccommon.DbgLog;

/**
 * Revolution 2015-2016
 * FTC team 9474
 */
public class DriverOpMode extends RevOpMode
{
    @Override
    public void Initialize()
    {

    }

    @Override
    public void Update()
    {
        // TODO(Peacock): Talk with drivers about desired controls

        // TODO(Peacock): Find out why gamepad isn't working
        // Get and scale the joystick values
        float leftYOne = Util.Clampf(gamepad1.left_stick_y, -1.0f, 1.0f);
        float leftXOne = Util.Clampf(gamepad1.left_stick_x, -1.0f, 1.0f);
        float rightYOne = Util.Clampf(gamepad1.right_stick_y, -1.0f, 1.0f);
        float rightXOne = Util.Clampf(gamepad1.right_stick_x, -1.0f, 1.0f);

        // Calculate tank drive speed for each side
        float leftPower = Scale(leftYOne);
        float rightPower = Scale(rightYOne);

        Drive(leftPower, rightPower);

        telemetry.addData("left tgt pwr", String.format("%.3f", leftPower));
        telemetry.addData("right tgt pwr", String.format("%.3f", rightPower));
    }

    // Based off the K9TankDrive scale function
    private float Scale(float value)
    {
        // TODO(Peacock): Adjust these values to the driver's liking
        float[] scaleArray = { 0.0f, 0.05f, 0.09f, 0.10f, 0.12f, 0.15f, 0.18f, 0.24f,
                               0.30f, 0.36f, 0.43f, 0.50f, 0.60f, 0.72f, 0.85f, 0.95f, 1.00f };

        int index = Util.RoundReal(value * (scaleArray.length - 1));

        if (index > scaleArray.length - 1)
            index = scaleArray.length - 1;

        boolean negative = value <= 0;

        return negative ? -scaleArray[index] : scaleArray[index];
    }
}
