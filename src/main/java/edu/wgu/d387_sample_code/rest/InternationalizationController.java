package edu.wgu.d387_sample_code.rest;

import edu.wgu.d387_sample_code.service.Internationalization;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/")
public class InternationalizationController {

    // Send translated welcome messages to front-end via JSON ResponseEntity
    @RequestMapping(path = "/welcome", method = RequestMethod.GET)
    public ResponseEntity<List<String>> getWelcomeMessages() {  // Spring Boot > Jackson converts List<String> into JSON via ResponseEntity

        Internationalization internationalization = new Internationalization();  // Initialize class with the multi-threads

        // Define welcome messages as an ArrayList of Strings
        List<String> welcomeMessages = internationalization.displayWelcome();  // Run threads to return language translations

        // Return ResponseEntity with ArrayList of messages, ResponseEntity converts it into JSON
        return new ResponseEntity<>(welcomeMessages, HttpStatus.OK);
    }


    // Send date/time zone conversion to front-end via JSON ResponseEntity
    @RequestMapping(path = "/presentationTimes", method = RequestMethod.GET)
    public ResponseEntity<List<String>> getPresentationTimes() {  // Spring Boot > Jackson converts List<LocalDateTime> into JSON via ResponseEntity

        Internationalization internationalization = new Internationalization();  // Initialize class time conversions

        // Define presentation times as list of DateTimes (date/time)
        List<String> presentationTimes = internationalization.convertByTimeZone(); // Run threads to return time zone conversions

        // Return ResponseEntity with ArrayList of messages, ResponseEntity converts it into JSON
        return new ResponseEntity<>(presentationTimes, HttpStatus.OK);
    }

}
