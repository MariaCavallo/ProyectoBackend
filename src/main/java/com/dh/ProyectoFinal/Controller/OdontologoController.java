package com.dh.ProyectoFinal.Controller;

import com.dh.ProyectoFinal.Entity.Odontologo;
import com.dh.ProyectoFinal.Exception.ResourceNotFoundException;
import com.dh.ProyectoFinal.Service.OdontologoService;
import com.dh.ProyectoFinal.Service.PacienteService;
import com.dh.ProyectoFinal.Service.TurnoService;
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
    public ResponseEntity<Odontologo> buscarOdontologoXid (@PathVariable Long id) {
        Optional<Odontologo> odontologoBuscado = odontologoService.buscarOdontologoXId(id);
        if (odontologoBuscado.isPresent()){
            return ResponseEntity.ok(odontologoBuscado.get());
        } else {
            return  ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/actualizar")
    public ResponseEntity<String> actualizarOdontologo (@RequestBody Odontologo odontologo) {
        Optional<Odontologo> odontologoBuscado = odontologoService.buscarOdontologoXId(odontologo.getId());
        if (odontologoBuscado.isPresent()) {
            odontologoService.actualizarOdontologo(odontologo);
            return ResponseEntity.ok().body("Se actualizo el odontologo con apellido: " + odontologo.getApellido());
        } else {
            return ResponseEntity.badRequest().body("El odontologo con id= " + odontologo.getId() +
                    " no existe en la Base de Datos, No se puede actualizar algo que no existe!");
        }
    }

    @DeleteMapping("/borrar/{id}")
    public ResponseEntity<String> eliminarOdontologo (@PathVariable Long id) throws ResourceNotFoundException{
        Optional<Odontologo> odontologoBuscado = odontologoService.buscarOdontologoXId(id);
        if (odontologoBuscado.isPresent()) {
            odontologoService.eliminarOdontologo(id);
            return ResponseEntity.ok().body("Se elimino el odontologo de id: " + id);
        } else {
            throw new ResourceNotFoundException("No se encuentra un odontologo con id= " +
                    id + " . Verificar el ingreso.");
        }
    }

    @GetMapping
    public ResponseEntity<List<Odontologo>> buscarTodosOdontologos () {
        return ResponseEntity.ok(odontologoService.listarOdontologos());
    }
}
