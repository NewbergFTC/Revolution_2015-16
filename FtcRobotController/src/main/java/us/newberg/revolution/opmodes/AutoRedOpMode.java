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

        while (opModeIsActive())
        {
            AutoDrive(0.5f, 24);
            break;
        }
    }

    @Override
    public void Initialize() {}

    @Override
    public void Update() {}
}
