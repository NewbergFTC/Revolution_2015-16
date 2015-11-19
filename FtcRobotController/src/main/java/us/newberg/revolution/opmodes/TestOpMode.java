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

        AutoDrive(0.25f, 50);
    }
}
