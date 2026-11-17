package edu.pe.cibertec.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "asistente")
@Getter
@Setter
@NoArgsConstructor
public class Asistente extends Empleado {
    
}