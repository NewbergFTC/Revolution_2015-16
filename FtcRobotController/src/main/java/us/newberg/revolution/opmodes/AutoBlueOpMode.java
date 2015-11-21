package us.newberg.revolution.opmodes;

/**
 * Revolution 2015-2016
 * FTC team 9474
 */
// TODO(Peacock): Update this to use encoders
public class AutoBlueOpMode extends RevOpMode
{
    @Override
    public void runOpMode() throws InterruptedException
    {
        super.Init();

        waitForStart();

        AutoDrive(0.72f, 23.5f);
        sleep(1500);
        Turn(32, 0.5f);
        sleep(1500);
        AutoDrive(0.72f, 53.5f);
        sleep(1500);
        Turn(32, 0.5f);
        sleep(1500);
        AutoDrive(1.0f, 38);
        sleep(1500);
    }
}
