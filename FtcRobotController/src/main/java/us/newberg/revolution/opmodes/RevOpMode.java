package us.newberg.revolution.opmodes;

import com.peacock.common.math.Util;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotorController;

import us.newberg.revolution.DriveTimer;
import us.newberg.revolution.lib.Reference;

/**
 * Revolution 2015-2016
 * FTC team 9474
 */
public class RevOpMode extends LinearOpMode
{
    // Drive motors
    protected DcMotor _frontLeftMotor;
    protected DcMotor _frontRightMotor;
    protected DcMotor _backLeftMotor;
    protected DcMotor _backRightMotor;

    // Motor controllers
    protected DcMotorController _frontController;
    protected DcMotorController _backController;

	// Stick servos
	protected Servo _leftStickServo;
	protected Servo _rightStickServo;

    // Drive timer
    // For timed autonomous stuff
    protected DriveTimer _timer;

    public RevOpMode()
    {
        super();

        _timer = new DriveTimer(this, 0);
    }

    @Override
    public void runOpMode() throws InterruptedException
    {
        Init();

        // Call subclass init
        Initialize();

        waitForStart();

        while (opModeIsActive())
        {
            Update();

            waitForNextHardwareCycle();
        }
    }

    public void Initialize() {}
    public void Update() {}

    /**
     * Initialize required components
     */
    protected final void Init() throws InterruptedException
    {
        _frontLeftMotor = hardwareMap.dcMotor.get("frontLeft");
        _frontRightMotor = hardwareMap.dcMotor.get("frontRight");
        _backLeftMotor = hardwareMap.dcMotor.get("backLeft");
        _backRightMotor = hardwareMap.dcMotor.get("backRight");

        _frontController = hardwareMap.dcMotorController.get("frontCon");
        _backController = hardwareMap.dcMotorController.get("backCon");

		_leftStickServo = hardwareMap.servo.get("leftStick");
		_rightStickServo = hardwareMap.servo.get("rightStick");

        _frontLeftMotor.setMode(DcMotorController.RunMode.RESET_ENCODERS);
		Wait();

        _frontLeftMotor.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
		Wait();

        _frontController.setMotorControllerDeviceMode(DcMotorController.DeviceMode.WRITE_ONLY);
    }

    /**
     * Sets the drive motors to their respective powers
     *
     * @param leftPower  Left side motor power (-1, 1)
     * @param rightPower Right side motor power (-1, 1)
     */
    public void Drive(float leftPower, float rightPower)
    {
    	// TODO(Peacock): Is the left side drifting?
        double leftSpd = Util.Clampd(leftPower, -1.0, 1.0);
        double rightSpd = Util.Clampd(rightPower, -1.0, 1.0);

        _frontLeftMotor.setPower(-leftSpd);
        _backLeftMotor.setPower(-leftSpd);
        _frontRightMotor.setPower(rightSpd);
        _backRightMotor.setPower(rightSpd);
    }

