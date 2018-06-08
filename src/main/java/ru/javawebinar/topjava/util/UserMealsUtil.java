package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;
import ru.javawebinar.topjava.util.SortByUserMeal;
import ru.javawebinar.topjava.util.TimeUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
        List<UserMealWithExceed> tempIndex = new ArrayList<UserMealWithExceed>();
        List<UserMealWithExceed> Index = new ArrayList<UserMealWithExceed>();
        Collections.sort(mealList, new SortByUserMeal());
        int i = 0;
        while (i < mealList.size()) {
            LocalDate temp = mealList.get(i).getDateTime().toLocalDate();
            int k = 0;
            int flag = 0;
            int calor = 0;
            while (i + k < mealList.size() && temp.compareTo(mealList.get(i + k).getDateTime().toLocalDate()) == 0) {
                calor += mealList.get(i + k).getCalories();
                k++;
            }
            if (calor > caloriesPerDay) flag = 1;
            for (int j = 0; j < k; j++) {
                if (flag == 1) {
                    tempIndex.add(new UserMealWithExceed(mealList.get(i + j).getDateTime(), mealList.get(i + j).getDescription(), mealList.get(i + j).getCalories(), true));
                } else
                    tempIndex.add(new UserMealWithExceed(mealList.get(i + j).getDateTime(), mealList.get(i + j).getDescription(), mealList.get(i + j).getCalories(), false));
            }
            i += k;
        }
        for (UserMealWithExceed aTempIndex : tempIndex) {
            if ((aTempIndex.getDateTime().toLocalTime().compareTo(startTime) >= 0 && aTempIndex.getDateTime().toLocalTime().compareTo(endTime) <= 0)) {
                Index.add(aTempIndex);
            }
        }

        return Index;
    }
}
