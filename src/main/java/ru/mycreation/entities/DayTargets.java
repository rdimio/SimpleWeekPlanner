package ru.mycreation.entities;

import lombok.*;

import javax.persistence.*;
import java.sql.Time;

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

    @Column
    private String title;

    @Column
    private int priority;

    @Column
    private String creation;

    @Column
    private Time time;

    @ManyToOne
    @JoinColumn(name = "days_id")
    private Days days;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
