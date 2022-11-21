package com.dh.ProyectoFinal.Repository;

import com.dh.ProyectoFinal.Entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PacienteRepository extends JpaRepository<Paciente,Integer> {
}
