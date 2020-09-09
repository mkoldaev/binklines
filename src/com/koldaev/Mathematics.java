package com.koldaev;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Mathematics {

    private static BigDecimal openprice, closeprice, percprice, raznica;
    private static final BigDecimal ONE_HUNDRED = new BigDecimal(100);

    //прибавляется к начальной цене
    public static BigDecimal plusperc(BigDecimal begin, BigDecimal plus) {
        BigDecimal plusprice = begin.add(plus).setScale(2,BigDecimal.ROUND_HALF_UP);
        return plusprice;
    }
    //вычитается из начальной цены
    public static BigDecimal minusperc(BigDecimal begin, BigDecimal minus) {
        BigDecimal minusprice = begin.subtract(minus).setScale(2,BigDecimal.ROUND_HALF_UP);
        return minusprice;
    }
    //вычисляются процент от начальной цены
    public static BigDecimal getperc(BigDecimal price,double perc) {
        BigDecimal plusprice = price.multiply(BigDecimal.valueOf(perc/100)).setScale(2,BigDecimal.ROUND_HALF_UP);
        return plusprice;
    }
    //вычисляется разница с минимума цены до ее максимума в %
    private static BigDecimal getperc(String minprice, String maxprice) {
        openprice = new BigDecimal(minprice).setScale(2,BigDecimal.ROUND_HALF_UP);
        closeprice = new BigDecimal(maxprice).setScale(2,BigDecimal.ROUND_HALF_UP);
        raznica = closeprice.subtract(openprice);
        percprice = raznica.multiply(ONE_HUNDRED).divide(closeprice,2,RoundingMode.HALF_UP);
        return percprice;
    }

/*    public static void main(String[] args) {
        String openpricus = "10050.57";
        String closepricus = "12110.10";
        BigDecimal bigdecimalprice = new BigDecimal(openpricus).setScale(2,BigDecimal.ROUND_HALF_UP);
        String perc_amplitude = getperc(openpricus,closepricus).toPlainString();
        out.println("Амплитуда крайних значений цены "+openpricus+" и "+closepricus+": "+perc_amplitude+"%");
        double plusminus = 5.5;
        BigDecimal percfromprice = getperc(bigdecimalprice,plusminus);
        out.println("Плюс "+plusminus+"% к цене "+openpricus+": "+plusperc(bigdecimalprice,percfromprice));
        out.println("Минус "+plusminus+"% к цене "+openpricus+": "+minusperc(bigdecimalprice,percfromprice));
        Амплитуда крайних значений цены 10050.57 и 12110.10: 17.01%
        Плюс 5.5% к цене 10050.57: 10603.35
        Минус 5.5% к цене 10050.57: 9497.79
    }*/

}
