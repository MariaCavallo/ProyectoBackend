package com.dh.ProyectoFinal;

import com.dh.ProyectoFinal.Repository.BD;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProyectoFinalApplication {

	public static void main(String[] args) {
		BD.crearTablas();
		SpringApplication.run(ProyectoFinalApplication.class, args);
	}

}
