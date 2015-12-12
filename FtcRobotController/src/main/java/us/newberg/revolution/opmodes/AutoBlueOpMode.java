package us.newberg.revolution.opmodes;

import android.media.MediaPlayer;

/**
 * Revolution 2015-2016
 * FTC team 9474
 */
public class AutoBlueOpMode extends RevOpMode
{
  @Override public void runOpMode() throws InterruptedException
  {
    super.Init();

    waitForStart();

    if (GetAppContext() != null)
      _player.start();

    AutoDrive(0.9f, 20f);
    sleep(1000);
    Turn(31, 0.8f);
    sleep(1500);
    AutoDrive(1.0f, 61);
    sleep(1000);
    Turn(21, 0.8f);
    sleep(500);
    RaiseDoor();
    sleep(500);
    AutoDrive(1.0f, 53);
    sleep(1500);
    LowerDoor();
    sleep(1000);
    AutoDrive(1.0f, -10);
    sleep(500);
    RaiseDoor();
    sleep(500);
    AutoDrive(1.0f, 12);
    sleep(1000);

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
