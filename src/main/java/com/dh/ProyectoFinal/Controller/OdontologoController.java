package com.dh.ProyectoFinal.Controller;

import com.dh.ProyectoFinal.Entity.Odontologo;
import com.dh.ProyectoFinal.Exception.BadRequestException;
import com.dh.ProyectoFinal.Exception.ResourceNotFoundException;
import com.dh.ProyectoFinal.Service.OdontologoService;
import com.dh.ProyectoFinal.Service.PacienteService;
import com.dh.ProyectoFinal.Service.TurnoService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

//rest o no rest
@RestController
@RequestMapping("/odontologos")
public class OdontologoController {

    private OdontologoService odontologoService;
    private PacienteService pacienteService;
    private TurnoService turnoService;

    private static final Logger LOGGER = Logger.getLogger(OdontologoController.class);

    @Autowired
    public OdontologoController(OdontologoService odontologoService, PacienteService pacienteService, TurnoService turnoService) {
        this.odontologoService = odontologoService;
        this.pacienteService = pacienteService;
        this.turnoService = turnoService;
    }

    @PostMapping
    public ResponseEntity<Odontologo> guardarOdontologo (@RequestBody Odontologo odontologo) {
        return ResponseEntity.ok(odontologoService.guardarOdontologo(odontologo));
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<Odontologo> buscarOdontologo (@PathVariable Long id) throws ResourceNotFoundException {
        Optional<Odontologo> odontologoBuscado = odontologoService.buscarOdontologo(id);
        if (odontologoBuscado.isPresent()){
            LOGGER.info("Se econtró el odontologo con id: " + id);
            return ResponseEntity.ok(odontologoBuscado.get());
        } else {
            LOGGER.error("No se encontó niongun odontologo con el id ingresado, intente nuevamente");
            return  ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/actualizar")
    public ResponseEntity<String> actualizarOdontologo (@RequestBody Odontologo odontologo) throws ResourceNotFoundException {
        Optional<Odontologo> odontologoBuscado = odontologoService.buscarOdontologo(odontologo.getId());
        if (odontologoBuscado.isPresent()) {
            odontologoService.actualizarOdontologo(odontologo);
            LOGGER.info("Se actualizó correctamente el odontologo con apellido: " + odontologo.getApellido());
            return ResponseEntity.ok().body("Se actualizo el odontologo con apellido: " + odontologo.getApellido());
        } else {
            LOGGER.error("El odontologo con id= " + odontologo.getId() +
                    " no existe en la Base de Datos, No se puede actualizar algo que no existe!");
            return ResponseEntity.badRequest().body("El odontologo con id= " + odontologo.getId() +
                    " no existe en la Base de Datos, No se puede actualizar algo que no existe!");
        }
    }

    @DeleteMapping("/borrar/{id}")
    public ResponseEntity<String> eliminarOdontologo (@PathVariable Long id) throws ResourceNotFoundException, BadRequestException {
        Optional<Odontologo> odontologoBuscado = odontologoService.buscarOdontologo(id);
        if (odontologoBuscado.isPresent()) {
            odontologoService.eliminarOdontologo(id);
            LOGGER.warn("Se elimino el odontologo de id: " + id);
            return ResponseEntity.ok().body("Se elimino el odontologo de id: " + id);
        } else {
            LOGGER.error("No se encuentra un odontologo con id= " +
                    id + " . Verificar el ingreso.");
            throw new ResourceNotFoundException("No se encuentra un odontologo con id= " +
                    id + " . Verificar el ingreso.");
        }
    }

    @GetMapping
    public ResponseEntity<List<Odontologo>> buscarTodosOdontologos () {
        LOGGER.info("Se listaron todos los odontologos con éxito");
        return ResponseEntity.ok(odontologoService.listarOdontologos());
    }
}
