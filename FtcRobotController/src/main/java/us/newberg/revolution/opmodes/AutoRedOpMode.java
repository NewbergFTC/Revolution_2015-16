package us.newberg.revolution.opmodes;

/**
 * Revolution 2015-2016
 * FTC team 9474
 */
// TODO(Peacock): Update this to use encoders
public class AutoRedOpMode extends RevOpMode
{
    @Override
    public void runOpMode() throws InterruptedException
    {
        super.Init();

        waitForStart();

        AutoDrive(0.72f, 23.2f);
        sleep(1500);
        Turn(-32, 0.45f);
        sleep(1500);
        AutoDrive(0.72f, 54f);
        sleep(1500);
        Turn(-32, 0.45f);
        sleep(1500);
        AutoDrive(1.0f, 38);
        sleep(1500);
    }
}
