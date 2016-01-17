package us.newberg.revolution.opmodes;

public class Auto extends RevOpMode
{
    @Override public void runOpMode() throws InterruptedException
    {
        super.Init();

        waitForStart();

        AutoDrive(0.9f, 60.0f);
//        sleep(1500);
//        _player.start();

    }
}
