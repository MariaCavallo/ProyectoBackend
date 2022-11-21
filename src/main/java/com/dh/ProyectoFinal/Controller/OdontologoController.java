package com.dh.ProyectoFinal.Controller;

import com.dh.ProyectoFinal.Entity.Odontologo;
import com.dh.ProyectoFinal.Service.OdontologoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//rest o no rest
@RestController
@RequestMapping("/odontologos")
public class OdontologoController {
    private OdontologoService odontologoService;

    @Autowired
    public OdontologoController(OdontologoService odontologoService) {
        this.odontologoService = odontologoService;
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<Odontologo>> buscarOdontologos() {
        return ResponseEntity.ok(odontologoService.listarOdontologos());
    }

    @PostMapping
    public ResponseEntity<Odontologo> registrarOdontologo(@RequestBody Odontologo odontologo){
        return ResponseEntity.ok(odontologoService.guardarOdontologo(odontologo));
    }

    @PutMapping
    public ResponseEntity<Odontologo> actualizarOdontologo(@RequestBody Odontologo odontologo){
        if(odontologoService.buscarOdontologoXId(odontologo.getId()) != null){
            odontologoService.actualizarOdontologo(odontologo);
            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Odontologo> eliminarOdontologo(@PathVariable("id") Integer id){
        ResponseEntity response = null;
        if(odontologoService.buscarOdontologoXId(id) != null) {
            odontologoService.eliminarOdontologo(id);
            response = ResponseEntity.status(HttpStatus.OK).build();
        }else{
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return response;
    }
}
