package us.newberg.revolution.opmodes;

/**
 * Revolution 2015-2016
 * FTC team 9474
 */
public class TestOpMode extends RevOpMode
{
    private boolean _first;

    @Override
    public void Initialize()
    {
        _first = true;
    }

    @Override
    public void Update()
    {
        if (_first)
        {
            // Drive forward at full speed for 10 seconds
            TimedDrive(1.0f, 1.0f, 10000);

            _first = false;
        }
    }
}
