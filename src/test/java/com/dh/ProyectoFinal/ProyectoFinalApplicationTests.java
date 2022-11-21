package com.dh.ProyectoFinal;

import com.dh.ProyectoFinal.Entity.Odontologo;
import com.dh.ProyectoFinal.Entity.Paciente;
import com.dh.ProyectoFinal.Service.OdontologoService;
import com.dh.ProyectoFinal.Repository.BD;
import com.dh.ProyectoFinal.Service.PacienteService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;


@SpringBootTest
class ProyectoFinalApplicationTests {

	@Test
	void listarPacientes() {
		PacienteService pacienteService = new PacienteService();
		BD.crearTablas();
		List<Paciente> listaEncontrada = pacienteService.buscarTodosPacientes();
		Assertions.assertEquals(1,listaEncontrada.size());
	}

}
