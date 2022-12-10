package com.dh.ProyectoFinal.Service;

import com.dh.ProyectoFinal.Entity.Domicilio;
import com.dh.ProyectoFinal.Entity.Odontologo;
import com.dh.ProyectoFinal.Entity.Paciente;
import com.dh.ProyectoFinal.Entity.Turno;
import com.dh.ProyectoFinal.Exception.BadRequestException;
import com.dh.ProyectoFinal.Exception.ResourceNotFoundException;
import org.junit.Assert;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@RunWith(SpringRunner.class)
@SpringBootTest
public class TurnoServiceTest {

    @Autowired
    private TurnoService turnoService;
    @Autowired
    private PacienteService pacienteService;
    @Autowired
    private OdontologoService odontologoService;

    @Test
    @Order(1)
    public void cargarDataSet() throws BadRequestException {
        Domicilio domicilio = new Domicilio("Av Santa fe", 4444, "CABA", "Buenos Aires");
        Paciente paciente = pacienteService.guardarPaciente(new Paciente("Santiago", "Paz", "88888888", new LocalDate(2022, 12, 5), "santiagopaz@gmail.com", domicilio));
        this.odontologoService.guardarOdontologo(new Odontologo("3455647", "Santiago","Paz" ));

    }

    @Test
    @Order(2)
    public void altaTurnoTest() throws BadRequestException {

        this.cargarDataSet();
        turnoService.guardarTurno(new Turno(pacienteService.buscarPaciente(1L).get(),odontologoService.buscarOdontologoXId(1L).get(),new LocalDate(2022, 11,5)));
        Assert.assertNotNull(turnoService.buscarTurno(1L));
    }


    @Test
    @Order(3)
    public void buscarTurnoTest() throws BadRequestException {
        Assert.assertNotNull(turnoService.buscarTurno(1L));
    }


    @Test
    @Order(4)
    public void eliminarTurnoTest() throws ResourceNotFoundException, BadRequestException {
        turnoService.eliminarTurno(1L);
        assertThrows(BadRequestException.class, () -> turnoService.buscarTurno(1L), "No se encontró el turno.");
    }
}
