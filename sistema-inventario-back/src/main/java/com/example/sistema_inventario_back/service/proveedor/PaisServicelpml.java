package com.example.sistema_inventario_back.service.proveedor;

import com.example.sistema_inventario_back.dto.proveedor.ciudad.CiudadResponseDTO;
import com.example.sistema_inventario_back.dto.proveedor.pais.PaisDTO;
import com.example.sistema_inventario_back.dto.proveedor.pais.PaisListarDTO;
import com.example.sistema_inventario_back.dto.proveedor.pais.PaisResponseDTO;
import com.example.sistema_inventario_back.entity.proveedor.Pais;
import com.example.sistema_inventario_back.repository.proveedor.PaisRepository;
import com.example.sistema_inventario_back.service.proveedor.proveedor_interface.PaisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaisServicelpml implements PaisService {

    @Autowired
    private PaisRepository paisRepository;

    //Servicio para crear un nuevo pais
    @Override
    public Pais createPais(PaisDTO paisDTO){

        if (paisDTO.getNombrePais() == null || paisDTO.getNombrePais().trim().isEmpty()){
            throw new IllegalArgumentException("El nombre del país es obligatorio");
        }

        Pais pais = new Pais();
        pais.setNombrePais(paisDTO.getNombrePais().trim());

        return paisRepository.save(pais);
    }

    //Servicio para listar a todos los paises
    @Override
    public List<PaisListarDTO> getAllPaises(){
        return paisRepository.findAllListar();
    }

    //Servicio para actualizar el pais
    @Override
    public PaisResponseDTO updatePais(Integer idPais, PaisDTO paisDTO){
        Pais pais = paisRepository.findById(idPais)
                .orElseThrow(() -> new RuntimeException("Pais no encontrado con ID: " +idPais));

        if (paisDTO.getNombrePais() != null && !paisDTO.getNombrePais().trim().isEmpty()
            && !paisDTO.getNombrePais().trim().equalsIgnoreCase(pais.getNombrePais())){
            pais.setNombrePais(paisDTO.getNombrePais().trim());
        }

        Pais paisActualizada = paisRepository.save(pais);
        return matToResponseDTO(paisActualizada);
    }

    //Servicio para buscar pais por el id
    @Override
    public PaisResponseDTO getPaisById(Integer idPais){
        Pais pais = paisRepository.findById(idPais)
                .orElseThrow(() -> new RuntimeException("Pais no encontrada con ID: " +idPais));

        return mapToResponseDTOWithCiudades(pais);
    }

    //Metodo de utilidad para mapear Pais a PaisResponseDTO incluyendo ciudades
    private PaisResponseDTO mapToResponseDTOWithCiudades(Pais pais){
        PaisResponseDTO dto = new PaisResponseDTO();
        dto.setIdPais(pais.getIdPais());
        dto.setNombrePais(pais.getNombrePais());

        List<CiudadResponseDTO> ciudadesDTO = pais.getCiudades().stream().map(ciudad -> {
            CiudadResponseDTO ciudadDTO = new CiudadResponseDTO();
            ciudadDTO.setIdCiudad(ciudad.getIdCiudad());
            ciudadDTO.setNombreCiudad(ciudad.getNombreCiudad());
            ciudadDTO.setIdPais(ciudad.getPais().getIdPais());
            ciudadDTO.setNombrePais(ciudad.getPais().getNombrePais());

            return ciudadDTO;
        }).toList();

        dto.setCiudades(ciudadesDTO);
        return dto;
    }

    //Metodo para modificar el mapeo Pais -> PaisResponseDTO
    private PaisResponseDTO matToResponseDTO(Pais pais){
        PaisResponseDTO paisResponseDTO = new PaisResponseDTO();
        paisResponseDTO.setIdPais(pais.getIdPais());
        paisResponseDTO.setNombrePais(pais.getNombrePais());

        return paisResponseDTO;
    }
}
