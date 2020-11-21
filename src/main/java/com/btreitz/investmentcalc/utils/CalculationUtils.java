package com.btreitz.investmentcalc.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CalculationUtils {

    public static double trimDoubleToDecimalPrecision(int maxDecimalPrecision, double value) {
        if ((BigDecimal.valueOf(value).scale() > maxDecimalPrecision)) {
            return BigDecimal.valueOf(value)
                    .setScale(maxDecimalPrecision, RoundingMode.HALF_UP)
                    .doubleValue();
        }
        return value;
    }
}
