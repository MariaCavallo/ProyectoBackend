package com.dh.ProyectoFinal.Controller;

import com.dh.ProyectoFinal.Entity.Odontologo;
import com.dh.ProyectoFinal.Service.OdontologoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

//rest o no rest
@RestController
@RequestMapping("/odontologos")
public class OdontologoController {

    @Autowired
    private OdontologoService odontologoService;


    @PostMapping
    public ResponseEntity<Odontologo> guardar (@RequestBody Odontologo odontologo) {
        return ResponseEntity.ok(odontologoService.guardarOdontologo(odontologo));
    }

    @GetMapping("/buscar")
    public ResponseEntity<Optional<Odontologo>> buscar (@RequestParam("id") Long id) {
        return ResponseEntity.ok(odontologoService.buscarOdontologoXId(id));
    }

    @PutMapping
    public ResponseEntity<String> actualizar (@RequestBody Odontologo odontologo) {
        odontologoService.actualizarOdontologo(odontologo);
        return ResponseEntity.ok().body("Se actualizo el paciente de apellido: " + odontologo.getApellido());
    }

    @DeleteMapping("/borrar")
    public ResponseEntity<String> eliminar (@RequestParam("id") Long id) {
        odontologoService.eliminarOdontologo(id);
        return ResponseEntity.ok().body("Se elimino el odontologo de id: " + id);
    }

    @GetMapping
    public ResponseEntity<List<Odontologo>> buscarTodos () {
        return ResponseEntity.ok(odontologoService.listarOdontologos());
    }
}
