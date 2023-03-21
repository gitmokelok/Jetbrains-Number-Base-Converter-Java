
package converter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) {
        // write your code here

        try (Scanner scanner = new Scanner(System.in)){
            String userInput = "start";
            while(true){
                printWelcomeSentenceV2();
                userInput = scanner.nextLine();
                if ("/exit".equals(userInput)) {
                    break; //exit outer loop - exit the program
                }
                int sourceBase = Integer.parseInt(userInput.split(" ")[0]);
                int targetBase = Integer.parseInt(userInput.split(" ")[1]);

                while(true) {
                    System.out.print(String.format("Enter number in base %d to convert to base %d (To go back type /back) ", sourceBase, targetBase));
                    userInput = scanner.nextLine();

                    if ("/back".equals(userInput)) {
                        System.out.println();
                        break; //exit inner loop
                    }

                    String integerResult = "";
                    String fractionalResult = "";

                    String integerPart = userInput.split("\\.")[0];
                    String fractionalPart = userInput.contains(".") ? userInput.split("\\.")[1] : null;



                    if (fractionalPart != null) {
                        if (sourceBase == 10) {
                            fractionalResult = convertAnyFractionalToTargetBase(new BigDecimal("0." + fractionalPart), targetBase);
                        } else {
                            BigDecimal tempResult = convertAnyFractionalToDecimalBase("0." + fractionalPart, sourceBase);
                            fractionalResult = convertAnyFractionalToTargetBase(tempResult, targetBase);
                            //System.out.println("Conversion result: " + result);
                        }
                    }



                    if (!"0".equals(integerPart)) {
                        if (sourceBase == 10) {
                            integerResult = convertAnyToTargetBase(new BigInteger(integerPart), targetBase);
                        } else {
                            BigInteger tempResult = convertAnyToDecimalBase(integerPart, sourceBase);
                            integerResult = convertAnyToTargetBase(tempResult, targetBase);
                            //System.out.println("Conversion result: " + result);
                        }
                    } else {
                        integerResult = integerPart;
                    }

                    if (fractionalPart == null) {
                        System.out.println("Conversion result: " + integerResult);
                    } else {

                        System.out.println("Conversion result: " + fractionalResult.replace("0.", integerResult + "."));
                    }
                    System.out.println();
                }
            }
        }
    }

    public static String convertAnyToTargetBase(BigInteger targetNumber, int targetBase){

        StringBuilder sb = new StringBuilder();
        while (!targetNumber.equals(BigInteger.ZERO)){
            int remainder = targetNumber.remainder(BigInteger.valueOf(targetBase)).intValue();
            targetNumber = targetNumber.divide(BigInteger.valueOf(targetBase));
            if (targetBase >=10 && remainder > 9){
                sb.append((char)(remainder+55));
            }else {
                sb.append(remainder);
            }
        }

        return new String(sb.reverse());//.replaceAll("^0+", "");

    }

    public static String convertAnyFractionalToTargetBase(BigDecimal targetNumber, int targetBase){

        StringBuilder sb = new StringBuilder();
        while (sb.length() != 5) {
            targetNumber = targetNumber.multiply(BigDecimal.valueOf(targetBase));
            int integerValue = targetNumber.intValue();
            //targetNumber = targetNumber.divide(BigInteger.valueOf(targetBase));
            if(integerValue > 0) {
                targetNumber = targetNumber.subtract(BigDecimal.valueOf(integerValue));
            }
            if (targetBase >=10 && integerValue > 9){
                sb.append((char)(integerValue+55));
            }else {
                sb.append(integerValue);
            }
        }

        return "0." + sb;//.replaceAll("^0+", "");

    }

    public static String convertToTargetBase(int targetNumber, int targetBase){
        StringBuilder sb = new StringBuilder();
        while (targetNumber != 0){
            int remainder = targetNumber % targetBase;
            targetNumber = targetNumber / targetBase;
            if (targetBase >= 10 && remainder > 9){
                switch (remainder) {
                    case 10:
                        sb.append("A");
                        break;
                    case 11:
                        sb.append("B");
                        break;
                    case 12:
                        sb.append("C");
                        break;
                    case 13:
                        sb.append("D");
                        break;
                    case 14:
                        sb.append("E");
                        break;
                    case 15:
                        sb.append("F");
                        break;
                }
            }else {
                sb.append(remainder);
            }

        }



        return new String(sb.reverse());//.replaceAll("^0+", "");

    }

    public static BigInteger convertAnyToDecimalBase(String sourceNumber, int sourceBase){

        char[] sourceNumberChars = sourceNumber.toUpperCase().toCharArray();
        int numberOfChars = sourceNumberChars.length;
        BigInteger result = BigInteger.ZERO;


        for (int i =0; i < numberOfChars; i++) {
            int numericValueOfChar = Integer.MAX_VALUE;
            if (Character.isAlphabetic(sourceNumberChars[i])) {
                numericValueOfChar = (int)(sourceNumberChars[i])-55;
            } else {
                numericValueOfChar = Integer.parseInt(String.valueOf(sourceNumberChars[i]));
            }
            BigInteger temp = BigDecimal.valueOf(numericValueOfChar*Math.pow(sourceBase, numberOfChars-i-1)).toBigInteger();
            result = result.add(temp);
        }

        return result;
    }

    public static BigDecimal convertAnyFractionalToDecimalBase(String sourceNumber, int sourceBase){


        sourceNumber = sourceNumber.replace("0.", "");
        char[] sourceNumberChars = sourceNumber.toUpperCase().toCharArray();
        int numberOfChars = sourceNumberChars.length;
        BigDecimal result = BigDecimal.ZERO;
        result = result.setScale(5, RoundingMode.UP);


        for (int i = 0; i < numberOfChars; i++) {
            int numericValueOfChar;
            if (Character.isAlphabetic(sourceNumberChars[i])) {
                numericValueOfChar = (int)(sourceNumberChars[i])-55;
            } else {
                numericValueOfChar = Integer.parseInt(String.valueOf(sourceNumberChars[i]));
            }
            BigDecimal temp = BigDecimal.valueOf(numericValueOfChar*Math.pow(sourceBase, -1-i));
            result = result.add(temp);
        }

        return result;//.setScale(5, RoundingMode.FLOOR);
    }



    public static int convertToDecimalBase(String sourceNumber, int sourceBase){

        char[] sourceNumberChars = sourceNumber.toCharArray();
        int numberOfChars = sourceNumberChars.length;
        int result = 0;

        switch (sourceBase){
            case 2:
                for (int i =0; i < numberOfChars; i++) {
                    int numericValueOfChar = Integer.parseInt(String.valueOf(sourceNumberChars[i]));
                    result += numericValueOfChar*Math.pow(2.0, numberOfChars-i-1);
                }
                break;
            case 8:
                for (int i =0; i < numberOfChars; i++) {
                    int numericValueOfChar = Integer.parseInt(String.valueOf(sourceNumberChars[i]));
                    result += numericValueOfChar*Math.pow(8.0, numberOfChars-i-1);
                }
                break;
            case 16:
                for (int i =0; i < numberOfChars; i++) {
                    int numericValueOfChar = Integer.MAX_VALUE;
                    switch (sourceNumberChars[i]) {
                        case 'a','A':
                            numericValueOfChar = 10;
                            break;
                        case 'b','B':
                            numericValueOfChar = 11;
                            break;
                        case 'c','C':
                            numericValueOfChar = 12;
                            break;
                        case 'd','D':
                            numericValueOfChar = 13;
                            break;
                        case 'e','E':
                            numericValueOfChar = 14;
                            break;
                        case 'f','F':
                            numericValueOfChar = 15;
                            break;
                        case '0','1','2','3','4','5','6','7','8','9':
                            numericValueOfChar = Integer.parseInt(String.valueOf(sourceNumberChars[i]));
                    }
                    result += numericValueOfChar*Math.pow(16.0, numberOfChars-i-1);
                }
                break;
        }
        return result;
    }

    public static void printWelcomeSentenceV2(){
        System.out.print("Enter two numbers in format: {source base} {target base} (To quit type /exit) ");
    }

    public static void printWelcomeSentence(){
        System.out.print("Do you want to convert /from decimal or /to decimal? (To quit type /exit) ");
    }

    public static void printEnterDecimalNumberSentence(){
        System.out.print("Enter a number in decimal system: ");
    }

    public static void printEnterTargetBaseSentence(){
        System.out.print("Enter the target base: ");
    }

    public static void printConversionResultBaseSentence(String resultNumber){
        System.out.print(String.format(
                """
                Conversion result: %s
                
                """, resultNumber));
    }

    public static void printConversionResultToDecimalSentence(int resultNumber){
        System.out.print(String.format(
                """
                Conversion to decimal result: %d
                
                """, resultNumber));
    }

    public static void printEnterSourceNumberSentence(){
        System.out.print("Enter source number: ");
    }

    public static void printEnterSourceBaseSentence(){
        System.out.print("Enter source base: ");
    }
}
