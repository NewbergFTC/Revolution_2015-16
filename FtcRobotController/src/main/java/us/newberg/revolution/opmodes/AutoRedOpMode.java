package us.newberg.revolution.opmodes;

/**
 * Revolution 2015-2016
 * FTC team 9474
 */
// TODO(Peacock): Plan at least two autonomous modes, one for each color team
public class AutoRedOpMode extends RevOpMode
{
    @Override
    public void runOpMode() throws InterruptedException
    {
        super.Init();

        waitForStart();

        TimedDrive(-0.15f, -0.15f, 1000);
        waitOneFullHardwareCycle();
        TimedDrive(0.25f, -0.25f, 1000);
        waitOneFullHardwareCycle();
        TimedDrive(-0.15f, -0.15f, 3800);
        waitOneFullHardwareCycle();
        TimedDrive(-0.25f, 0.25f, 900);
        waitOneFullHardwareCycle();
        TimedDrive(-0.15f, -0.15f, 1800);
        waitOneFullHardwareCycle();
        TimedDrive(0.25f, -0.25f, 1000);
        waitOneFullHardwareCycle();
        TimedDrive(-1.0f, -1.0f, 1000);
        waitOneFullHardwareCycle();
        TimedDrive(0.0f, 0.0f, 5000);
        waitOneFullHardwareCycle();
        TimedDrive(0.5f, 0.5f, 1500);
        waitOneFullHardwareCycle();
    }

    @Override
    public void Initialize() {}

    @Override
    public void Update() {}
}
