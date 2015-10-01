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
// TODO(Peacock): Should this extend LinearOpMode?
// That might make the whole timedDrive thing better, but we should test this first
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
        // TODO(Peacock): Should this be before or after we set the motor speed?
        // Call child loop
        Update();

        _frontLeftMotor.setPower(GetFrontLeftSpeed());
        _frontRightMotor.setPower(GetFrontRightSpeed());
        _backLeftMotor.setPower(GetBackLeftSpeed());
        _backRightMotor.setPower(GetBackRightSpeed());
    }

    final public void Drive(float leftPower, float rightPower)
    {
        float leftPwr = Util.Clampf(leftPower, -1.0f, 1.0f);
        float rightPwr = Util.Clampf(rightPower, -1.0f, 1.0f);

        SetFrontLeftSpeed(leftPwr);
        SetBackLeftSpeed(leftPower);
        SetFrontRightSpeed(rightPwr);
        SetBackRightSpeed(rightPwr);
    }

    // TODO(Peacock): Test this
    final public void TimedDrive(float leftPower, float rightPower, long millis)
    {
        Drive(leftPower, rightPower);
        new Thread(new DriveTimer(this, millis)).start();
        Drive(0, 0);
    }

    final public void SetFrontLeftSpeed(double speed)
    {
        double spd = Util.Clampd(speed, -1.0, 1.0);

        _frontLeftSpeed.set(spd);
    }

    final public void SetFrontRightSpeed(double speed)
    {
        double spd = Util.Clampd(speed, -1.0, 1.0);

        _frontRightSpeed.set(spd);
    }

    final public void SetBackLeftSpeed(double speed)
    {
        double spd = Util.Clampd(speed, -1.0, 1.0);

        _backLeftSpeed.set(spd);
    }

    final public void SetBackRightSpeed(double speed)
    {
        double spd = Util.Clampd(speed, -1.0, 1.0);

        _backRightSpeed.set(spd);
    }

    final public double GetFrontLeftSpeed()
    {
        return _frontLeftSpeed.get();
    }

    final public double GetFrontRightSpeed()
    {
        return _frontRightSpeed.get();
    }

    final public double GetBackLeftSpeed()
    {
        return _backLeftSpeed.get();
    }

    final public double GetBackRightSpeed()
    {
        return _backRightSpeed.get();
    }
}
