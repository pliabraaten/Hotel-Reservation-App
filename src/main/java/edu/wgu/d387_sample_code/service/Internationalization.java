package edu.wgu.d387_sample_code.service;

import org.springframework.core.io.ClassPathResource;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;

import static java.util.concurrent.Executors.newFixedThreadPool;

public class Internationalization {

    // Variables to reference Threads
    public List<String> welcomeMessages = new ArrayList<>();
    public static String fromThread1, fromThread2;

    // Variables to reference time zones
    public static List<String> convertedTimes = new ArrayList<>();
//    public static ZoneId zoneId = ZoneId.systemDefault();  // User's time zone
    public static ZoneId zEastern = ZoneId.of("America/New_York");
    public static ZoneId zMountain = ZoneId.of("America/Denver");
    public static ZoneId zUTC = ZoneId.of("UTC");

    // ExecutorService method is one way to multi-thread: used for Java mobile apps
    static ExecutorService messageExecutor = newFixedThreadPool(2);  // Creates 2 threads


    // LANGUAGES
    // Method for multilingual multithreaded welcome message
    public List<String> displayWelcome() {

        Properties properties = new Properties();

        messageExecutor.execute(() -> {  // Executes task in a thread
            try {
                InputStream stream = new ClassPathResource("welcome_en_US.properties").getInputStream();
                properties.load(stream);  // Loads key-value pairs from properties file named "welcome_en_US.properties"
                fromThread1 = properties.getProperty("welcome");
                welcomeMessages.add(fromThread1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        messageExecutor.execute(() -> {
            try {
                InputStream stream = new ClassPathResource("welcome_fr_CA.properties").getInputStream();
                properties.load(stream);
                fromThread2 = properties.getProperty("welcome");
                welcomeMessages.add(fromThread2);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // Delay thread to allow them to finish before returning
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return welcomeMessages;
    }


    // TIME ZONES
    // Method for calculating a set time for different time zones
    public List<String> convertByTimeZone() {

//        LocalDateTime localDateTime = LocalDateTime.now();
//        System.out.println("local time " + localDateTime.toString());

        // Clear list to prevent continual adding everytime the method is called
        convertedTimes.clear();

        ZonedDateTime presentationTime = ZonedDateTime.of(2025, 5, 12, 10, 30, 0, 0, ZoneId.systemDefault());

        // Use formatting object to put zonedDateTime into readable format for front-end
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        ZonedDateTime zonedDateTimeEastern = presentationTime.withZoneSameInstant(zEastern);
        LocalDateTime localDateTimeEastern = zonedDateTimeEastern.toLocalDateTime();
        convertedTimes.add(localDateTimeEastern.format(formatter));

        ZonedDateTime zonedDateTimeMountain = presentationTime.withZoneSameInstant(zMountain);
        LocalDateTime localDateTimeMountain = zonedDateTimeMountain.toLocalDateTime();
        convertedTimes.add(localDateTimeMountain.format(formatter));

        ZonedDateTime zonedDateTimeUTC = presentationTime.withZoneSameInstant(zUTC);
        LocalDateTime localDateTimeUTC = zonedDateTimeUTC.toLocalDateTime();
        convertedTimes.add(localDateTimeUTC.format(formatter));

        return convertedTimes;
    }

}
