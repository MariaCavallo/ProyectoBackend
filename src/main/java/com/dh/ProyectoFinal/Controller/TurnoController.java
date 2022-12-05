package com.dh.ProyectoFinal.Controller;

import com.dh.ProyectoFinal.DTO.TurnoDTO;
import com.dh.ProyectoFinal.Entity.Turno;
import com.dh.ProyectoFinal.Exception.ResourceNotFoundException;
import com.dh.ProyectoFinal.Repository.OdontologoRepository;
import com.dh.ProyectoFinal.Repository.PacienteRepository;
import com.dh.ProyectoFinal.Service.OdontologoService;
import com.dh.ProyectoFinal.Service.PacienteService;
import com.dh.ProyectoFinal.Service.TurnoService;
import org.apache.log4j.Logger;
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

    private static final Logger LOGGER = Logger.getLogger(TurnoController.class);

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
            LOGGER.info("Se agregó correctamente el turno con id: " + turno.getId());
            respuesta = ResponseEntity.ok(turnoService.guardarTurno(turno));
        } else {
            LOGGER.error("No se pudo agregar el turno con id: " + turno.getId());
            respuesta = ResponseEntity.badRequest().build();
        }
        return respuesta;
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<TurnoDTO> buscarTurno (@PathVariable("id") Long id) {
        Optional<TurnoDTO> turnoBuscado = turnoService.buscarTurno(id);
        if (turnoBuscado.isPresent()){
            LOGGER.info("Se encontró el turno con id: " + id);
            return ResponseEntity.ok(turnoBuscado.get());
        } else {
            LOGGER.error("No se encontró ningun turno con id: " + id + " Verifique que el turno exista");
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
                LOGGER.info("Se actualizo el turno de id: " + turno.getId());
                return ResponseEntity.ok().body("Se actualizo el turno de id: " + turno.getId());
            } else {
                LOGGER.error("Error al actualizar el turno con el id= " + turno.getId() +
                        "Verificar si el odontologo y/o el paciente existen en la base de datos.");
                return ResponseEntity.badRequest().body("Error al actualizar el turno con el id= " + turno.getId() +
                        "Verificar si el odontologo y/o el paciente existen en la base de datos.");
                }
            } else {
            LOGGER.error("No se puede actualizar el turno con el id= " + turno.getId() +
                    "Ese turno no existe en la base de datos!");
            return ResponseEntity.badRequest().body("No se puede actualizar el turno con el id= " + turno.getId() +
                    "Ese turno no existe en la base de datos!");
        }
    }

    @DeleteMapping("/borrar/{id}")
    public ResponseEntity<String> eliminarTurno (@PathVariable Long id) throws ResourceNotFoundException{
        if (turnoService.buscarTurno(id).isPresent()){
            turnoService.eliminarTurno(id);
            LOGGER.warn("Se elimino el turno con id= " + id);
            return ResponseEntity.ok().body("Se elimino el turno con id= " + id);
        } else {
            LOGGER.error("No se ha encontrado un turno con id= " +
                    id + ". Ya que el mismo no existe en la base de datos.");
            throw new ResourceNotFoundException("No se ha encontrado un turno con id= " +
                    id + ". Ya que el mismo no existe en la base de datos.");
        }
    }

    @GetMapping
    public ResponseEntity<List<TurnoDTO>> buscarTodosTurnos () {
        LOGGER.info("Se listaron todos los turnos con éxito");
        return ResponseEntity.ok(turnoService.buscarTodosTurno());
    }
}
