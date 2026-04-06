package com.portfolio.landonhotel.rest;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicReference;

@RestController
@CrossOrigin
public class MessageResource {

    private static final ZoneId ZONE_ET = ZoneId.of("America/New_York");
    private static final ZoneId ZONE_MT = ZoneId.of("America/Denver");
    private static final ZoneId ZONE_UTC = ZoneId.of("UTC");

    @GetMapping("/api/messages")
    public MessageResponse getMessages() throws InterruptedException {

        AtomicReference<String> welcomeEn = new AtomicReference<>("");
        AtomicReference<String> welcomeFr = new AtomicReference<>("");

        Thread englishThread = new Thread(() -> {
            ResourceBundle bundle = ResourceBundle.getBundle("messages", Locale.ENGLISH);
            welcomeEn.set(bundle.getString("welcome.message"));
        });

        Thread frenchThread = new Thread(() -> {
            ResourceBundle bundle = ResourceBundle.getBundle("messages", Locale.FRENCH);
            welcomeFr.set(bundle.getString("welcome.message"));
        });

        englishThread.start();
        frenchThread.start();

        // Ensure both threads finish before we return the response
        englishThread.join();
        frenchThread.join();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        ZonedDateTime presentationET =
                ZonedDateTime.now(ZONE_ET)
                        .with(LocalTime.of(10, 0))
                        .withSecond(0)
                        .withNano(0);

        ZonedDateTime presentationMT = presentationET.withZoneSameInstant(ZONE_MT);
        ZonedDateTime presentationUTC = presentationET.withZoneSameInstant(ZONE_UTC);

        return new MessageResponse(
                welcomeEn.get(),
                welcomeFr.get(),
                presentationET.format(formatter),
                presentationMT.format(formatter),
                presentationUTC.format(formatter)
        );
    }

    public record MessageResponse(
            String welcomeEnglish,
            String welcomeFrench,
            String timeET,
            String timeMT,
            String timeUTC
    ) {}
}

