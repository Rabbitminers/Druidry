package com.rabbitminers.druidry.base.helpers;

public class AngleHelper {
    public static float degreesFromDouble(double angle) {
        if (angle == 0)
            return (float) angle;
        return (float) (angle * 180 / Math.PI);
    }
}
