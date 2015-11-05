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
public abstract class RevOpMode extends LinearOpMode
{
    // Drive motors
    private DcMotor _frontLeftMotor;
    private DcMotor _frontRightMotor;
    private DcMotor _backLeftMotor;
    private DcMotor _backRightMotor;

//    // Atomic variables so we don't run into data races when doing timed autonomous stuff
//    private AtomicDouble _frontLeftSpeed;
//    private AtomicDouble _frontRightSpeed;
//    private AtomicDouble _backLeftSpeed;
//    private AtomicDouble _backRightSpeed;
//
    protected DriveTimer _timer;

    public RevOpMode()
    {
        super();

//        _frontLeftSpeed = new AtomicDouble();
//        _frontRightSpeed = new AtomicDouble();
//        _backLeftSpeed = new AtomicDouble();
//        _backRightSpeed = new AtomicDouble();

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

//            _frontLeftMotor.setPower(GetFrontLeftSpeed());
//            _frontRightMotor.setPower(GetFrontRightSpeed());
//            _backLeftMotor.setPower(GetBackLeftSpeed());
//            _backRightMotor.setPower(GetBackRightSpeed());
        }
    }

    public abstract void Initialize();
    public abstract void Update();

    protected void Init()
    {
        _frontLeftMotor = hardwareMap.dcMotor.get("frontLeft");
        _frontRightMotor = hardwareMap.dcMotor.get("frontRight");
        _backLeftMotor = hardwareMap.dcMotor.get("backLeft");
        _backRightMotor = hardwareMap.dcMotor.get("backRight");

        SetDriveSpeed(0);
    }

    public void Drive(float leftPower, float rightPower)
    {
        SetFrontLeftSpeed(-leftPower);
        SetBackLeftSpeed(-leftPower);
        SetFrontRightSpeed(rightPower);
        SetBackRightSpeed(rightPower);
    }

    // TODO(Peacock): Test this
    public void AutoDrive(float power, float inches)
    {
        _frontLeftMotor.setMode(DcMotorController.RunMode.RUN_TO_POSITION);

        SetDriveSpeed(0);

        float ticks = (Reference.ENCODER_TICKS_PER_REVOLUTION / Reference.WHEEL_CIRCUMFRENCE) * inches;
        float goal = _frontLeftMotor.getCurrentPosition() + ticks;
        telemetry.addData("Target: ", String.valueOf(goal));

        _frontLeftMotor.setTargetPosition(Util.RoundReal(goal));

        _timer.Terminate();
        _timer.SetDelay(Util.RoundReal((inches / 12) + 1));

        _timer.start();

        if (_frontLeftMotor.getTargetPosition() < goal)
        {
            while (_frontLeftMotor.getTargetPosition() < goal)
            {
                telemetry.addData("Current: ", String.valueOf(_frontLeftMotor.getTargetPosition()));

                SetFrontLeftSpeed(-power);
                SetFrontRightSpeed(power);
                SetBackLeftSpeed(-power);
                SetBackRightSpeed(power);
            }
        }

        if (_frontLeftMotor.getTargetPosition() > goal)
        {
            while (_frontLeftMotor.getTargetPosition() < goal)
            {
                telemetry.addData("Current: ", String.valueOf(_frontLeftMotor.getTargetPosition()));

                SetFrontLeftSpeed(power);
                SetFrontRightSpeed(-power);
                SetBackLeftSpeed(power);
                SetBackRightSpeed(-power);
            }
        }

        _timer.Terminate();
        try
        {
            _timer.join();
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }

        SetDriveSpeed(0);

        _frontLeftMotor.setMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
    }

    public void TimedDrive(float leftPower, float rightPower, long millis)
    {
        if (_timer == null)
            _timer = new DriveTimer(this, millis + 100);
        else
        {
            _timer.Terminate();
            _timer.SetDelay(millis + 100);
        }

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

    // TODO(Peacock): Test this
    final public void Turn(float degree, float speed)
    {
        SetDriveSpeed(0);

        _frontLeftMotor.setMode(DcMotorController.RunMode.RUN_TO_POSITION);

        float goal = _frontLeftMotor.getCurrentPosition() + degree;
        telemetry.addData("Target: ", String.valueOf(goal));

        _frontLeftMotor.setTargetPosition(Util.RoundReal(goal));

        _timer.Terminate();
        // TODO(Peacock): Real value here
        _timer.SetDelay(15);

        if (_frontLeftMotor.getTargetPosition() < goal)
        {
            while (_frontLeftMotor.getTargetPosition() < goal)
            {
                telemetry.addData("Current: ", String.valueOf(_frontLeftMotor.getTargetPosition()));

                SetDriveSpeed(speed);
            }
        }

        if (_frontLeftMotor.getTargetPosition() > goal)
        {
            while (_frontLeftMotor.getTargetPosition() < goal)
            {
                telemetry.addData("Current: ", String.valueOf(_frontLeftMotor.getTargetPosition()));

                SetFrontLeftSpeed(-speed);
                SetFrontRightSpeed(speed);
                SetBackLeftSpeed(-speed);
                SetBackRightSpeed(speed);
            }
        }

        _timer.Terminate();
        try
        {
            _timer.join();
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }

        SetDriveSpeed(0);

        _frontLeftMotor.setMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
    }

    public void SetDriveSpeed(double speed)
    {
        SetFrontLeftSpeed(speed);
        SetFrontRightSpeed(speed);
        SetBackLeftSpeed(speed);
        SetBackRightSpeed(speed);
    }

    synchronized public void SetFrontLeftSpeed(double speed)
    {
        double spd = Util.Clampd(speed, -1.0, 1.0);

        _frontLeftMotor.setPower(spd);
    }

    synchronized public void SetFrontRightSpeed(double speed)
    {
        double spd = Util.Clampd(speed, -1.0, 1.0);

        _frontRightMotor.setPower(spd);
    }

    synchronized public void SetBackLeftSpeed(double speed)
    {
        double spd = Util.Clampd(speed, -1.0, 1.0);

        _backLeftMotor.setPower(spd);
    }

    synchronized public void SetBackRightSpeed(double speed)
    {
        double spd = Util.Clampd(speed, -1.0, 1.0);

        _backRightMotor.setPower(spd);
    }
}
