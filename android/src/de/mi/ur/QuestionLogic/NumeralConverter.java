package de.mi.ur.QuestionLogic;

import java.util.Random;

import de.mi.ur.Constants;

/**
 * Created by Anna-Marie on 06.09.2016.
 */
public class NumeralConverter {
    private static Random randomGen = new Random();

    public NumeralConverter(){}

    /*
     * Int number is converted to a String as a representation of another numeral system
     */
    public static String convertToNumeral(int number, int radix){
        if (radix < Constants.MIN_NUMERAL_BASE || radix > Constants.MAX_NUMERAL_BASE) {
            radix = Constants.DEFAULT_NUMERAL_BASE;
        }
        String toReturn = Integer.toString(number, radix);
        return toReturn.toUpperCase();
    }

    /*
    * String representation of a number in a numeralsystem with base radix is converted to a decimal int
    */
    public static int convertFromNumeral(String number, int radix){
        return Integer.parseInt(number, radix);
    }

    /*
     * String representation of a number is converted from one numeralSystem (base radixInput base) to another (base radixOutput)
     */
    public static String convertFromNumeralToNumeral(String number, int radixInput, int radixOutput){
        int num = convertFromNumeral(number, radixInput);
        return convertToNumeral(num, radixOutput);
    }

    /*
     * Generates a number in the numeral system with base numeralBase and maxDigits digits
     */
    public static String generateNumWithMaxDigits(int numeralBase, int maxDigits){
        String number = "";
        for(int i = 0; i<maxDigits; i++){
            int num = randomGen.nextInt(numeralBase);
            number += convertToNumeral(num, numeralBase);
        }
        return number;
    }

    /*
     * Generates a number in the destinationNumeralBase numeral system, which is below the threshold maxDecimal (in decimal numeral system)
     */
    public static String generateNumBelowMax(int destinationNumeralBase, int maxDecimal){
        int num = randomGen.nextInt(maxDecimal);
        return convertToNumeral(num, destinationNumeralBase);
    }
}
