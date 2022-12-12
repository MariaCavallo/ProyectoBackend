package com.dh.ProyectoFinal.Controller;

import com.dh.ProyectoFinal.DTO.TurnoDTO;
import com.dh.ProyectoFinal.Entity.Turno;
import com.dh.ProyectoFinal.Exception.BadRequestException;
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

    private final TurnoService turnoService;
    private final PacienteService pacienteService;
    private final OdontologoService odontologoService;

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
    public ResponseEntity<TurnoDTO> guardarTurno(@RequestBody TurnoDTO turnoDTO) throws BadRequestException, ResourceNotFoundException {
        ResponseEntity<TurnoDTO> respuesta;
        if (pacienteService.buscarPaciente(turnoDTO.getPacienteId()).isPresent()&&
                odontologoService.buscarOdontologo(turnoDTO.getOdontologoId()).isPresent()
        ){
            respuesta=ResponseEntity.ok(turnoService.guardarTurno(turnoDTO));
        }
        else{
            throw new BadRequestException("No se puede registrar un turno cuando no exista un odontologo y/o un paciente");
        }
        return respuesta;
    }

    @PutMapping
    public ResponseEntity<String> actualizarTurno (@RequestBody TurnoDTO turno) throws BadRequestException, ResourceNotFoundException {
        turnoService.actualizarTurno(turno);
        return ResponseEntity.ok("Se actualizó el turno con id=" + turno.getId());
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarTurno (@PathVariable Long id) throws ResourceNotFoundException, BadRequestException {
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

    @GetMapping("/buscar/{id}")
    public ResponseEntity<TurnoDTO> buscarTurno(@PathVariable("id") Long id) throws BadRequestException, ResourceNotFoundException {
        return ResponseEntity.ok(turnoService.buscarTurno(id).get());
    }
    @GetMapping
    public ResponseEntity<List<TurnoDTO>> listarTurnos () {
        LOGGER.info("Se listaron todos los turnos con éxito");
        return ResponseEntity.ok(turnoService.listarTurnos());
    }
}
