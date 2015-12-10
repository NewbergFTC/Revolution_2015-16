package us.newberg.revolution.opmodes;

import android.media.MediaPlayer;

/**
 * Revolution 2015-2016
 * FTC team 9474
 */
public class AutoBlueOpMode extends RevOpMode
{
    @Override
    public void runOpMode() throws InterruptedException
    {
        super.Init();

        waitForStart();

		if (GetAppContext() != null)
			_player.start();

        AutoDrive(0.8f, 23f);
        sleep(1500);
        Turn(28, 0.6f);
        sleep(1500);
        AutoDrive(1.0f, 54);
        sleep(1500);
        Turn(15, 0.6f);
        sleep(1500);
        AutoDrive(1.0f, 30);
        sleep(1500);
       
       	// Do a little dance
       	// Sing a little song
       	// Get down tonight
       	DeployLeftServo();
       	sleep(500);
       	DeployRightServo();
       	sleep(500);
       	DeployLeftServo();
       	sleep(500);
       	DeployRightServo();
       	sleep(500);
       	DeployLeftServo();
       	sleep(500);
       	DeployRightServo();
       	sleep(500);
       	DeployLeftServo();
       	sleep(500);
       	DeployRightServo();
       	sleep(500);
       	DeployLeftServo();
       	sleep(500);
       	DeployRightServo();
       	sleep(500);
       	RaiseRightServo();
    }
}
