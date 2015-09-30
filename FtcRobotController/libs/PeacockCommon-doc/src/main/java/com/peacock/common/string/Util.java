package com.peacock.common.string;

import java.util.ArrayList;

/**
 * String utilities
 *
 * @author Garrison Peacock
 * @version 1.0
 */
public class Util
{
    /**
     * Removes empty strings from a string array
     *
     * @param data Array of strings
     * @return Clear array
     */
    public static String[] RemoveEmptyStrings(String[] data)
    {
        ArrayList<String> result = new ArrayList<>();

        for(int i = 0; i < data.length; i++)
            if(!data[i].equals(""))
                result.add(data[i]);

        String[] res = new String[result.size()];
        result.toArray(res);

        return res;
    }
}
