package com.rabbitminers.druidry.base.helpers;

import net.minecraft.util.Mth;

public class Colour {
    protected int value;

    public Colour(int r, int g, int b) {
        this(r, g, b, 255);
    }

    public Colour(int r, int g, int b, int a) {
        this.value = ((a & 255) << 24) | ((r & 255) << 16) | ((g & 255) << 8)  | ((b & 255));
    }

    public Colour(float r, float g, float b, float a) {
        this((int) (0.5 + 255 * Mth.clamp(r, 0, 1)), (int) (0.5 + 255 * Mth.clamp(g, 0, 1)), (int) (0.5 + 255 * Mth.clamp(b, 0, 1)), (int) (0.5 + 255 * Mth.clamp(a, 0, 1)));
    }

    public Colour(int value) {
        this.value = value;
    }

    public Colour copy() {
        return new Colour(value);
    }

    public int getValue() {
        return value;
    }

    public int getRed() {
        return (this.getValue() >> 16) & 0xff;
    }

    public int getGreen() {
        return (this.getValue() >> 8) & 0xff;
    }

    public int getBlue() {
        return (this.getValue()) & 0xff;
    }

    public int getAlpha() {
        return (this.getValue() >> 24) & 0xff;
    }

}
