package ru.mycreation.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Time;

@Data
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
    private char creation;

    @Column
    private Time time;

    @ManyToOne
    @JoinColumn(name = "days_id")
    private Days days;

}
