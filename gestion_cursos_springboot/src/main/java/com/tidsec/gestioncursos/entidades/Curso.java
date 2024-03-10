package com.tidsec.gestioncursos.entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "cursos")
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class Curso
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //hace que la columna sea identity
    private Integer id;
    @Column(length = 128,nullable = false) // @Column  es para dar el largo de
    // caracteres de la columna y que no permite que sea null
    private String titulo;
    @Column(length = 256)
    private String descripcion;
    @Column(nullable = false)
    private int nivel;
    @Column(name = "estado_publicado") // cambia el nombre de la columna al crearla
    private boolean isPublicado;
}
