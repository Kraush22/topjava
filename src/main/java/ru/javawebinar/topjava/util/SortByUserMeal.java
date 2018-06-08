package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;

import java.util.Comparator;

public class SortByUserMeal implements Comparator<UserMeal> {
    public int compare(UserMeal a, UserMeal b) {
        return a.getDateTime().compareTo(b.getDateTime());

    }
}
