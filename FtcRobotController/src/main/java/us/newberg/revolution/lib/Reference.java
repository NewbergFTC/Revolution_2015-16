package us.newberg.revolution.lib;

/**
 * Revolution 2015-2016
 * FTC team 9474
 */
public class Reference
{
  // Wheel
  public static final float ENCODER_TICKS_PER_REVOLUTION = 1111.0f;
  public static final float WHEEL_DIAMETER = 3.85f;
  public static final float WHEEL_CIRCUMFERENCE =
      WHEEL_DIAMETER * (float)Math.PI;

  // Servos
  public static final float LEFT_SERVO_DEPLOYED = 0.45f;
  public static final float LEFT_SERVO_UP = 0.0f;
  public static final float RIGHT_SERVO_DEPLOYED = 0.0f;
  public static final float RIGHT_SERVO_UP = 0.45f;
}
