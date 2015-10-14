package us.newberg.revolution.opmodes;

import com.google.common.util.concurrent.AtomicDouble;
import com.peacock.common.math.Util;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import us.newberg.revolution.DriveTimer;

/**
 * Revolution 2015-2016
 * FTC team 9474
 */
public abstract class RevOpMode extends OpMode
{
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

    public RevOpMode()
    {
        super();

        _frontLeftSpeed = new AtomicDouble();
        _frontRightSpeed = new AtomicDouble();
        _backLeftSpeed = new AtomicDouble();
        _backRightSpeed = new AtomicDouble();
    }

    // Force children to call these instead of calling super
    public abstract void Initialize();
    public abstract void Update();

    @Override
    final public void init()
    {
        // Init the drive motors
        // TODO(Peacock): Figure out how to upload a robot config to the phone
        _frontLeftMotor = hardwareMap.dcMotor.get("frontLeft");
        _frontRightMotor = hardwareMap.dcMotor.get("frontRight");
        _backLeftMotor = hardwareMap.dcMotor.get("backLeft");
        _backRightMotor = hardwareMap.dcMotor.get("backRight");

        SetFrontLeftSpeed(0);
        SetBackLeftSpeed(0);
        SetFrontRightSpeed(0);
        SetBackRightSpeed(0);

        // Call child init
        Initialize();
    }

    @Override
    final public void loop()
    {
        // Should this be before or after we set the motor speed?
        // Call child loop
        Update();

        _frontLeftMotor.setPower(GetFrontLeftSpeed());
        _frontRightMotor.setPower(GetFrontRightSpeed());
        _backLeftMotor.setPower(GetBackLeftSpeed());
        _backRightMotor.setPower(GetBackRightSpeed());
    }

    final public void Drive(float leftPower, float rightPower)
    {
        SetFrontLeftSpeed(-leftPower);
        SetBackLeftSpeed(-leftPower);
        SetFrontRightSpeed(rightPower);
        SetBackRightSpeed(rightPower);
    }

    final public void TimedDrive(float leftPower, float rightPower, long millis)
    {
        Drive(leftPower, rightPower);

        new Thread(new DriveTimer(this, millis)).start();
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
