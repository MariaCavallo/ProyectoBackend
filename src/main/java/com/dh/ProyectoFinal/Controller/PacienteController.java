package com.dh.ProyectoFinal.Controller;

import com.dh.ProyectoFinal.Entity.Paciente;
import com.dh.ProyectoFinal.Exception.ResourceNotFoundException;
import com.dh.ProyectoFinal.Service.PacienteService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {


    private PacienteService pacienteService;

    private static final Logger LOGGER = Logger.getLogger(PacienteController.class);

    @Autowired
    public PacienteController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    @PostMapping
    public ResponseEntity<Paciente> guardarPaciente (@RequestBody Paciente paciente) {
        LOGGER.info("Se agregó correctamente el paciente " + paciente.getNombre() + paciente.getApellido());
        return ResponseEntity.ok(pacienteService.guardarPaciente(paciente));
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<Paciente> buscarPacienteXid (@PathVariable("id") Long id) throws ResourceNotFoundException {
        Optional<Paciente> pacienteBuscado = pacienteService.buscarPaciente(id);
        if (pacienteBuscado.isPresent()) {
            LOGGER.info("Se econtró el paciente con id: " + id);
            return ResponseEntity.ok(pacienteBuscado.get());
        } else {
            LOGGER.error("No se encontró ningun paciente con ese id, verificque los datos ingresados");
            return ResponseEntity.badRequest().build();
        }
    }
    @GetMapping("/buscar/mail")
    public ResponseEntity<Optional<Paciente>> buscarPacienteXemail (@RequestParam("email") String string) throws ResourceNotFoundException {
        LOGGER.info("Se econtró el paciente con el mail: " + string);
        return ResponseEntity.ok(pacienteService.buscarPorEmail(string));
    }

    @PutMapping("/actualizar")
    public ResponseEntity<String> actualizarPaciente (@RequestBody Paciente paciente) throws ResourceNotFoundException {
        Optional<Paciente> pacienteBuscado = pacienteService.buscarPaciente(paciente.getId());
        if (pacienteBuscado.isPresent()) {
            pacienteService.actualizarPaciente(paciente);
            LOGGER.info("Se actualizó el paciente de apellido: " + paciente.getApellido());
            return ResponseEntity.ok().body("Se actualizo el paciente de apellido: " + paciente.getApellido());
        } else {
            LOGGER.error("El paciente con id= "+
                    paciente.getId() + " no existe en la Base de Datos." +
                    "No puede actualizar algo que no existe :(");
            return ResponseEntity.badRequest().body("El paciente con id= "+
                    paciente.getId() + " no existe en la Base de Datos." +
                            "No puede actualizar algo que no existe :(");
        }
    }

    @DeleteMapping("/borrar/{id}")
    public ResponseEntity<String> eliminarPaciente (@PathVariable Long id) throws ResourceNotFoundException{
        Optional<Paciente> pacienteBuscado = pacienteService.buscarPaciente(id);
        if (pacienteBuscado.isPresent()) {
            pacienteService.eliminarPaciente(id);
            LOGGER.warn("Se elimino el paciente de id: " + id);
            return ResponseEntity.ok().body("Se elimino el paciente de id: " + id);
        } else {
            LOGGER.error("No se pudo eliminar el paciente con id " +
                    id + ". Verificar si existe");
            throw new ResourceNotFoundException("No se pudo eliminar el paciente con id " +
                    id + ". Verificar si existe");
        }
    }

    @GetMapping
    public ResponseEntity<List<Paciente>> buscarTodosPacientes () {
        LOGGER.info("Se listaron todos los pacientes con éxito");
        return ResponseEntity.ok(pacienteService.listarPacientes());
    }
}
