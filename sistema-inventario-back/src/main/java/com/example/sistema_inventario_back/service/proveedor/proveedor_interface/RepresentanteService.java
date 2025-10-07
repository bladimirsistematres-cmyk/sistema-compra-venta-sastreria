package com.example.sistema_inventario_back.service.proveedor.proveedor_interface;

import com.example.sistema_inventario_back.dto.proveedor.representante.RepresentanteListarDTO;
import com.example.sistema_inventario_back.dto.proveedor.representante.RepresentantePageResponseDTO;
import com.example.sistema_inventario_back.dto.proveedor.representante.RepresentanteRequestDTO;
import com.example.sistema_inventario_back.dto.proveedor.representante.RepresentanteResponseDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RepresentanteService {

    //Servicio para crear un nuevo Representante
    RepresentanteResponseDTO createRepresentante(RepresentanteRequestDTO representanteRequestDTO);

    //Servicio para listar todos los Representantes
    RepresentantePageResponseDTO getAllRepresentantes(Pageable pageable);

    //Servicio para obtener un Representante por ID
    RepresentanteResponseDTO getRepresentanteById(Integer id);

    //Servicio para actualizar un Representante por Id
    RepresentanteResponseDTO updateRepresentante(Integer id, RepresentanteRequestDTO representanteRequestDTO);

    //Servicio para buscar representantes por filtros multiples
    RepresentantePageResponseDTO buscarRepresentantesConFiltros(
            String nombre,
            String cargo,
            String cedulaIdentidad,
            String correoElectronico,
            Pageable pageable
    );

    //Servicio para listar a todos los representantes del proveedor segun id
    List<RepresentanteListarDTO> getAllRepresentantesByIdProveedor(Integer idProveedor);
}