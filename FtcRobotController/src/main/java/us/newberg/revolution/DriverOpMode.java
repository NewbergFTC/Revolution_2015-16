package us.newberg.revolution;

import com.peacock.common.math.Util;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * revolution-2015-16
 * Created by Garrison Peacock on 9/29/15.
 */
public class DriverOpMode extends OpMode
{
    // Drive motors
    private DcMotor _frontLeftMotor;
    private DcMotor _frontRightMotor;
    private DcMotor _backLeftMotor;
    private DcMotor _backRightMotor;

    @Override
    public void init()
    {
        // Get the drive motors
        _frontLeftMotor = hardwareMap.dcMotor.get("frontLeft");
        _frontRightMotor = hardwareMap.dcMotor.get("frontRight");
        _backLeftMotor = hardwareMap.dcMotor.get("backLeft");
        _backRightMotor = hardwareMap.dcMotor.get("backRight");
    }

    @Override
    public void loop()
    {
        // Get and scale the joystick values
        float leftYOne = Util.Clampf(gamepad1.left_stick_y, -1.0f, 1.0f);
        float leftXOne = Util.Clampf(gamepad1.left_stick_x, -1.0f, 1.0f);
        float rightYOne = Util.Clampf(gamepad1.right_stick_y, -1.0f, 1.0f);
        float rightXOne = Util.Clampf(gamepad1.right_stick_x, -1.0f, 1.0f);

        // Calculate tank drive speed for each side
        float leftPower = Scale(leftYOne);
        float rightPower = Scale(rightYOne);

        // Set the motor speed
        _frontLeftMotor.setPower(leftPower);
        _backLeftMotor.setPower(leftPower);
        _frontRightMotor.setPower(rightPower);
        _backRightMotor.setPower(rightPower);

        // Not sure how to access this data yet, but it could be really cool.
        telemetry.addData("left tgt pwr", String.format("%.3f", leftPower));
        telemetry.addData("right tgt pwr", String.format("%.3f", rightPower));
    }

    // Based off the K9TankDrive scale function
    private float Scale(float value)
    {
        // TODO: Adjust these values to the driver's liking
        float[] scaleArray = { 0.0f, 0.05f, 0.09f, 0.10f, 0.12f, 0.15f, 0.18f, 0.24f,
                               0.30f, 0.36f, 0.43f, 0.50f, 0.60f, 0.72f, 0.85f, 0.95f, 1.00f };

        int index = Util.RoundReal(value * (scaleArray.length - 1));

        if (index > scaleArray.length - 1)
            index = scaleArray.length - 1;

        boolean negative = value <= 0;

        return negative ? -scaleArray[index] : scaleArray[index];
    }
}
