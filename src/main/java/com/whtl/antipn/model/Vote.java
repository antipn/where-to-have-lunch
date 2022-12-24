package com.whtl.antipn.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;


@Entity
@Table(name = "votes")@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "global_seq")
    @SequenceGenerator(name = "global_seq", sequenceName = "hibernate_sequence_global", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "date")
    @NotBlank
    private LocalDate date;

    @JoinColumn(name = "user_id", nullable = false)
    @NotBlank
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @JoinColumn(name = "restaurant_id", nullable = false)
    @NotBlank
    @ManyToOne(fetch = FetchType.LAZY)
    private Restaurant restaurant;
}
