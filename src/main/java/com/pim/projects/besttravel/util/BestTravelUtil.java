package com.pim.projects.besttravel.util;

import java.time.LocalDateTime;
import java.util.Random;

public class BestTravelUtil {

    private static final Random random = new Random();

    public static LocalDateTime getRandomSoonDate(){
        var randomHours = random.nextInt(5 - 2) + 2; //number between 2 and 5
        var now = LocalDateTime.now();

        return now.plusHours(randomHours);
    }

    public static LocalDateTime getRandomLaterDate(){
        var randomHours = random.nextInt(12 - 6) + 6; //number between 2 and 5
        var now = LocalDateTime.now();

        return now.plusHours(randomHours);
    }
}
