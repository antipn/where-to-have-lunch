package com.whtl.antipn.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "restaurants")
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "global_seq")
    @SequenceGenerator(name = "global_seq", sequenceName = "hibernate_sequence_global", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false)
    @Size(max = 128)
    @NotBlank
    private String name;

    @Column(name = "address", nullable = false)
    @NotBlank
    @Size(max = 128)
    private String address;

    @Column(name = "open", nullable = false)
    @NotBlank
    private boolean open = true;

}
