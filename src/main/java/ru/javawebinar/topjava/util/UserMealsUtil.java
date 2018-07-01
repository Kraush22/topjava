package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.lang.Integer;
import java.util.*;
import java.util.stream.Collectors;

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
        List<UserMealWithExceed> result = getFilteredWithExceededStreams(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
//        .toLocalDate();
//        .toLocalTime();

        for (UserMealWithExceed aResult : result) {
            System.out.println(aResult.getDateTime() + " " + aResult.getDescription() + " " + aResult.getCalories() + " " + aResult.getExceed());
        }

    }

    public static List<UserMealWithExceed> getFilteredWithExceededStreams(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Collections.sort(mealList, new SortByUserMeal());
        Map<LocalDate, Integer> calorControl = mealList.stream().collect(Collectors.groupingBy(userMeal -> convert(userMeal.getDateTime()), Collectors.summingInt(UserMeal::getCalories)));
        List<UserMealWithExceed> index = new ArrayList<>();
        mealList.stream()
                .filter(x -> (x.getDateTime().toLocalTime().compareTo(startTime) >= 0 && x.getDateTime().toLocalTime().compareTo(endTime) <= 0))
                .forEach(
                        x -> {
                            if (calorControl.get(x.getDateTime().toLocalDate()) > caloriesPerDay) {
                                index.add(new UserMealWithExceed(x.getDateTime(), x.getDescription(), x.getCalories(), true));
                            } else {
                                index.add(new UserMealWithExceed(x.getDateTime(), x.getDescription(), x.getCalories(), false));
                            }
                        }
                );

        return index;
    }

    public static LocalDate convert(LocalDateTime ldt) {
        int year = ldt.getYear();
        int month = ldt.getMonthValue();
        int day = ldt.getDayOfMonth();
        return LocalDate.of(year, month, day);
    }


    public static List<UserMealWithExceed> getFilteredWithExceededCycles(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        List<UserMealWithExceed> index = new ArrayList<>();
        Collections.sort(mealList, new SortByUserMeal());
        Map<LocalDate, Integer> calorControl = new HashMap<>();

        for (UserMeal userMeal : mealList) {
            if (!(calorControl.containsKey(userMeal.getDateTime().toLocalDate()))) {
                calorControl.put(userMeal.getDateTime().toLocalDate(), userMeal.getCalories());
            } else {
                calorControl.put(userMeal.getDateTime().toLocalDate(), calorControl.get(userMeal.getDateTime().toLocalDate()) + userMeal.getCalories());
            }
        }
        for (UserMeal userMeal : mealList) {
            if (userMeal.getDateTime().toLocalTime().compareTo(startTime) >= 0 && userMeal.getDateTime().toLocalTime().compareTo(endTime) <= 0) {
                if (calorControl.get(userMeal.getDateTime().toLocalDate()) > caloriesPerDay) {
                    index.add(new UserMealWithExceed(userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories(), true));
                } else {
                    index.add(new UserMealWithExceed(userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories(), false));
                }
            }
        }
        return index;
    }

}

