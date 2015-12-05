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

		while (opModeIsActive())
		{
			Drive(0.1f, 0.1f);

			waitForNextHardwareCycle();
		}
    }
}
