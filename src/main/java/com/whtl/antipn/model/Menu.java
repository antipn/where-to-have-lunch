package com.whtl.antipn.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.time.LocalDate;

//@Entity
//@Table(name="menus")
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "menus")
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "global_seq")
    @SequenceGenerator(name = "global_seq", sequenceName = "hibernate_sequence_global", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "date", nullable = false)
    @NotBlank
    private LocalDate date;

    @JoinColumn(name = "restaurant", nullable = false)
    @NotBlank
    @ManyToOne(fetch = FetchType.LAZY)
    private Restaurant restaurant;

    @Column(name = "name", nullable = false)
    @NotBlank
    @Size(min = 2, max = 128)
    private String name;

    @Column(name = "price", nullable = false)
    @NotBlank
    @PositiveOrZero
    private Double price;


}
