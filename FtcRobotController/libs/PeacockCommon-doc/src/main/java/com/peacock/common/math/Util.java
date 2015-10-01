package com.peacock.common.math;

/**
 * Math utilities
 *
 * @author Garrison Peacock
 * @version 1.0
 */
public class Util
{
    /**
     * Clamps a value to a range
     *
     * @param a     The value to clamp
     * @param min   Min
     * @param max   Max
     * @return      Clamped value
     */
    public static float Clampf(float a, float min, float max)
    {
        return Math.max(min, Math.min(max, a));
    }

    /**
     * Clamps a value to a range
     *
     * @param a     The value to clamp
     * @param min   Min
     * @param max   Max
     * @return      Clamped value
     */
    public static double Clampd(double a, double min, double max)
    {
        return Math.max(min, Math.min(max, a));
    }

    /**
     * Round a float to an int with a bit more precision
     *
     * @param a The value to round
     * @return  The rounded value
     */
    public static int RoundReal(float a)
    {
        return (int)(a + 0.5f);
    }
}
