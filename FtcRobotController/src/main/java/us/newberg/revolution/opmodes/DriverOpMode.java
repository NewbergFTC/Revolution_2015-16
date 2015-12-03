package us.newberg.revolution.opmodes;

import com.peacock.common.math.Util;
import com.qualcomm.robotcore.hardware.DcMotorController;

import us.newberg.revolution.ServoTimer;
import us.newberg.revolution.lib.Reference;

/**
 * Revolution 2015-2016
 * FTC team 9474
 */
public class DriverOpMode extends RevOpMode
{
    @Override
    public void Initialize()
    {
        _frontController.setMotorControllerDeviceMode(DcMotorController.DeviceMode.WRITE_ONLY);

		_leftStickServo.setPosition(Reference.LEFT_SERVO_UP);
		_rightStickServo.setPosition(Reference.RIGHT_SERVO_UP);
    }

    @Override
    public void Update()
    {
        // TODO(Peacock): Talk with drivers about desired controls

        // Get and scale the joystick values
        float leftYOne = Util.Clampf(gamepad1.left_stick_y, -1.0f, 1.0f);
        float leftXOne = Util.Clampf(gamepad1.left_stick_x, -1.0f, 1.0f);

        // Calculate tank drive speed for each side
        float leftPower = Scale(leftYOne);
        float rightPower = Scale(leftYOne);

        float leftX = Scale(leftXOne);

        if (leftX > 0)
        {
            leftPower += leftX;
            rightPower -= rightPower > 0 ? leftX * 2 : leftX;
        }
        else if (leftX < 0)
        {
            leftPower += leftPower > 0 ? leftX * 2 : leftX;
            rightPower -= leftX;
        }

		if (gamepad1.a)
		{
			_leftStickServo.setPosition(Reference.LEFT_SERVO_UP);
			_rightStickServo.setPosition(Reference.RIGHT_SERVO_UP);

			_servoTimer.Terminate();
			_servoTimer = new ServoTimer(this, 5000);
			_servoTimer.start();
		}

		if (gamepad1.b)
			_leftStickServo.setPosition(Reference.LEFT_SERVO_UP);

		if (gamepad1.x)
		{
			_rightStickServo.setPosition(Reference.RIGHT_SERVO_DEPLOYED);
			_leftStickServo.setPosition(Reference.LEFT_SERVO_UP);

			_servoTimer.Terminate();
			_servoTimer = new ServoTimer(this, 5000);
			_servoTimer.start();
		}

		if (gamepad1.y)
			_rightStickServo.setPosition(Reference.RIGHT_SERVO_UP);

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

        int index = Math.abs(Util.RoundReal(value * (scaleArray.length - 1)));

        if (index > scaleArray.length - 1)
            index = scaleArray.length - 1;

        boolean negative = value < 0;

        return negative ? -scaleArray[index] : scaleArray[index];
    }
}
