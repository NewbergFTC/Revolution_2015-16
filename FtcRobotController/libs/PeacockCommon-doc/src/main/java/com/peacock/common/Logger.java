package com.peacock.common;

/**
 * A simple static logger
 *
 * @author Garrison Peacock
 * @version 1.0
 */
public class Logger
{
    /**
     * Log levels
     */
    public static enum Level
    {
        /**
         * Informational level
         */
        INFO,
        /**
         * Debug level
         */
        DEBUG,
        /**
         * Error level
         */
        ERROR,
        /**
         * Fatal level
         */
        FATAL,
        /**
         * Warning level
         */
        WARN
    }

    /**
     * Log a message with a certain level
     * <br>
     * Will call .toString() on the message object
     *
     * @param level Level of the log
     * @param message Message
     */
    public static void Log(Level level, Object message)
    {
        System.out.println("[" + level.toString() + "] " + message.toString());
    }

    /**
     * Logs a debug message
     *
     * @param message Message
     */
    public static void Debug(Object message)
    {
        Logger.Log(Level.DEBUG, message);
    }

    /**
     * Logs a informational message
     *
     * @param message Message
     */
    public static void Info(Object message)
    {
        Logger.Log(Level.INFO, message);
    }

    /**
     * Logs a error message
     *
     * @param message Message
     */
    public static void Error(Object message)
    {
        Logger.Log(Level.ERROR, message);
    }

    /**
     * Logs a warning message
     *
     * @param message Message
     */
    public static void Warn(Object message)
    {
        Logger.Log(Level.WARN, message);
    }

    /**
     * Logs a fatal message
     *
     * @param message Message
     */
    public static void Fatal(Object message)
    {
        Logger.Log(Level.FATAL, message);
    }
}