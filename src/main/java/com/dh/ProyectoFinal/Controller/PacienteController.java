package com.dh.ProyectoFinal.Controller;

import com.dh.ProyectoFinal.Entity.Paciente;
import com.dh.ProyectoFinal.Service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {


    private PacienteService pacienteService;

    @Autowired
    public PacienteController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    @PostMapping
    public ResponseEntity<Paciente> guardar (@RequestBody Paciente paciente) {
        return ResponseEntity.ok(pacienteService.guardarPaciente(paciente));
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<Paciente> buscar (@PathVariable("id") Long id) {
        Optional<Paciente> pacienteBuscado = pacienteService.buscarPaciente(id);
        if (pacienteBuscado.isPresent()) {
            return ResponseEntity.ok(pacienteBuscado.get());
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
    @GetMapping("/buscar/mail")
    public ResponseEntity<Optional<Paciente>> buscar (@RequestParam("email") String string) {
        return ResponseEntity.ok(pacienteService.buscarByEmail(string));
    }

    @PutMapping("/actualizar")
    public ResponseEntity<String> actualizar (@RequestBody Paciente paciente) {
        Optional<Paciente> pacienteBuscado = pacienteService.buscarPaciente(paciente.getId());
        if (pacienteBuscado.isPresent()) {
            pacienteService.actualizarPaciente(paciente);
            return ResponseEntity.ok().body("Se actualizo el paciente de apellido: " + paciente.getApellido());
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar (@PathVariable Long id) {
        Optional<Paciente> pacienteBuscado = pacienteService.buscarPaciente(id);
        if (pacienteBuscado.isPresent()) {
            pacienteService.eliminarPaciente(id);
            return ResponseEntity.ok().body("Se elimino el paciente de id: " + id);
        } else {
            return ResponseEntity.badRequest().body("No se pudo eliminar el paciente con id: " + id);
        }
    }

    @GetMapping
    public ResponseEntity<List<Paciente>> buscarTodos () {
        return ResponseEntity.ok(pacienteService.buscarTodosPacientes());
    }
}
