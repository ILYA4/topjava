package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;

/**
 * GKislin
 * 31.05.2015.
 */
public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510)
        );

//        .toLocalDate();
//        .toLocalTime();
        for (UserMealWithExceed exceedMeal : getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(22,0), 2000)){
        System.out.println(exceedMeal.toString());
        }
    }

    private static List<UserMealWithExceed>  getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> dateSumCalories = new HashMap<>(); //create Map for saving caloriesPerParticularDay
        int calories = 0;
        for(UserMeal meal : mealList){                                                                          //Putting map
            LocalDate date = meal.getDateTime().toLocalDate();
            calories = dateSumCalories.containsKey(date) ? meal.getCalories()+calories : meal.getCalories();
            dateSumCalories.put(date, calories);
        }
        List<UserMealWithExceed> result = new ArrayList<>();
        for (UserMeal meal: mealList){                                  //adding items with exceeded
            if (TimeUtil.isBetween(meal.getDateTime().toLocalTime(),startTime, endTime)) {
            result.add(createUserMealExceeded(meal, dateSumCalories.get(meal.getDateTime().toLocalDate()) > caloriesPerDay));
            }
        }

        return result;
    }

    private static UserMealWithExceed createUserMealExceeded (UserMeal meal, boolean exceed){
        return new UserMealWithExceed(meal.getDateTime(),meal.getDescription(),meal.getCalories(), exceed);
    }
}
