package com.dh.ProyectoFinal.Controller;

import com.dh.ProyectoFinal.Entity.Turno;
import com.dh.ProyectoFinal.Repository.OdontologoRepository;
import com.dh.ProyectoFinal.Repository.TurnoRepository;
import com.dh.ProyectoFinal.Service.OdontologoService;
import com.dh.ProyectoFinal.Service.PacienteService;
import com.dh.ProyectoFinal.Service.TurnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/turnos")
public class TurnoController {
    private TurnoService turnoService;

    @Autowired
    public TurnoController(TurnoService turnoService) {
        this.turnoService = turnoService;
    }

    @GetMapping
    public ResponseEntity<List<Turno>> listarTurnos(){
        return ResponseEntity.ok(turnoService.buscarTodosTurno());
    }

    @PostMapping
    public ResponseEntity<Turno> registarTurno(@RequestBody Turno turno){
        OdontologoRepository odontologoRepository = null;
        PacienteService pacienteService= new PacienteService();
        OdontologoService odontologoService= new OdontologoService(odontologoRepository);
        ResponseEntity<Turno> respuesta;

        if (pacienteService.buscarPaciente(turno.getPaciente().getId())!=null&&
        odontologoService.buscarOdontologoXId(turno.getOdontologo().getId())!=null){
            //ambos existen en la BD
            //podemos registrar el turno sin problemas, indicamos ok (200)
            respuesta=ResponseEntity.ok(turnoService.guardarTurno(turno));
        }
        else{
            //uno o ambos no existen, debemos bloquear la operación
            respuesta=ResponseEntity.badRequest().build();
            //alternativa para seleccionar cualquier código
            //respuesta=ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return respuesta;
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<Turno> buscarTurno(@PathVariable("id") Integer id) {
        TurnoRepository turnoRepository = null;
        Turno turnoBuscado = turnoRepository.findById(id).get();
        if (turnoBuscado != null) {
            return ResponseEntity.ok(turnoBuscado);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping
    public ResponseEntity<String> actualizarTurno(@RequestBody Turno turno) {
        PacienteService pacienteService = new PacienteService();
        OdontologoRepository odontologoRepository = null;
        OdontologoService odontologoService = new OdontologoService(odontologoRepository);
        ResponseEntity<Turno> response;

        if (turnoService.buscarTurno(turno.getId()) != null) {
            if (pacienteService.buscarPaciente(turno.getPaciente().getId()) != null &&
                    odontologoService.buscarOdontologoXId(turno.getOdontologo().getId())!= null) {
                turnoService.actualizarTurno(turno);
                return ResponseEntity.ok("Se ha actualizado el turno con id: " + turno.getId());

            } else {

                return ResponseEntity.badRequest().body("Error a actualizar, verificar si el odontologo y/o el paciente existen en la base de datos");
            }
        } else {
            return ResponseEntity.badRequest().body("No se puede actualizar un turno que no existe en la base de datos");
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarTurno(@PathVariable("id") Integer id) {
        if (turnoService.buscarTurno(id) != null) {
            turnoService.eliminarTurno(id);
            return ResponseEntity.ok().body("EL turno con el id: " + id + " se ha eliminado.");
        } else {
            return ResponseEntity.badRequest().body("No se puede eliminar el turno con id= " + id + " ya que no existe en la base de datos");
        }
    }

}
