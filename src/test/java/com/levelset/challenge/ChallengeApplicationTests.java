package com.levelset.challenge;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ChallengeApplicationTests {

	@Test
	void contextLoads() {
		// //if zip isn't 5 numbers
		// String valid = "74635";
		// String invalid = "74g63f";

		// assertEquals(true, valid.matches("[0-9]+"));
		// assertEquals(false, invalid.matches("[0-9]+"));

		// //if start date isn't date format
		
		// String date = "2020-03-02";
        // // Matcher n  = Pattern.compile("\\d\\d\\d\\d-\\d\\d-\\d\\d").matcher(date);
        // // Matcher o = Pattern.compile("\\d\\d\\d\\d-\\d-\\d\\d").matcher(date);
        // // Matcher p  = Pattern.compile("\\d\\d\\d\\d-\\d-\\d\\d").matcher(date);
		// // Matcher q  = Pattern.compile("\\d\\d\\d\\d-\\d\\d-\\d").matcher(date);
		
		// assertEquals(true, date.matches("[0-9][0-9][0-9][0-9]-[0-9][0-9]-[0-9][0-9]"));
		// assertEquals(false, date.matches("[0-9][0-9][0-9][0-9]-[0-9]-[0-9][0-9]"));
		// assertEquals(false, date.matches("[0-9][0-9][0-9][0-9]-[0-9][0-9]-[0-9]"));
		// assertEquals(false, date.matches("[0-9][0-9][0-9][0-9]-[0-9]-[0-9]"));
	
		// assertEquals(false, (!date.matches("[0-9][0-9][0-9][0-9]-[0-9][0-9]-[0-9][0-9]")
		// && !date.matches("[0-9][0-9][0-9][0-9]-[0-9]-[0-9][0-9]")
		// && !date.matches("[0-9][0-9][0-9][0-9]-[0-9][0-9]-[0-9]")
		// && !date.matches("[0-9][0-9][0-9][0-9]-[0-9]-[0-9]")));
	   
		// String string= "ohiwrejkfsd";
		// assertEquals(true, string.contains("[a-zA-Z]"));
		
	}

}
