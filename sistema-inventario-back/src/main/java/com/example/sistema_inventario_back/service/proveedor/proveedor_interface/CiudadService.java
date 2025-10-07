package com.example.sistema_inventario_back.service.proveedor.proveedor_interface;

import com.example.sistema_inventario_back.dto.proveedor.ciudad.CiudadListarDTO;
import com.example.sistema_inventario_back.dto.proveedor.ciudad.CiudadRequestDTO;
import com.example.sistema_inventario_back.dto.proveedor.ciudad.CiudadPageResponseDTO;
import com.example.sistema_inventario_back.dto.proveedor.ciudad.CiudadResponseDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CiudadService {

    // Listar Ciudades (Nueva Version)
    List<CiudadListarDTO> getAllCiudadesNew();

    //Crear una nueva Ciudad
    CiudadResponseDTO createCiudad(CiudadRequestDTO ciudadRequestDTO);

    //Iterar todas las ciudades (Paginadas)
    CiudadPageResponseDTO getAllCiudadesPage(Pageable pageable);

    // Iterar todas las ciudades sin Paginar
    List<CiudadResponseDTO> getAllCiudades();

    //Llamar una ciudad mediante id
    CiudadResponseDTO getCiudadById(Integer id_ciudad);

    //Actualizar los datos de una ciudad
    CiudadResponseDTO updateCiudad(Integer idCiudad, CiudadRequestDTO ciudadRequestDTO);
}