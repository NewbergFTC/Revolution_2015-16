package us.newberg.revolution.opmodes;

import com.qualcomm.robotcore.hardware.DcMotorController;

/**
 * Revolution 2015-2016
 * FTC team 9474
 */
public class TestOpMode extends RevOpMode
{
    @Override
    public void Initialize() { }

    @Override
    public void Update() { }

    @Override
    public void runOpMode() throws InterruptedException
    {
        super.Init();

        waitForStart();

        _frontLeftMotor.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        waitOneFullHardwareCycle();
        waitOneFullHardwareCycle();

        _frontLeftMotor.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
        waitOneFullHardwareCycle();
        waitOneFullHardwareCycle();

        _frontLeftMotor.setTargetPosition(2000);

        while (opModeIsActive())
        {
            _frontController.setMotorControllerDeviceMode(DcMotorController.DeviceMode.READ_ONLY);
            waitOneFullHardwareCycle();
            waitOneFullHardwareCycle();
            waitOneFullHardwareCycle();

            int ticks = _frontLeftMotor.getCurrentPosition();
            telemetry.addData("Ticks:", String.valueOf(ticks));

            _frontController.setMotorControllerDeviceMode(DcMotorController.DeviceMode.WRITE_ONLY);
            waitOneFullHardwareCycle();
            waitOneFullHardwareCycle();
            waitOneFullHardwareCycle();

            Drive(-0.05f, -0.05f);
        }
    }
}
