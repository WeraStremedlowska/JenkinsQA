package api;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class DateParse {
    public static void main(String[] args) {
        long timestamp = 1714220552991L;
        String formattedTime = convertTimestamp(timestamp);
        String formattedDuration = convertTimestampToDuration(timestamp);
        System.out.println(formattedTime + " Last Success " + formattedDuration);
    }

    public static String convertTimestamp(long timestamp) {
        Instant instant = Instant.ofEpochMilli(timestamp);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")
                .withZone(ZoneId.systemDefault());
        return formatter.format(instant);
    }

    public static String convertTimestampToDuration(long timestamp) {
        Instant then = Instant.ofEpochMilli(timestamp);
        Instant now = Instant.now();
        Duration duration = Duration.between(then, now);
        long hours = duration.toHours();
        long minutes = duration.toMinutes() % 60;
        return hours + " hr " + minutes + " min";
    }
}
