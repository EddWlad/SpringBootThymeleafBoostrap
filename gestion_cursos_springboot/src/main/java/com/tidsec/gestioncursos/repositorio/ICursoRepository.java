package com.tidsec.gestioncursos.repositorio;

import com.tidsec.gestioncursos.entidades.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICursoRepository extends JpaRepository<Curso,Integer> // Hereda de la clase de spring
//que tiene todas las operaciones crud
{

}
