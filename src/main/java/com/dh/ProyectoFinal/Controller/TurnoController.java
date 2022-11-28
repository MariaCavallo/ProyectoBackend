package com.dh.ProyectoFinal.Controller;

import com.dh.ProyectoFinal.Entity.Turno;
import com.dh.ProyectoFinal.Service.TurnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/turnos")
public class TurnoController {


    private TurnoService turnoService;

    @Autowired
    public TurnoController (TurnoService turnoService){
        this.turnoService = turnoService;
    }


    @PostMapping
    public ResponseEntity<Turno> guardar (@RequestBody Turno turno) {
        return ResponseEntity.ok(turnoService.guardarTurno(turno));
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<Turno> buscar (@PathVariable Long id) {
        Optional<Turno> turnoBuscado = turnoService.buscarTurno(id);
        if (turnoBuscado.isPresent()){
            return ResponseEntity.ok(turnoBuscado.get());
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping
    public ResponseEntity<String> actualizar (@RequestBody Turno turno) {
        Optional<Turno> turnoBuscado = turnoService.buscarTurno(turno.getId());
        if (turnoBuscado.isPresent()){
            turnoService.actualizarTurno(turno);
            return ResponseEntity.ok().body("Se actualizo el turno de id: " + turno.getId());
        } else {
            return ResponseEntity.badRequest().body("El turno con id= " + turno.getId() +
                    "no existe en la Base de Datos. No se puede actualizar algo que no existe!");
        }
    }

    @DeleteMapping("/borrar/{id}")
    public ResponseEntity<String> eliminar (@PathVariable Long id) {
        Optional<Turno> turnoBuscado = turnoService.buscarTurno(id);
        if (turnoBuscado.isPresent()){
            turnoService.eliminarTurno(id);
            return ResponseEntity.ok().body("Se elimino el turno de id: " + id);
        } else {
            return ResponseEntity.badRequest().body("No se ha encontrado un paciente con id= "
                    + id + ". Verifique el ingreso.");
        }
    }

    @GetMapping
    public ResponseEntity<List<Turno>> buscarTodos () {
        return ResponseEntity.ok(turnoService.buscarTodosTurno());
    }
}
