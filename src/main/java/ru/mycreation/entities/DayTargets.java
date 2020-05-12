package ru.mycreation.entities;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

//@Data
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "day_targets")
public class DayTargets {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Size(min=2, max=100, message = "заполните поле")
    @Column
    private String title;

    @Column
    private int priority;

    @Column
    private String creation;

    @Pattern(regexp = "^[0-9]{2}:[0-9]{2}$",message = "заполните поле")
    @Column
    private String time;

    @ManyToOne
    @JoinColumn(name = "days_id")
    private Days days;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public void setUser(User user) {
        this.user = user;
    }
}
