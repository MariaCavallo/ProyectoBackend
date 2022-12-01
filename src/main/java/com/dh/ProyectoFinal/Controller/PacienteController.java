package com.dh.ProyectoFinal.Controller;

import com.dh.ProyectoFinal.Entity.Paciente;
import com.dh.ProyectoFinal.Exception.ResourceNotFoundException;
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
    public ResponseEntity<Paciente> guardarPaciente (@RequestBody Paciente paciente) {
        return ResponseEntity.ok(pacienteService.guardarPaciente(paciente));
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<Paciente> buscarPacienteXid (@PathVariable("id") Long id) {
        Optional<Paciente> pacienteBuscado = pacienteService.buscarPaciente(id);
        if (pacienteBuscado.isPresent()) {
            return ResponseEntity.ok(pacienteBuscado.get());
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
    @GetMapping("/buscar/mail")
    public ResponseEntity<Optional<Paciente>> buscarPacienteXemail (@RequestParam("email") String string) {
        return ResponseEntity.ok(pacienteService.buscarByEmail(string));
    }

    @PutMapping("/actualizar")
    public ResponseEntity<String> actualizarPaciente (@RequestBody Paciente paciente) {
        Optional<Paciente> pacienteBuscado = pacienteService.buscarPaciente(paciente.getId());
        if (pacienteBuscado.isPresent()) {
            pacienteService.actualizarPaciente(paciente);
            return ResponseEntity.ok().body("Se actualizo el paciente de apellido: " + paciente.getApellido());
        } else {
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
            return ResponseEntity.ok().body("Se elimino el paciente de id: " + id);
        } else {
            throw new ResourceNotFoundException("No se pudo eliminar el paciente con id " +
                    id + ". Verificar si existe");
        }
    }

    @GetMapping
    public ResponseEntity<List<Paciente>> buscarTodosPacientes () {
        return ResponseEntity.ok(pacienteService.buscarTodosPacientes());
    }
}
