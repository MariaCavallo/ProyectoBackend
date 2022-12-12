package com.dh.ProyectoFinal.Service;

import com.dh.ProyectoFinal.DTO.TurnoDTO;
import com.dh.ProyectoFinal.Entity.Odontologo;
import com.dh.ProyectoFinal.Entity.Paciente;
import com.dh.ProyectoFinal.Exception.BadRequestException;
import com.dh.ProyectoFinal.Exception.ResourceNotFoundException;
import com.dh.ProyectoFinal.Repository.TurnoRepository;
import com.dh.ProyectoFinal.Entity.Turno;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TurnoService {
    private final TurnoRepository turnoRepository;
    //private final PacienteService pacienteService;
    //private final OdontologoService odontologoService;
    private final Logger LOGGER = Logger.getLogger(TurnoService.class);

    @Autowired
    public TurnoService(TurnoRepository turnoRepository, PacienteService pacienteService, OdontologoService odontologoService){
        this.turnoRepository= turnoRepository;
        //this.pacienteService = pacienteService;
        //this.odontologoService = odontologoService;
    }

    private TurnoDTO turnoAturnoDTO(Turno turno){
        TurnoDTO respuesta = new TurnoDTO();
        respuesta.setId(turno.getId());
        respuesta.setFecha(turno.getFecha());
        respuesta.setOdontologoId(turno.getOdontologo().getId());
        respuesta.setPacienteId(turno.getPaciente().getId());
        return respuesta;
    }

    private Turno turnoDTOaTurno (TurnoDTO turnoDTO){
        Turno turno = new Turno();
        Paciente paciente = new Paciente();
        Odontologo odontologo = new Odontologo();
        //cargar los elementos
        paciente.setId(turnoDTO.getPacienteId());
        odontologo.setId(turnoDTO.getOdontologoId());
        turno.setId(turnoDTO.getId());
        turno.setFecha(turnoDTO.getFecha());
        //asociar cada elemento
        turno.setPaciente(paciente);
        turno.setOdontologo(odontologo);
        //SALIDA
        return turno;
    }

    public TurnoDTO guardarTurno (TurnoDTO turno){
        Turno turnoAGuardar=turnoDTOaTurno(turno);
        Turno turnoGuardado=turnoRepository.save(turnoAGuardar);
        LOGGER.info("Se inició un pedido de incorporación de un turno");
        return turnoAturnoDTO(turnoGuardado);
    }

    public Optional<TurnoDTO> buscarTurno(Long id){
        Optional<Turno> turnoBuscado=turnoRepository.findById(id);
        if (turnoBuscado.isPresent()){
            //turno encontrado
            LOGGER.info("Se inició un pedido de busqueda del turno con id: " + id);
            return Optional.of(turnoAturnoDTO(turnoBuscado.get()));
        }
        else{
            //no se encuentra el turno
            return Optional.empty();
        }
    }


    public List<TurnoDTO> listarTurnos() {
        LOGGER.info("Iniciando la búsqueda de todos los turnos");
        List<Turno> turnosEncontrados = turnoRepository.findAll();
        List<TurnoDTO> respuesta = new ArrayList<>();
        for (Turno t: turnosEncontrados) {
            respuesta.add(turnoAturnoDTO(t));
        }
        return respuesta;
    }

    public void actualizarTurno(TurnoDTO turnoDTO) {
        buscarTurno(turnoDTO.getId());
        turnoRepository.save(turnoDTOaTurno(turnoDTO));
        LOGGER.info("Iniciando la actualización del turno con id="+turnoDTO.getId());
    }

    public void eliminarTurno(Long id) {
        buscarTurno(id);
        turnoRepository.deleteById(id);
        LOGGER.info("Se eliminó al turno con id="+id);
    }
}
