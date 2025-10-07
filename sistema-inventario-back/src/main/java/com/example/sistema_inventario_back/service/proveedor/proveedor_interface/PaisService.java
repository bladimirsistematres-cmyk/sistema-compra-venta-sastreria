package com.example.sistema_inventario_back.service.proveedor.proveedor_interface;


import com.example.sistema_inventario_back.dto.proveedor.pais.PaisDTO;
import com.example.sistema_inventario_back.dto.proveedor.pais.PaisListarDTO;
import com.example.sistema_inventario_back.dto.proveedor.pais.PaisResponseDTO;
import com.example.sistema_inventario_back.entity.proveedor.Pais;

import java.util.List;

public interface PaisService {

    //Servicio para crear un nuevo Pais
    Pais createPais(PaisDTO paisDTO);

    //Servicio para listar a todos los paises con sus respectivas ciudades
    List<PaisListarDTO> getAllPaises();

    //Servicio para actualizar el pais
    PaisResponseDTO updatePais(Integer idPais, PaisDTO paisDTO);

    //Servicio para buscar por id
    PaisResponseDTO getPaisById(Integer idPais);
}