package com.dh.ProyectoFinal.Service;

import com.dh.ProyectoFinal.Entity.Odontologo;
import com.dh.ProyectoFinal.Exception.BadRequestException;
import com.dh.ProyectoFinal.Exception.ResourceNotFoundException;
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
        assertEquals("Leonel", odontologoGuardado.getNombre());
    }

    @Test
    @Order(2)
    public void buscarOdontologoPorIdTest() throws ResourceNotFoundException {
        Long idABuscar=1L;
        Optional<Odontologo> odontologoBuscado = odontologoService.buscarOdontologo(idABuscar);
        assertNotNull(odontologoBuscado.get());
    }

    @Test
    @Order(3)
    public void listarOdontologosTest(){
        List<Odontologo> odontologos = odontologoService.listarOdontologos();
        Integer cantEsperada = 1;
        assertEquals(cantEsperada, odontologos.size());
    }

    @Test
    @Order(4)
    public void actualizarOdontologoTest() throws ResourceNotFoundException {
        Odontologo odontologoAActualizar = new Odontologo("24597", "Leonel", "Messi");
        odontologoService.guardarOdontologo(odontologoAActualizar);
        odontologoService.actualizarOdontologo(odontologoAActualizar);
        Optional<Odontologo> odontologoActualizado = odontologoService.buscarOdontologo(odontologoAActualizar.getId());
        assertEquals("Messi", odontologoActualizado.get().getApellido());
    }

    @Test
    @Order(5)
    public void eliminarOdontologoTest() throws ResourceNotFoundException {
        Long idAEliminar = 1L;
        odontologoService.eliminarOdontologo(idAEliminar);
        ResourceNotFoundException rnte = assertThrows(
                ResourceNotFoundException.class,
                () -> odontologoService.buscarOdontologo(idAEliminar)
        );
        assertTrue(rnte.getMessage().contains("No se encontró ningún odontólogo con id=" + idAEliminar));
    }
}
