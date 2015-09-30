package com.peacock.common.video;

import java.util.Arrays;

/**
 * A nice and simple java style bitmap
 *
 * @author Garrison Peacock
 * @version 1.0
 */
public class Bitmap
{
    /** Width of the bitmap, in pixels */
    private final int width;
    /**  Height of the bitmap, in pixels */
    private final int height;
    /**  The components of the bitmap */
    private final byte[] components;

    /**
     *  Initializes a Bitmap
     *
     * @param height Height of the bitmap, in pixels
     * @param width  Width of the bitmap, in pixels
     */
    public Bitmap(int width, int height)
    {
        this.width = width;
        this.height = height;
        components = new byte[width * height * 4];
    }

    /**
     * Sets every pixel to a specific shade of grey
     *
     * @param shade Shade of grey
     */
    public void Clear(byte shade)
    {
        Arrays.fill(components, shade);
    }

    /**
     * Clear every pixel to a (a, b, g, r) value
     *
     * @param alpha alpha component
     * @param blue  blue component
     * @param green green component
     * @param red   red component
     */
    public void Clear(byte alpha, byte blue, byte green, byte red)
    {
        for (int i = 0; i < components.length; i += 4)
        {
            components[i    ] = alpha;
            components[i + 1] = blue;
            components[i + 2] = green;
            components[i + 3] = red;
        }
    }

    /**
     * Sets a pixel at (x,y) to the (alpha, blue, green, red) value
     *
     * @param x     X coord of the pixel
     * @param y     Y coord of the pixel
     * @param alpha Alpha value of the pixel color
     * @param blue  Blue value of the pixel color
     * @param green Green value of the pixel color
     * @param red   Red value of the pixel color
     */
    public void DrawPixel(int x, int y, byte alpha, byte blue, byte green, byte red)
    {
        int index = (x + y * width) * 4;

        // Dirty fix, when x and y are the same as the width and height of the bitmap
        // the index is out of bounds
        if (index > width * height * 4)
            index = (width * height * 4) - 4;

        components[index    ] = alpha;
        components[index + 1] = blue;
        components[index + 2] = green;
        components[index + 3] = red;
    }

    public int GetWidth()
    {
        return width;
    }

    public int GetHeight()
    {
        return height;
    }

    /**
     * Copies the components to a BGR byte array
     *
     * @param dest The destination
     */
    public void CopyToByteArray(byte[] dest)
    {
        for (int i = 0; i < width * height; i++)
        {
            dest[i * 3    ] = components[i * 4 + 1];
            dest[i * 3 + 1] = components[i * 4 + 2];
            dest[i * 3 + 2] = components[i * 4 + 3];
        }
    }
}