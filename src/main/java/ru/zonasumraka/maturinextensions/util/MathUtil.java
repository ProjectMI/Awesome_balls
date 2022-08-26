package ru.zonasumraka.maturinextensions.util;

import java.math.BigDecimal;

public class MathUtil {
	private static final double POSITIVE_ZERO = 0d;
	
    public static double round(double x, int scale, int roundingMethod) {
        try {
            final double rounded = (new BigDecimal(Double.toString(x))
                   .setScale(scale, roundingMethod))
                   .doubleValue();
            // MATH-1089: negative values rounded to zero should result in negative zero
            return rounded == POSITIVE_ZERO ? POSITIVE_ZERO * x : rounded;
        } catch (NumberFormatException ex) {
            if (Double.isInfinite(x)) {
                return x;
            } else {
                return Double.NaN;
            }
        }
    }
}
