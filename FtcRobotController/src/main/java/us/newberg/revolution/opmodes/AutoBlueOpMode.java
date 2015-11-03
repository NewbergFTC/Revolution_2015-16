package us.newberg.revolution.opmodes;

import us.newberg.revolution.DriveTimer;

/**
 * Revolution 2015-2016
 * FTC team 9474
 */
// TODO(Peacock): Plan at least two autonomous modes, one for each color team
public class AutoBlueOpMode extends RevOpMode
{
    private int _stage;
    private boolean _running;
    private boolean _runSuccess;

    @Override
    public void Initialize()
    {
        _timer = new DriveTimer(this, 0);

        _stage = 0;
        _running = false;
        _runSuccess = false;
    }

    @Override
    public void Update()
    {
        switch (_stage)
        {
            // Start of the round - We should drive some distance (5 ft?) forward
            case 0:
            {
                if (!_running)
                {
                    _timer.SetDelay(6000);  // Six seconds should be enough
                    AutoDrive(0.75f, 5);    // Drive forward at 75% power for five feet

                    _running = true;
                }
                else
                {
                    if (_runSuccess)         // Successful in whatever we did
                    {                        // We should move to the next stage
                        _timer.Terminate();
                        _running = false;
                        _runSuccess = false;
                        _stage = 1;
                    }
                }
            }
            break;

            // Second move - turn towards the shelter
            case 1:
            {
                if (!_running)
                {
                    _timer.SetDelay(2000);  // Two seconds should be enough time to turn
                    Turn(45, 0.5f);         // Turn some amount to the left (hopefully)
                                            // TODO(Peacock): A real turn value
                    _running = true;
                }
                else
                {
                    if (_runSuccess)        // Successful in whatever we did
                    {                       // We should move to the next stage
                        _timer.Terminate();
                        _running = false;
                        _runSuccess = false;
                        _stage = 2;
                    }
                }
            }
            break;
        }
    }
}
