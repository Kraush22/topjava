package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;
import ru.javawebinar.topjava.util.SortByUserMeal;
import ru.javawebinar.topjava.util.TimeUtil;

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
        List<UserMealWithExceed> Index = new ArrayList<UserMealWithExceed>();
        Collections.sort(mealList, new SortByUserMeal());
        Map CalorControl = new HashMap<LocalDate, Integer>();
        for (int i=0;i<mealList.size();i++){
            if (!(CalorControl.containsKey(mealList.get(i).getDateTime().toLocalDate()))){
                CalorControl.put(mealList.get(i).getDateTime().toLocalDate(),mealList.get(i).getCalories());
            }
            else{
                CalorControl.put(mealList.get(i).getDateTime().toLocalDate(),CalorControl.get(mealList.get(i).getDateTime().toLocalDate()).intValue()+mealList.get(i).getCalories());
            }
        }
        return  null;
    }
}
