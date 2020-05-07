package ru.mycreation.entities;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;
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

    @Size(min=2, max=100)
    @Column
    private String title;

    @Column
    private int priority;

    @Column
    private char creation;

    @NotNull
    @Column
    private Time time;

    @ManyToOne
    @JoinColumn(name = "days_id")
    private Days days;

}
