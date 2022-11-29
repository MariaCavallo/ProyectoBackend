package com.dh.ProyectoFinal.Controller;

import com.dh.ProyectoFinal.DTO.TurnoDTO;
import com.dh.ProyectoFinal.Entity.Turno;
import com.dh.ProyectoFinal.Repository.OdontologoRepository;
import com.dh.ProyectoFinal.Repository.PacienteRepository;
import com.dh.ProyectoFinal.Service.OdontologoService;
import com.dh.ProyectoFinal.Service.PacienteService;
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
    private PacienteService pacienteService;
    private OdontologoService odontologoService;

    private PacienteRepository pacienteRepository;
    private OdontologoRepository odontologoRepository;

    @Autowired
    public TurnoController(TurnoService turnoService, PacienteService pacienteService, OdontologoService odontologoService) {
        this.turnoService = turnoService;
        this.pacienteService = pacienteService;
        this.odontologoService = odontologoService;
    }

    @PostMapping
    public ResponseEntity<TurnoDTO> guardarTurno (@RequestBody TurnoDTO turno) {
        PacienteService pacienteService = new PacienteService(pacienteRepository);
        OdontologoService odontologoService = new OdontologoService(odontologoRepository);
        ResponseEntity<TurnoDTO> respuesta;

        if (pacienteService.buscarPaciente(turno.getPacienteId()).isPresent() &&
                odontologoService.buscarOdontologoXId(turno.getOdontologoId()).isPresent()){
            respuesta = ResponseEntity.ok(turnoService.guardarTurno(turno));
        } else {
            respuesta = ResponseEntity.badRequest().build();
        }
        return respuesta;
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<TurnoDTO> buscarTurno (@PathVariable("id") Long id) {
        Optional<TurnoDTO> turnoBuscado = turnoService.buscarTurno(id);
        if (turnoBuscado.isPresent()){
            return ResponseEntity.ok(turnoBuscado.get());
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping
    public ResponseEntity<String> actualizarTurno (@RequestBody TurnoDTO turno) {
        PacienteService pacienteService = new PacienteService(pacienteRepository);
        OdontologoService odontologoService = new OdontologoService(odontologoRepository);
        ResponseEntity<Turno> respuesta;

        if (turnoService.buscarTurno(turno.getId()).isPresent()){

            if (pacienteService.buscarPaciente(turno.getPacienteId()).isPresent() &&
                    odontologoService.buscarOdontologoXId(turno.getOdontologoId()).isPresent()){
                turnoService.actualizarTurno(turno);

                return ResponseEntity.ok().body("Se actualizo el turno de id: " + turno.getId());
            } else {
                return ResponseEntity.badRequest().body("Error al actualizar el turno con el id= " + turno.getId() +
                        "Verificar si el odontologo y/o el paciente existen en la base de datos.");
                }
            } else {
            return ResponseEntity.badRequest().body("No se puede actualizar el turno con el id= " + turno.getId() +
                    "Ese turno no existe en la base de datos!");
        }
    }

    @DeleteMapping("/borrar/{id}")
    public ResponseEntity<String> eliminar (@PathVariable Long id) {
        if (turnoService.buscarTurno(id).isPresent()){
            turnoService.eliminarTurno(id);
            return ResponseEntity.ok().body("Se elimino el turno con id= " + id);
        } else {
            return ResponseEntity.badRequest().body("No se ha encontrado un paciente con id= "
                    + id + ". Ya que el mismo no existe en la base de datos.");
        }
    }

    @GetMapping
    public ResponseEntity<List<TurnoDTO>> buscarTodos () {
        return ResponseEntity.ok(turnoService.buscarTodosTurno());
    }
}
