package com.rabbitminers.druidry.render.easing;

public class EasingHelper {
    public static float cubicEase(float t, float p0, float p1, float p2, float p3) {
        float c0 = p0 * (1 - t) * (1 - t) * (1 - t);
        float c1 = 3 * p1 * t * (1 - t) * (1 - t);
        float c2 = 3 * p2 * t * t * (1 - t);
        float c3 = p3 * t * t * t;
        return c0 + c1 + c2 + c3;
    }
}
