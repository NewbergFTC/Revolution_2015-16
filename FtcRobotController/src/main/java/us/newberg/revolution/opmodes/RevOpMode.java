package us.newberg.revolution.opmodes;

import com.google.common.util.concurrent.AtomicDouble;
import com.peacock.common.math.Util;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import java.util.Map;

import us.newberg.revolution.DriveTimer;

/**
 * Revolution 2015-2016
 * FTC team 9474
 */
public abstract class RevOpMode extends OpMode
{
    // TODO(Peacock): Setup encoders and find this stuff
    public static final float CLICKS_PER_REVOLUTION = 0;
    public static final float CLICKS_PER_FOOT = 0;

    // Drive motors
    private DcMotor _frontLeftMotor;
    private DcMotor _frontRightMotor;
    private DcMotor _backLeftMotor;
    private DcMotor _backRightMotor;

    // Atomic variables so we don't run into data races when doing timed autonomous stuff
    private AtomicDouble _frontLeftSpeed;
    private AtomicDouble _frontRightSpeed;
    private AtomicDouble _backLeftSpeed;
    private AtomicDouble _backRightSpeed;

    protected DriveTimer _timer;

    public RevOpMode()
    {
        super();

        _frontLeftSpeed = new AtomicDouble();
        _frontRightSpeed = new AtomicDouble();
        _backLeftSpeed = new AtomicDouble();
        _backRightSpeed = new AtomicDouble();

        _timer = null;
    }

    // Force subclasses to call these instead of calling init or loop
    public abstract void Initialize();
    public abstract void Update();

    @Override
    final public void init()
    {
        // TODO(Peacock): Figure out how to upload a robot config to the phone

        // Init the drive motors
        _frontLeftMotor = hardwareMap.dcMotor.get("frontLeft");
        _frontRightMotor = hardwareMap.dcMotor.get("frontRight");
        _backLeftMotor = hardwareMap.dcMotor.get("backLeft");
        _backRightMotor = hardwareMap.dcMotor.get("backRight");

        SetFrontLeftSpeed(0);
        SetBackLeftSpeed(0);
        SetFrontRightSpeed(0);
        SetBackRightSpeed(0);

        // Call subclass init
        Initialize();
    }

    @Override
    final public void loop()
    {
        // Call subclass loop
        Update();

        _frontLeftMotor.setPower(GetFrontLeftSpeed());
        _frontRightMotor.setPower(GetFrontRightSpeed());
        _backLeftMotor.setPower(GetBackLeftSpeed());
        _backRightMotor.setPower(GetBackRightSpeed());
    }

    public void Drive(float leftPower, float rightPower)
    {
        SetFrontLeftSpeed(-leftPower);
        SetBackLeftSpeed(-leftPower);
        SetFrontRightSpeed(rightPower);
        SetBackRightSpeed(rightPower);
    }

    public void AutoDrive(float power, float feet)
    {

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
    }

    final public void Turn(float degree, float speed)
    {

    }

    public void StopTimedDrive()
    {
        if (_timer != null)
            _timer.Terminate();
    }

    synchronized public void SetFrontLeftSpeed(double speed)
    {
        double spd = Util.Clampd(speed, -1.0, 1.0);

        _frontLeftSpeed.set(spd);
    }

    synchronized public void SetFrontRightSpeed(double speed)
    {
        double spd = Util.Clampd(speed, -1.0, 1.0);

        _frontRightSpeed.set(spd);
    }

    synchronized public void SetBackLeftSpeed(double speed)
    {
        double spd = Util.Clampd(speed, -1.0, 1.0);

        _backLeftSpeed.set(spd);
    }

    synchronized public void SetBackRightSpeed(double speed)
    {
        double spd = Util.Clampd(speed, -1.0, 1.0);

        _backRightSpeed.set(spd);
    }

    synchronized public double GetFrontLeftSpeed()
    {
        return _frontLeftSpeed.get();
    }

    synchronized public double GetFrontRightSpeed()
    {
        return _frontRightSpeed.get();
    }

    synchronized public double GetBackLeftSpeed()
    {
        return _backLeftSpeed.get();
    }

    synchronized public double GetBackRightSpeed()
    {
        return _backRightSpeed.get();
    }
}
