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

        TimedDrive(1.0f, 1.0f, 500);
        waitOneFullHardwareCycle();
    }

    @Override
    public void Initialize() {}

    @Override
    public void Update() {}
}
