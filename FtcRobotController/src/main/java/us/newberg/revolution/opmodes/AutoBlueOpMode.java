package us.newberg.revolution.opmodes;

/**
 * Revolution 2015-2016
 * FTC team 9474
 */
// TODO(Peacock): Plan at least two autonomous modes, one for each color team
public class AutoBlueOpMode extends RevOpMode
{
    @Override
    public void Initialize()
    {

    }

    @Override
    public void Update()
    {
        // TODO(Peacock): Test this
        // It should move forward at 1/2 speed for 2 seconds
        TimedDrive(0.5f, 0.5f, 2000);
    }
}
