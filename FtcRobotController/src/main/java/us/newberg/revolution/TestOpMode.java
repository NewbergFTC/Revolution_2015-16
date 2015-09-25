package us.newberg.revolution;

import com.qualcomm.ftccommon.DbgLog;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
<<<<<<< HEAD
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.LegacyModule;
=======
>>>>>>> sdk

/**
 * revolution-2015-16
 * Created by Garrison Peacock on 9/21/15.
 */
public class TestOpMode extends OpMode
{
<<<<<<< HEAD
    LegacyModule legacyModule;
    DcMotorController motorController;
=======
>>>>>>> sdk
    DcMotor motor;

    @Override
    public void init()
    {
        motor = hardwareMap.dcMotor.get("mot");

        DbgLog.msg("Init!");

        legacyModule = hardwareMap.legacyModule.get("leg");
        motorController = hardwareMap.dcMotorController.get("con");
        motor = hardwareMap.dcMotor.get("mot");
    }

    @Override
    public void loop()
    {
        motor.setPower(1);
<<<<<<< HEAD
=======

        DbgLog.msg("Loop!");
>>>>>>> sdk
    }
}
