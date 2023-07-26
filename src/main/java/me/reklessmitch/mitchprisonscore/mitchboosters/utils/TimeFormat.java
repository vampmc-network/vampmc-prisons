package me.reklessmitch.mitchprisonscore.mitchboosters.utils;

public class TimeFormat {

    public static String formatSeconds(int seconds) {
        int hours = seconds / 3600;
        int minutes = (seconds % 3600) / 60;
        int remainingSeconds = seconds % 60;

        StringBuilder timeParts = new StringBuilder();
        if (hours > 0) {
            timeParts.append(hours).append("h ");
        }
        if (minutes > 0) {
            timeParts.append(minutes).append("m ");
        }
        if (remainingSeconds > 0) {
            timeParts.append(remainingSeconds).append("s");
        }

        return timeParts.toString().trim();
    }
}
