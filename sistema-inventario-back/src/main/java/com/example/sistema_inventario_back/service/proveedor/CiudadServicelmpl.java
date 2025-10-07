package com.example.sistema_inventario_back.service.proveedor;

import com.example.sistema_inventario_back.dto.proveedor.ciudad.CiudadListarDTO;
import com.example.sistema_inventario_back.dto.proveedor.ciudad.CiudadRequestDTO;
import com.example.sistema_inventario_back.dto.proveedor.ciudad.CiudadPageResponseDTO;
import com.example.sistema_inventario_back.dto.proveedor.ciudad.CiudadResponseDTO;
import com.example.sistema_inventario_back.dto.proveedor.proveedor.ProveedorResponseDTO;
import com.example.sistema_inventario_back.entity.proveedor.Ciudad;
import com.example.sistema_inventario_back.entity.proveedor.Pais;
import com.example.sistema_inventario_back.entity.proveedor.Proveedor;
import com.example.sistema_inventario_back.repository.proveedor.CiudadRepository;
import com.example.sistema_inventario_back.repository.proveedor.PaisRepository;
import com.example.sistema_inventario_back.service.proveedor.proveedor_interface.CiudadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CiudadServicelmpl implements CiudadService {

    @Autowired
    private CiudadRepository ciudadRepository;

    @Autowired
    private PaisRepository paisRepository;

    // Servicio para listar a todas las ciudades (Nueva Version)
    @Override
    public List<CiudadListarDTO> getAllCiudadesNew(){
        return ciudadRepository.findAllListar();
    }

    @Override
    public List<CiudadResponseDTO> getAllCiudades(){
        List<Ciudad> ciudades = ciudadRepository.findAll();

        return ciudades
                .stream()
                .map(this::mapToResponseDTO)
                .toList();
    }

    //Servicio para listar a todas las ciudades (Paginado)
    @Override
    public CiudadPageResponseDTO getAllCiudadesPage(Pageable pageable){
        Page<Ciudad> ciudadPage = ciudadRepository.findAll(pageable);

        List<CiudadResponseDTO> ciudades = ciudadPage.getContent().stream()
                .map(ciudad -> {
                    CiudadResponseDTO dto = new CiudadResponseDTO();
                    dto.setIdCiudad(ciudad.getIdCiudad());
                    dto.setNombreCiudad(ciudad.getNombreCiudad());
                    dto.setIdPais(ciudad.getPais().getIdPais());
                    dto.setNombrePais(ciudad.getPais().getNombrePais());
                    return dto;
                }).toList();

        CiudadPageResponseDTO responseDTO = new CiudadPageResponseDTO();
        responseDTO.setCiudades(ciudades);
        responseDTO.setPaginaActual(ciudadPage.getNumber());
        responseDTO.setTotalPaginas(ciudadPage.getTotalPages());
        responseDTO.setTotalEmentos(ciudadPage.getTotalElements());
        responseDTO.setTamanioPagina(ciudadPage.getSize());

        return responseDTO;
    }

    //Servicio para crear una nueva Ciudad
    @Override
    public CiudadResponseDTO createCiudad(CiudadRequestDTO ciudadRequestDTO){
        Ciudad ciudad = matToEntity(ciudadRequestDTO);
        Ciudad ciudadSaved = ciudadRepository.save(ciudad);


        return mapToResponseDTO(ciudadSaved);
    }

    //Servicio para buscar la ciudad por el id
    @Override
    public CiudadResponseDTO getCiudadById(Integer idCiudad){
        Ciudad ciudad = ciudadRepository.findById(idCiudad)
                .orElseThrow(() -> new RuntimeException("Ciudad no encontrada con ID: " +idCiudad));

        return mapToResponseDTO(ciudad);
    }

    //Servicio para actualizar la ciudad
    @Override
    public CiudadResponseDTO updateCiudad(Integer idCiudad, CiudadRequestDTO ciudadRequestDTO){

        Ciudad ciudad = ciudadRepository.findById(idCiudad)
                .orElseThrow(() -> new RuntimeException("Ciudad no encontrada con ID: " +idCiudad));

        if (ciudadRequestDTO.getNombreCiudad() != null && !ciudadRequestDTO.getNombreCiudad().equals(ciudad.getNombreCiudad())){
            ciudad.setNombreCiudad(ciudadRequestDTO.getNombreCiudad());
        }

        if(ciudadRequestDTO.getIdPais() != null && !ciudadRequestDTO.getIdPais().equals(ciudad.getPais().getIdPais())){
            Pais pais = paisRepository.findById(ciudadRequestDTO.getIdPais())
                    .orElseThrow(() -> new RuntimeException("Pais no encontrado con ID: " + ciudadRequestDTO.getIdPais()));

            ciudad.setPais(pais);
        }

        Ciudad ciudadActualizada = ciudadRepository.save(ciudad);
        return mapToResponseDTO(ciudadActualizada);
    }

    private Ciudad matToEntity(CiudadRequestDTO ciudadRequestDTO){
        Pais pais = paisRepository.findById(ciudadRequestDTO.getIdPais())
                .orElseThrow(() -> new RuntimeException("País no encontrado: " + ciudadRequestDTO.getIdPais()));

        Ciudad ciudad = new Ciudad();
        ciudad.setNombreCiudad(ciudadRequestDTO.getNombreCiudad());
        ciudad.setPais(pais);

        return ciudad;
    }

    //Metodo para modificar el mapeo Ciudad -> CiudadResponseDTO
    private CiudadResponseDTO mapToResponseDTO(Ciudad ciudad){
        CiudadResponseDTO ciudadResponseDTO = new CiudadResponseDTO();
        ciudadResponseDTO.setIdCiudad(ciudad.getIdCiudad());
        ciudadResponseDTO.setNombreCiudad(ciudad.getNombreCiudad());

        // Listar los proveedores de una ciudad
        List<ProveedorResponseDTO> proveedores =  ciudad.getProveedores()
                .stream()
                .map(this::matProveedorToDTO)
                .toList();

        ciudadResponseDTO.setProveedor(proveedores);

        if (ciudad.getPais() != null){
            ciudadResponseDTO.setIdPais(ciudad.getPais().getIdPais());
            ciudadResponseDTO.setNombrePais(ciudad.getPais().getNombrePais());
        }

        return ciudadResponseDTO;
    }

    // Metodo para listar a todos los proveedores
    private ProveedorResponseDTO matProveedorToDTO(Proveedor proveedor){
        ProveedorResponseDTO dto = new ProveedorResponseDTO();

        dto.setIdProveedor(proveedor.getIdProveedor());
        dto.setNombreComercial(proveedor.getNombreComercial());
        dto.setIdentificacionFiscal(proveedor.getIdentificacionFiscal());
        dto.setCorreoElectronico(proveedor.getCorreoElectronico());
        dto.setDireccion(proveedor.getDireccion());
        dto.setEnlacePagina(proveedor.getEnlacePagina());

        return dto;
    }
}