package com.peacock.common;

/**
 * A simple static time class
 *
 * @author Garrison Peacock
 * @version 1.0
 */
public class Time
{
    private static final long SECOND = 1000000000L;

    private static double delta;

    /**
     * Get the current time in seconds
     * <br>
     * Up to nanosecond accuracy
     * @return Current time
     */
    public static double getTime()
    {
        return (double)System.nanoTime() / (double)SECOND;
    }


}