package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.lang.Integer;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000)

        );
        List<UserMealWithExceed> result = getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
//        .toLocalDate();
//        .toLocalTime();

        for (UserMealWithExceed aResult : result) {
            System.out.println(aResult.getDateTime() + " " + aResult.getDescription() + " " + aResult.getCalories() + " " + aResult.getExceed());
        }

    }

    public static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        List<UserMealWithExceed> index = new ArrayList<>();
        Collections.sort(mealList, new SortByUserMeal());
        Map<LocalDate, Integer> calorControl = new HashMap<>();
        for (UserMeal aMealList : mealList) {
            if (!(calorControl.containsKey(aMealList.getDateTime().toLocalDate()))) {
                calorControl.put(aMealList.getDateTime().toLocalDate(), aMealList.getCalories());
            } else {
                calorControl.put(aMealList.getDateTime().toLocalDate(), calorControl.get(aMealList.getDateTime().toLocalDate()) + aMealList.getCalories());
            }
        }
        for (UserMeal aMealList : mealList) {
            if (aMealList.getDateTime().toLocalTime().compareTo(startTime) >= 0 && aMealList.getDateTime().toLocalTime().compareTo(endTime) <= 0) {
                if (calorControl.get(aMealList.getDateTime().toLocalDate()) > caloriesPerDay) {
                    index.add(new UserMealWithExceed(aMealList.getDateTime(), aMealList.getDescription(), aMealList.getCalories(), true));
                } else {
                    index.add(new UserMealWithExceed(aMealList.getDateTime(), aMealList.getDescription(), aMealList.getCalories(), false));
                }
            }
        }


        return index;
    }
}
