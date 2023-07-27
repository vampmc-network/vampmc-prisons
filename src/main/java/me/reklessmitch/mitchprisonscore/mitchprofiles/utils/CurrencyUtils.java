package me.reklessmitch.mitchprisonscore.mitchprofiles.utils;

import org.bukkit.Bukkit;

public class CurrencyUtils {

    public static String format(int amount){
        return String.valueOf(amount);
        // @TODO: Format the amount
    }

    public static long parse(String amount){
        long amountInt;
        char letter;

        char lastChar = amount.charAt(amount.length() - 1);

        if (Character.isDigit(lastChar)) {
            try{
                amountInt = Integer.parseInt(amount);
            }catch (NumberFormatException e){
                return -1; // Cannot pay in doubles
            }
        } else {
            letter = amount.substring(amount.length() - 1).toLowerCase().charAt(0);
            double doubleAmount = Double.parseDouble(amount.substring(0, amount.length() - 1));
            Bukkit.broadcastMessage(String.valueOf(doubleAmount));
            amountInt = change(letter, doubleAmount);
        }

        return amountInt;
    }

    private static long change(char letter, double amountInt){
        double result;
        switch (letter) {
            case 'k' -> result = amountInt * 1000L;
            case 'm' -> result = amountInt * 1000000L;
            case 'b' -> result = amountInt * 1000000000L;
            case 't' -> result = amountInt * 1000000000000L;
            case 'q' -> result = amountInt * 1000000000000000L;
            default -> result = -1;
        }
        Bukkit.broadcastMessage(String.valueOf(result));
        return (long) result;
    }
}