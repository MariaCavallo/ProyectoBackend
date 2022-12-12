package com.dh.ProyectoFinal.Service;

import com.dh.ProyectoFinal.DTO.TurnoDTO;
import com.dh.ProyectoFinal.Entity.Domicilio;
import com.dh.ProyectoFinal.Entity.Odontologo;
import com.dh.ProyectoFinal.Entity.Paciente;
import com.dh.ProyectoFinal.Entity.Turno;
import com.dh.ProyectoFinal.Exception.BadRequestException;
import com.dh.ProyectoFinal.Exception.ResourceNotFoundException;
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
    Odontologo odontologo = new Odontologo("ODO6666", "Jimmy","Neutron");

    @Test
    @Order(1)
    public void guardarTurnoTest() throws BadRequestException, ResourceNotFoundException {
        Odontologo odontologoAGuardar = odontologoService.guardarOdontologo(odontologo);
        Paciente pacienteAGuardar = pacienteService.guardarPaciente(paciente);
        TurnoDTO turnoAGuardar = new TurnoDTO();
        turnoAGuardar.setPacienteId(pacienteAGuardar.getId());
        turnoAGuardar.setOdontologoId(odontologoAGuardar.getId());
        turnoAGuardar.setFecha(LocalDate.of(2022,12,12));
        TurnoDTO turnoGuardado = turnoService.guardarTurno(turnoAGuardar);

        assertEquals(1L, turnoGuardado.getId());
        assertEquals(LocalDate.of(2022,12,12), turnoGuardado.getFecha());
    }

    @Test
    @Order(2)
    public void buscarTurnoPorIdTest() throws BadRequestException, ResourceNotFoundException {
        Long idABuscar=1L;
        Optional<TurnoDTO> turnoBuscado = turnoService.buscarTurno(idABuscar);

        assertNotNull(turnoBuscado);
    }

    @Test
    @Order(3)
    public void listarTurnosTest(){
        List<TurnoDTO> turnos = turnoService.listarTurnos();
        Integer cantEsperada = 1;
        assertEquals(cantEsperada, turnos.size());
    }

    @Test
    @Order(4)
    public void actualizarTurnoTest() throws BadRequestException, ResourceNotFoundException {
        Odontologo odontologoAActualizar = odontologoService.guardarOdontologo(odontologo);
        Paciente pacienteAActualizar = pacienteService.guardarPaciente(paciente);
        TurnoDTO turnoAActualizar = new TurnoDTO();
        LocalDate fecha = LocalDate.of(2022,11,30);

        turnoAActualizar.setId(1L);
        turnoAActualizar.setPacienteId(pacienteAActualizar.getId());
        turnoAActualizar.setOdontologoId(odontologoAActualizar.getId());
        turnoAActualizar.setFecha(fecha);
        turnoService.guardarTurno(turnoAActualizar);
        turnoService.actualizarTurno(turnoAActualizar);
        Optional<TurnoDTO> turnoActualizado = turnoService.buscarTurno(turnoAActualizar.getId());

        assertEquals(fecha, turnoActualizado.get().getFecha());
    }

    @Test
    @Order(5)
    public void eliminarTurnoTest() throws BadRequestException, ResourceNotFoundException {
        Long idAEliminar = 1L;
        turnoService.eliminarTurno(idAEliminar);
        Optional<TurnoDTO> turnoEliminado = turnoService.buscarTurno(idAEliminar);
        assertFalse(false);
    }
}
