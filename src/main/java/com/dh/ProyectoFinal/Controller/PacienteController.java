package com.dh.ProyectoFinal.Controller;

import com.dh.ProyectoFinal.Entity.Paciente;
import com.dh.ProyectoFinal.Service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {
    private PacienteService pacienteService;
    @Autowired
    public PacienteController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }
    /*
    @GetMapping
    public String traerPacienteXEmail(Model model, @RequestParam("email") String email){
        Paciente pacienteBuscado=pacienteService.buscarPacienteByEmail(email);
        model.addAttribute("nombre",pacienteBuscado.getNombre());
        model.addAttribute("apellido",pacienteBuscado.getApellido());
        return "index";
    }
     */
    @PostMapping
    public Paciente registrarNuevoPaciente(@RequestBody Paciente paciente){
        return pacienteService.guardarPaciente(paciente);
    }
    @PutMapping
    public String actualizarPaciente(@RequestBody Paciente paciente){
        Paciente pacienteBuscado=pacienteService.buscarPaciente(paciente.getId());
        if (pacienteBuscado!=null){
            pacienteService.actualizarPaciente(paciente);
            return "Se actulizó el paciente con apellido "+paciente.getApellido();
        }
        else{
            return "El paciente con id= "+paciente.getId()+" no existe en la BD." +
                    "No puede actualizar algo que no existe :(";
        }

    }

    @DeleteMapping("eliminar/{id}")
    public String eliminarPaciente(@PathVariable("id") Integer id){
        Paciente pacienteBuscado = pacienteService.buscarPaciente(id);
        if(pacienteBuscado != null) {
            pacienteService.eliminarPaciente(id);
            return "Se ha eliminado el paciente con el id: " + id;
        }else{
            return "El paciente con id: " + id + " no existe en la base de datos.";
        }
    }

    @GetMapping("buscar/{id}")
    public Paciente buscarPaciente(@PathVariable("id") Integer id){
        return pacienteService.buscarPaciente(id);
    }

    @GetMapping()
    public List<Paciente> listarPacientes(){
        List<Paciente> pacienteList = new ArrayList<>();
        pacienteList = pacienteService.buscarTodosPacientes();
        if(pacienteList != null){
            return pacienteList;
        }
        return pacienteList;

    }

}
