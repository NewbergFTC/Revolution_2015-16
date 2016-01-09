package us.newberg.revolution.opmodes;

import com.peacock.common.math.Util;
import com.qualcomm.robotcore.hardware.DcMotorController;

import us.newberg.revolution.lib.Reference;

/**
 * Revolution 2015-2016
 * FTC team 9474
 */
public class DriverOpMode extends RevOpMode
{
    @Override public void Initialize()
    {
        _frontController.setMotorControllerDeviceMode(DcMotorController.DeviceMode.WRITE_ONLY);

        _leftStickServo.setPosition(Reference.LEFT_SERVO_UP);
        _rightStickServo.setPosition(Reference.RIGHT_SERVO_UP);
    }

    @Override public void Update()
    {
		// Arm values
		float leftYTwo = Util.Clampf(gamepad2.left_stick_y, -1.0f, 1.0f); 
		float leftXTwo = Util.Clampf(gamepad2.left_stick_x, -1.0f, 1.0f);
		float rightYTwo  = Util.Clampf(gamepad2.right_stick_y, -1.0f, 1.0f); 
			
		// TODO(Peacock): Some kind of scaling?
		float leftArmPower = leftYTwo;
		float rightArmPower = leftArmPower;
		float armTiltPower = rightYTwo;

		// Drive values
		float leftYOne = Util.Clampf(gamepad1.left_stick_y, -1.0f, 1.0f);
		float leftXOne = Util.Clampf(gamepad1.left_stick_x, -1.0f, 1.0f);

        // Calculate tank drive speed for each side
        float leftPower = DriveScale(leftYOne);
		float rightPower = DriveScale(leftYOne);
        float leftX = DriveScale(leftXOne);

        if (leftX > 0)
        {
            leftPower += leftX;
            rightPower -= rightPower > 0 ? leftX * 2 : leftX;
        }
        else
        {
            leftPower += leftPower > 0 ? leftX * 2 : leftX;
            rightPower -= leftX;
        }

        Drive(leftPower, rightPower);

		// Servos

        if (gamepad1.a)
        {
            DeployLeftServo();
            StartServoTimer(5000);
        }

        if (gamepad1.x)
        {
            DeployRightServo();
            StartServoTimer(5000);
        }

        if (gamepad1.b)
            RaiseLeftServo();

        if (gamepad1.y)
            RaiseRightServo();

        if (gamepad1.dpad_left)
            RaiseDoor();

        if (gamepad1.dpad_right)
            LowerDoor();

		// Arm

		if (gamepad2.right_bumper)
			_armTiltLocked = true;
		else
			_armTiltLocked = false;

		if (!_armTiltLocked)
			SetArmTiltSpeed(armTiltPower);
		else	// TODO(Peacock): Some value that will lock the motors 
			SetArmTiltSpeed(0);
		
		SetArmLeftSpeed(leftArmPower);
		SetArmRightSpeed(rightArmPower);

        telemetry.addData("left tgt pwr", String.format("%.3f", leftPower));
        telemetry.addData("right tgt pwr", String.format("%.3f", rightPower));
    }

    // Based off the K9TankDrive scale function
    private float DriveScale(float value)
    {
        float[] scaleArray = {0.00f, 0.05f, 0.09f, 0.13f, 0.14f, 0.17f, 0.20f, 0.24f, 0.30f,
                              0.36f, 0.43f, 0.50f, 0.60f, 0.72f, 0.85f, 0.95f, 1.00f};

        int index = Math.abs(Util.RoundReal(value * (scaleArray.length - 1)));

        if (index > scaleArray.length - 1)
            index = scaleArray.length - 1;

        _frontController = hardwareMap.dcMotorController.get("frontCon");
        _backController = hardwareMap.dcMotorController.get("backCon");
        return value < 0 ? -scaleArray[index] : scaleArray[index];
    }
}
