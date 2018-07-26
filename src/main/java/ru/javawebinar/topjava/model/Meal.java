package ru.javawebinar.topjava.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
@NamedQueries(
        {
          @NamedQuery(name = Meal.ALL_SORT, query = "SELECT x FROM Meal x WHERE x.user.id=:userId  ORDER BY x.dateTime DESC ") ,
                @NamedQuery(name = Meal.DELETE, query = "DELETE FROM Meal x WHERE x.id=:id AND x.user.id=:userId") ,
                @NamedQuery(name = Meal.GET_BETWEEN, query = "SELECT x FROM Meal x WHERE x.user.id=:userId AND  x.dateTime BETWEEN :startDate AND :endDate ORDER BY x.dateTime DESC"),
                @NamedQuery(name = Meal.GET, query = "SELECT x FROM Meal x WHERE x.id=:id AND x.user.id=:userId")
        }
)
@Entity
@Table (name = "meals", uniqueConstraints ={@UniqueConstraint(columnNames = {"date_time"}, name = "meals_unique_user_datetime_idx")} )
public class Meal extends AbstractBaseEntity {
    public static final String ALL_SORT = "Meal.getAll";
    public static final String DELETE = "Meal.delete";
    public static final String GET_BETWEEN = "Meal.getBetween";
    public static final String GET = "Meal.get";

    @Column(name = "date_time", nullable = false)
    @NotNull
    private LocalDateTime dateTime;

    @Column(name = "description", nullable = false)
    @NotBlank
    private String description;

    @Column(name = "calories", nullable = false)
    private int calories;


    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    private User user;

    public Meal() {
    }

    public Meal(LocalDateTime dateTime, String description, int calories) {
        this(null, dateTime, description, calories);
    }

    public Meal(Integer id, LocalDateTime dateTime, String description, int calories) {
        super(id);
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    public LocalDate getDate() {
        return dateTime.toLocalDate();
    }

    public LocalTime getTime() {
        return dateTime.toLocalTime();
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Meal{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                '}';
    }
}
