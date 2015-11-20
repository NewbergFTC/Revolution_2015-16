package us.newberg.revolution.opmodes;

import com.peacock.common.math.Util;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
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
    protected final void Init()
    {
        _frontLeftMotor = hardwareMap.dcMotor.get("frontLeft");
        _frontRightMotor = hardwareMap.dcMotor.get("frontRight");
        _backLeftMotor = hardwareMap.dcMotor.get("backLeft");
        _backRightMotor = hardwareMap.dcMotor.get("backRight");

        _frontController = hardwareMap.dcMotorController.get("frontCon");
        _backController = hardwareMap.dcMotorController.get("backCon");

        _frontLeftMotor.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        WaitOneFullCycle();
        _frontLeftMotor.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        _frontController.setMotorControllerDeviceMode(DcMotorController.DeviceMode.WRITE_ONLY);
    }

    /**
     * Waits on full hardware cycle
     * Catches any exception waiting might throw
     */
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

    /**
     * Sets the fontCon to WRITE_ONLY, then waits one full hardware cycles
     */
    public void SetWriteMode()
    {
        switch (_frontController.getMotorControllerDeviceMode())
        {
            case WRITE_ONLY:
                break;
            case SWITCHING_TO_WRITE_MODE:
                WaitOneFullCycle();
                break;
            default:
                _frontController.setMotorControllerDeviceMode(DcMotorController.DeviceMode.WRITE_ONLY);
                WaitOneFullCycle();
                break;
        }
    }

    /**
     * Sets the fontCon to READ_ONLY, then waits one full hardware cycle
     */
    public void SetReadMode()
    {
        switch (_frontController.getMotorControllerDeviceMode())
        {
            case READ_ONLY:
                break;
            case SWITCHING_TO_READ_MODE:
                WaitOneFullCycle();
                break;
            default:
                _frontController.setMotorControllerDeviceMode(DcMotorController.DeviceMode.READ_ONLY);
                WaitOneFullCycle();
                break;
        }
    }

    /**
     * Get the number of ticks from the frontLeft motor
     * Sets the frontCon to READ_ONLY
     *
     * @return Number of ticks
     */
    public int GetTicks()
    {
        SetReadMode();
        return _frontLeftMotor.getCurrentPosition();
    }

    /**
     * Sets the drive motors to their respective powers
     * Sets the frontCon to WRITE_ONLY
     *
     * @param leftPower  Left side motor power (-1, 1)
     * @param rightPower Right side motor power (-1, 1)
     */
    public void Drive(float leftPower, float rightPower)
    {
        SetWriteMode();
        SetFrontLeftSpeed(-leftPower);
        SetBackLeftSpeed(-leftPower);
        SetFrontRightSpeed(rightPower);
        SetBackRightSpeed(rightPower);
    }

    /**
     * Drives for some amount of time at some power
     *
     * @param leftPower  Left side motor power (-1, 1)
     * @param rightPower Right side motor power (-1, 1)
     * @param millis     Time to wait in milliseconds
     */
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

    /**
     * Drives forward or backward for some distance at some power
     *
     * @param power  Drive Power
     * @param inches Distance in inches
     */
    public void AutoDrive(float power, float inches)
    {
        _frontLeftMotor.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        WaitOneFullCycle();

        float position = GetTicks();
        float ticks = (Reference.ENCODER_TICKS_PER_REVOLUTION / Reference.WHEEL_CIRCUMFERENCE) * inches;
        float target = position + ticks;

        telemetry.addData("Target Ticks", String.valueOf(target));

        if (_timer != null)
            _timer.Terminate();

        _timer = new DriveTimer(this, Util.RoundReal(inches));

        if (position <= target)
        {
            Drive(-power, -power);

            WaitOneFullCycle();

            while (position <= target)
            {
                position = GetTicks();
                telemetry.addData("Ticks", String.valueOf(position));
                telemetry.addData("Target", String.valueOf(target));
            }
         }

        if (position >= target)
        {
            Drive(power, power);

            WaitOneFullCycle();

            while (position >= target)
            {
                position = GetTicks();
                telemetry.addData("Ticks", String.valueOf(position));
                telemetry.addData("Target", String.valueOf(target));
            }
        }

        _timer.Terminate();

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
