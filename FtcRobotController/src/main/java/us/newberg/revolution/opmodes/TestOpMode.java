package us.newberg.revolution.opmodes;

/**
 * Revolution 2015-2016
 * FTC team 9474
 */
public class TestOpMode extends RevOpMode
{
    @Override
    public void runOpMode() throws InterruptedException
    {
        super.Init();

        waitForStart();

        AutoDrive(0.8f, 24);
        sleep(1500);
        Turn(-30, 0.5f);
        sleep(1500);
        AutoDrive(0.8f, 53);
        sleep(1500);
        Turn(-15, 0.5f);
        sleep(1500);
        AutoDrive(1.0f, 8);
        sleep(1500);
    }
}