    /**
     * Drives for some amount of time at some power
     *
     * @param leftPower  Left side motor power (-1, 1)
     * @param rightPower Right side motor power (-1, 1)
     * @param millis     Time to wait in milliseconds
     */
    public void TimedDrive(float leftPower, float rightPower, long millis) throws InterruptedException
    {
        if (_timer != null)
            _timer.Terminate();

        _timer = new DriveTimer(this, millis);

        _frontController.setMotorControllerDeviceMode(DcMotorController.DeviceMode.WRITE_ONLY);
		Wait();

        _timer.start();
        Drive(leftPower, rightPower);

        try
        {
            _timer.join();
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }

	/**
	 * Waits for 1+5 hardware cycles
	 */
    public void Wait() throws InterruptedException
    {
        waitForNextHardwareCycle();
        waitOneFullHardwareCycle();
        waitOneFullHardwareCycle();
        waitOneFullHardwareCycle();
        waitOneFullHardwareCycle();
        waitOneFullHardwareCycle();
    }

    /**
     * Drives forward or backward for some distance at some power
     *
     * @param power  Drive Power
     * @param inches Distance in inches
     */
    public void AutoDrive(float power, float inches) throws InterruptedException
    {
        _frontController.setMotorControllerDeviceMode(DcMotorController.DeviceMode.WRITE_ONLY);
        Wait();

        _frontLeftMotor.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        Wait();

        _frontLeftMotor.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        Wait();

        _frontController.setMotorControllerDeviceMode(DcMotorController.DeviceMode.READ_ONLY);
        Wait();

        float position = _frontLeftMotor.getCurrentPosition();
        float ticks = (Reference.ENCODER_TICKS_PER_REVOLUTION / Reference.WHEEL_CIRCUMFERENCE) * inches;
        float target = position + ticks;

        telemetry.addData("Target Ticks", String.valueOf(target));

        _frontController.setMotorControllerDeviceMode(DcMotorController.DeviceMode.WRITE_ONLY);
        Wait();

        if (position <= target)
        {
            Drive(-power, -power);
            Wait();

            _frontController.setMotorControllerDeviceMode(DcMotorController.DeviceMode.READ_ONLY);
            Wait();

            while (position <= target)
            {
                position = _frontLeftMotor.getCurrentPosition();
                telemetry.addData("Ticks", String.valueOf(position));
                telemetry.addData("Target", String.valueOf(target));

                waitForNextHardwareCycle();
            }
        }
        else if (position >= target)
        {
            Drive(power, power);
            Wait();

            _frontController.setMotorControllerDeviceMode(DcMotorController.DeviceMode.READ_ONLY);
            Wait();

            while (position >= target)
            {
                position = _frontLeftMotor.getCurrentPosition();
                telemetry.addData("Ticks", String.valueOf(position));
                telemetry.addData("Target", String.valueOf(target));

                waitForNextHardwareCycle();
            }
        }

        _frontController.setMotorControllerDeviceMode(DcMotorController.DeviceMode.WRITE_ONLY);
        Wait();

        Drive(0, 0);
    }

    public void Turn(float degree, float power) throws InterruptedException
    {
        _frontController.setMotorControllerDeviceMode(DcMotorController.DeviceMode.WRITE_ONLY);
        Wait();
        _frontLeftMotor.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        Wait();
        _frontLeftMotor.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        Wait();
        _frontController.setMotorControllerDeviceMode(DcMotorController.DeviceMode.READ_ONLY);
        Wait();

        float position = _frontLeftMotor.getCurrentPosition();
        float ticks = (Reference.ENCODER_TICKS_PER_REVOLUTION / Reference.WHEEL_CIRCUMFERENCE) * degree / 360;
        float target = position + ticks;
        target *= 53;

        telemetry.addData("Target", String.valueOf(target));

        _frontController.setMotorControllerDeviceMode(DcMotorController.DeviceMode.WRITE_ONLY);
        Wait();

        if (position < target)  // Right
        {
            Drive(-power, power);
            Wait();

            _frontController.setMotorControllerDeviceMode(DcMotorController.DeviceMode.READ_ONLY);
            Wait();

            while (position < target)
            {
                position = _frontLeftMotor.getCurrentPosition();
                telemetry.addData("Ticks:", String.valueOf(position));

                waitForNextHardwareCycle();
            }
        }
        else if (position > target) // Left
        {
            Drive(power, -power);
            Wait();

            _frontController.setMotorControllerDeviceMode(DcMotorController.DeviceMode.READ_ONLY);
            Wait();

            while (position > target)
            {
                position = _frontLeftMotor.getCurrentPosition();
                telemetry.addData("Ticks:", String.valueOf(position));

                waitForNextHardwareCycle();
            }
        }

        _frontController.setMotorControllerDeviceMode(DcMotorController.DeviceMode.WRITE_ONLY);
        Wait();

        Drive(0, 0);
    }

    /**
     * Sets the front left motor speed
     *
     * @param speed Motor speed
     */
    public void SetFrontLeftSpeed(float speed)
    {
        float spd = Util.Clampf(speed, -1.0f, 1.0f);

        _frontLeftMotor.setPower(spd);
    }

    /**
     * Sets the front right motor speed
     *
     * @param speed Motor speed
     */
    public void SetFrontRightSpeed(float speed)
    {
        float spd = Util.Clampf(speed, -1.0f, 1.0f);

        _frontRightMotor.setPower(spd);
    }

    /**
     * Sets the back left motor speed
     *
     * @param speed Motor speed
     */
    public void SetBackLeftSpeed(float speed)
    {
        float spd = Util.Clampf(speed, -1.0f, 1.0f);

        _backLeftMotor.setPower(spd);
    }

    /**
     * Sets the back right motor speed
     *
     * @param speed Motor speed
     */
    public void SetBackRightSpeed(float speed)
    {
        float spd = Util.Clampf(speed, -1.0f, 1.0f);

        _backRightMotor.setPower(spd);
    }
}
