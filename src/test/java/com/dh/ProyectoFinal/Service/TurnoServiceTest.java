package com.dh.ProyectoFinal.Service;

import com.dh.ProyectoFinal.DTO.TurnoDTO;
import com.dh.ProyectoFinal.Entity.Domicilio;
import com.dh.ProyectoFinal.Entity.Odontologo;
import com.dh.ProyectoFinal.Entity.Paciente;
import com.dh.ProyectoFinal.Entity.Turno;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

@SpringBootTest
public class TurnoServiceTest {

    @Autowired
    private TurnoService turnoService;
    @Autowired
    private PacienteService pacienteService;
    @Autowired
    private OdontologoService odontologoService;

    Paciente paciente = new Paciente("Marta", "Gonzalez", "45645645", LocalDate.of(2022,12,10), "martita@gmail.com", new Domicilio());
    Domicilio domicilio = new Domicilio("Av Bulevar", 6556, "Prado", "Montevideo");
    Odontologo odontologo = new Odontologo("ODO6666", "Jimmy","Neutron");

    @Test
    @Order(1)
    public void guardarTurnoTest(){
        TurnoDTO turnoAGuardar = new TurnoDTO();
        TurnoDTO turnoGuardado = turnoService.guardarTurno(turnoAGuardar);
        assertEquals(1L, turnoGuardado.getId());
    }

    @Test
    @Order(2)
    public void buscarTurnoPorIdTest(){
        Long idABuscar=1L;
        Optional<TurnoDTO> turnoBuscado = turnoService.buscarTurno(idABuscar);
        assertNotNull(turnoBuscado.get());
    }

    @Test
    @Order(3)
    public void buscarTurnosTest(){
        List<TurnoDTO> turnos = turnoService.buscarTodosTurno();
        //por cantidad de pacientes
        Integer cantEsperada = 1;
        assertEquals(cantEsperada, turnos.size());
    }

    @Test
    @Order(4)
    public void actualizarTurnoTest(){
        TurnoDTO turnoAActualizar = new TurnoDTO(LocalDate.of(2022,12,10), paciente.getId(), odontologo.getId());
        turnoService.actualizarTurno(turnoAActualizar);
        Optional<TurnoDTO> turnoActualizado;
        turnoActualizado = turnoService.buscarTurno(turnoAActualizar.getId());
        assertEquals(1, turnoActualizado.get());
    }

    @Test
    @Order(5)
    public void eliminarTurnoTest(){
        Long idAEliminar = 1L;
        turnoService.eliminarTurno(idAEliminar);
        Optional<TurnoDTO> turnoEliminado = turnoService.buscarTurno(idAEliminar);
        assertEquals(true, turnoEliminado.isEmpty());
    }
}
