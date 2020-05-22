package ru.mycreation.entities;

import lombok.*;

import javax.persistence.*;
import java.util.List;

//@Data
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "days")
public class Days {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private String title;

    @OneToMany(mappedBy = "days")
    private List<DayTargets> dayTargets;

}
