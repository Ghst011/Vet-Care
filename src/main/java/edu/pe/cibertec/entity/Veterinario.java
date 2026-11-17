package edu.pe.cibertec.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "veterinario")
@Getter
@Setter
@NoArgsConstructor
public class Veterinario extends Empleado {

    @Column(nullable = false, length = 60)
    private String especialidad;
}