package com.dh.ProyectoFinal.Service;

import com.dh.ProyectoFinal.Entity.Domicilio;
import com.dh.ProyectoFinal.Entity.Paciente;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

@SpringBootTest
class PacienteServiceTest {
    @Autowired
    private PacienteService pacienteService;

    @Test
    @Order(1)
    public void guardarPacienteTest(){
        Paciente pacienteAGuardar = new Paciente("Maria", "Cavallo", "52577114", LocalDate.of(2022, 11, 28), "mariacavallom@gmail.com", new Domicilio("Dr Cesar Carlos Bianco", 2720, "Paso de la Arena", "Montevideo"));
        Paciente pacienteGuardado = pacienteService.guardarPaciente(pacienteAGuardar);
        assertEquals(1L, pacienteGuardado.getId());
    }

    @Test
    @Order(2)
    public void buscarPacientePorIdTest(){
        Long idABuscar=1L;
        Optional<Paciente> pacienteBuscado = pacienteService.buscarPaciente(idABuscar);
        assertNotNull(pacienteBuscado.get());
    }

    @Test
    @Order(3)
    public void buscarPacientesTest(){
        List<Paciente> pacientes = pacienteService.buscarTodosPacientes();
        //por cantidad de pacientes
        Integer cantEsperada = 1;
        assertEquals(cantEsperada, pacientes.size());
    }

    @Test
    @Order(4)
    public void actualizarPacienteTest(){
        Paciente pacienteAActualizar = new Paciente(1L,"Maria", "Cavallo", "52577114", LocalDate.of(2022, 11, 28), "mariacavallom@gmail.com", new Domicilio(1L,"Avenida Brasil", 2720, "Paso de la Arena", "Montevideo"));
        pacienteService.actualizarPaciente(pacienteAActualizar);
        Optional<Paciente> pacienteActualizado = pacienteService.buscarPaciente(pacienteAActualizar.getId());
        assertEquals("Avenida Brasil", pacienteActualizado.get().getDomicilio().getCalle());
    }

    @Test
    @Order(5)
    public void eliminarPacienteTest(){
        Long idAEliminar = 1L;
        pacienteService.eliminarPaciente(idAEliminar);
        Optional<Paciente> pacienteEliminado = pacienteService.buscarPaciente(idAEliminar);
        assertFalse(pacienteEliminado.isPresent());
    }
}