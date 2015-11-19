package us.newberg.revolution.opmodes;

import com.peacock.common.math.Util;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.Servo;

import us.newberg.revolution.DriveTimer;
import us.newberg.revolution.lib.Reference;

/**
 * Revolution 2015-2016
 * FTC team 9474
 */
public abstract class RevOpMode extends LinearOpMode
{
    // Drive motors
    protected DcMotor _frontLeftMotor;
    protected DcMotor _frontRightMotor;
    protected DcMotor _backLeftMotor;
    protected DcMotor _backRightMotor;

    protected DcMotorController _frontController;
    protected DcMotorController _backController;

    // Zip-line stick things
    protected Servo _leftStick;
    protected Servo _rightStick;

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

    public abstract void Initialize();
    public abstract void Update();

    protected final void Init()
    {
        _frontLeftMotor = hardwareMap.dcMotor.get("frontLeft");
        _frontRightMotor = hardwareMap.dcMotor.get("frontRight");
        _backLeftMotor = hardwareMap.dcMotor.get("backLeft");
        _backRightMotor = hardwareMap.dcMotor.get("backRight");

        _frontController = hardwareMap.dcMotorController.get("frontCon");
        _backController = hardwareMap.dcMotorController.get("backCon");

        //_leftStick = hardwareMap.servo.get("leftStick");
        //_rightStick = hardwareMap.servo.get("rightStick");

        SetDriveSpeed(0);

        _frontLeftMotor.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        WaitOneFullCycle();
        WaitOneFullCycle();
        _frontLeftMotor.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        _frontController.setMotorControllerDeviceMode(DcMotorController.DeviceMode.WRITE_ONLY);
    }

    public int GetTicks()
    {
        SetReadMode();
        return _frontLeftMotor.getCurrentPosition();
    }

    public void SetWriteMode()
    {
        _frontController.setMotorControllerDeviceMode(DcMotorController.DeviceMode.WRITE_ONLY);
        WaitOneFullCycle();
        WaitOneFullCycle();
        WaitOneFullCycle();
    }

    public void SetReadMode()
    {
        _frontController.setMotorControllerDeviceMode(DcMotorController.DeviceMode.READ_ONLY);
        WaitOneFullCycle();
        WaitOneFullCycle();
        WaitOneFullCycle();
    }

    public void Drive(float leftPower, float rightPower)
    {
        SetWriteMode();

        SetFrontLeftSpeed(-leftPower);
        SetBackLeftSpeed(-leftPower);
        SetFrontRightSpeed(rightPower);
        SetBackRightSpeed(rightPower);
    }

    public void WaitOneFullCycle()
    {
        try
        {
            waitOneFullHardwareCycle();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void AutoDrive(float power, float inches)
    {
        float position = GetTicks();
        float ticks = (Reference.ENCODER_TICKS_PER_REVOLUTION / Reference.WHEEL_CIRCUMFERENCE) * inches;
        float target = position + ticks;

        telemetry.addData("Target Ticks", String.valueOf(target));

        if (_timer != null)
            _timer.Terminate();

        _timer = new DriveTimer(this, Util.RoundReal(inches * 0.9));

        if (position <= target)
        {
            Drive(-power, -power);
            WaitOneFullCycle();

            while (position <= target)
            {
                position = GetTicks();
                WaitOneFullCycle();
            }
         }

        if (position >= target)
        {
            Drive(power, power);
            WaitOneFullCycle();

            while (position >= target)
            {
                position = GetTicks();
                WaitOneFullCycle();
            }
        }

        _timer.Terminate();
    }

    public void TimedDrive(float leftPower, float rightPower, long millis)
    {
        if (_timer != null)
            _timer.Terminate();

        _timer = new DriveTimer(this, millis);

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

    public void Turn(float degree, float power)
    {
        float position = GetTicks();
        float target = position + degree;

        telemetry.addData("Target Ticks", String.valueOf(target));

        if (_timer != null)
            _timer.Terminate();

        // TODO(Peacock): Real wait time here
        _timer = new DriveTimer(this, 20);

        while (position <= target)
        {
            Drive(power, -power);
            position = GetTicks();
        }

        while (position >= target)
        {
            Drive(-power, power);
            position = GetTicks();
        }

        _timer.Terminate();
    }

    public void SetLeftStick(float position)
    {
        _leftStick.setPosition(position);
    }

    public void SetRightStick(float position)
    {
        _rightStick.setPosition(position);
    }

    public void SetDriveSpeed(float speed)
    {
        SetFrontLeftSpeed(speed);
        SetFrontRightSpeed(speed);
        SetBackLeftSpeed(speed);
        SetBackRightSpeed(speed);
    }

    public void SetFrontLeftSpeed(float speed)
    {
        float spd = Util.Clampf(speed, -1.0f, 1.0f);

        _frontLeftMotor.setPower(spd);
    }

    public void SetFrontRightSpeed(float speed)
    {
        float spd = Util.Clampf(speed, -1.0f, 1.0f);

        _frontRightMotor.setPower(spd);
    }

    public void SetBackLeftSpeed(float speed)
    {
        float spd = Util.Clampf(speed, -1.0f, 1.0f);

        _backLeftMotor.setPower(spd);
    }

    public void SetBackRightSpeed(float speed)
    {
        float spd = Util.Clampf(speed, -1.0f, 1.0f);

        _backRightMotor.setPower(spd);
    }
}
