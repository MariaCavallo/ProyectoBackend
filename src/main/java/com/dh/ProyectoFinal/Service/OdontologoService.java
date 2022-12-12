package com.dh.ProyectoFinal.Service;

import com.dh.ProyectoFinal.Entity.Odontologo;
import com.dh.ProyectoFinal.Exception.ResourceNotFoundException;
import com.dh.ProyectoFinal.Repository.OdontologoRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OdontologoService{

    private final OdontologoRepository odontologoRepository;

    private final Logger LOGGER = Logger.getLogger(OdontologoService.class);

    @Autowired
    public OdontologoService(OdontologoRepository odontologoRepository){
        this.odontologoRepository = odontologoRepository;
    }

    public Odontologo guardarOdontologo(Odontologo odontologo) {
        LOGGER.info("Se ha registrado exitosamente un nuevo odontólogo");
        return odontologoRepository.save(odontologo);
    }

    public Optional<Odontologo> buscarOdontologo(Long id) throws ResourceNotFoundException {
        Optional<Odontologo> odontologoBuscado = odontologoRepository.findById(id);
        if (odontologoBuscado.isEmpty()) {
            LOGGER.warn("No se encontró ningún odontólogo con id="+id);
            throw new ResourceNotFoundException("No se encontró ningún odontólogo con id="+id);
        }
        LOGGER.info("Iniciando la búsqueda de un odontólogo con id="+id);
        return odontologoBuscado;
    }

    public List<Odontologo> listarOdontologos() {
        LOGGER.info("Iniciando la búsqueda de todos los odontólogos");
        return odontologoRepository.findAll();
    }

    public void actualizarOdontologo(Odontologo odontologo) throws ResourceNotFoundException {
        buscarOdontologo(odontologo.getId());
        odontologoRepository.save(odontologo);
        LOGGER.info("Iniciando la actualización del odontólogo con id="+odontologo.getId());
    }

    public void eliminarOdontologo(Long id) throws ResourceNotFoundException {
        buscarOdontologo(id);
        odontologoRepository.deleteById(id);
        LOGGER.info("Iniciando la eliminación del odontólogo con id="+id);
    }
}
