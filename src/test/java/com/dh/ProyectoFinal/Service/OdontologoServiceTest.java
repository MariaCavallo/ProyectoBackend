package com.dh.ProyectoFinal.Service;

import com.dh.ProyectoFinal.Entity.Odontologo;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
class OdontologoServiceTest {

    @Autowired
    private OdontologoService odontologoService;

    @Test
    @Order(1)
    public void guardarOdontologoTest(){
        Odontologo odontologoAGuardar = new Odontologo("24597", "Leonel", "Messi");
        Odontologo odontologoGuardado = odontologoService.guardarOdontologo(odontologoAGuardar);
        assertEquals(1L, odontologoGuardado.getId());
    }

    @Test
    @Order(2)
    public void buscarOdontologoPorIdTest(){
        Long idABuscar=1L;
        Optional<Odontologo> odontologoBuscado = odontologoService.buscarOdontologoXId(idABuscar);
        assertNotNull(odontologoBuscado.get());
    }

    @Test
    @Order(3)
    public void buscarOdontologosTest(){
        List<Odontologo> odontologos = odontologoService.listarOdontologos();
        //por cantidad de pacientes
        Integer cantEsperada = 1;
        assertEquals(cantEsperada, odontologos.size());
    }

    @Test
    @Order(4)
    public void actualizarOdontologoTest(){
        Odontologo odontologoAActualizar = new Odontologo("24597", "Leonel", "Messi");
        odontologoService.actualizarOdontologo(odontologoAActualizar);
        Optional<Odontologo> odontologoActualizado = odontologoService.buscarOdontologoXId(odontologoAActualizar.getId());
        assertEquals("Messi", odontologoActualizado.get().getMatricula());
    }

    @Test
    @Order(5)
    public void eliminarOdontologoTest(){
        Long idAEliminar = 1L;
        odontologoService.eliminarOdontologo(idAEliminar);
        Optional<Odontologo> odontologoEliminado = odontologoService.buscarOdontologoXId(idAEliminar);
        assertFalse(odontologoEliminado.isPresent());
    }
}
