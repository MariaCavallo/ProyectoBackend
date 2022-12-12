package com.dh.ProyectoFinal.integration;

import com.dh.ProyectoFinal.DTO.TurnoDTO;
import com.dh.ProyectoFinal.Entity.Domicilio;
import com.dh.ProyectoFinal.Entity.Odontologo;
import com.dh.ProyectoFinal.Entity.Paciente;
import com.dh.ProyectoFinal.Exception.BadRequestException;
import com.dh.ProyectoFinal.Exception.ResourceNotFoundException;
import com.dh.ProyectoFinal.Service.OdontologoService;
import com.dh.ProyectoFinal.Service.PacienteService;
import com.dh.ProyectoFinal.Service.TurnoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class IntegracionTurnoTest {

        @Autowired
        private PacienteService pacienteService;
        @Autowired
        private OdontologoService odontologoService;
        @Autowired
        private TurnoService turnoService;
        @Autowired
        private MockMvc mockMvc;

        private void cargarTurnoInicial() throws BadRequestException, ResourceNotFoundException {
            Domicilio domicilio= new Domicilio("Calle a",234,"Localidad a","Provincia a");
            Paciente pacienteAGuardar= new Paciente("Maria","Cavallo","5451", LocalDate.of(2022,12,7),"prueba@gmail.com",domicilio);
            Paciente pacienteGuardado=pacienteService.guardarPaciente(pacienteAGuardar);
            Odontologo odontologoAGuardar=new Odontologo("MP4541","Leonel","Messi");
            Odontologo odontologoGuardado= odontologoService.guardarOdontologo(odontologoAGuardar);

            TurnoDTO turnoAGuardar= new TurnoDTO();
            turnoAGuardar.setFecha(LocalDate.of(2022,12, 7));
            turnoAGuardar.setPacienteId(pacienteGuardado.getId());
            turnoAGuardar.setOdontologoId(odontologoGuardado.getId());
            turnoService.guardarTurno(turnoAGuardar);
        }
        @Test
        public void listadoTurnoTest() throws Exception {
            cargarTurnoInicial();
            MvcResult respuesta = mockMvc.perform(MockMvcRequestBuilders.get("/turnos")
                    .accept(MediaType.APPLICATION_JSON))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
            assertFalse(respuesta.getResponse().getContentAsString().isEmpty());
        }
}
