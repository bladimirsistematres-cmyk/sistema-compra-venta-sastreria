package com.example.sistema_inventario_back.service.proveedor.proveedor_interface;

import com.example.sistema_inventario_back.dto.proveedor.proveedor.*;
import com.example.sistema_inventario_back.dto.proveedor.representante.RepresentanteResponseDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProveedorService {

    //Servicio para crear un nuevo Proveedor
    ProveedorResponseDTO createProveedor(ProveedorRequestDTO proveedorRequestDTO);

    //Servicio para listar a todos los Proveedores
    ProveedorPageResponseDTO getAllProveedores(Pageable pageable);

    // Servicio para listar a todos los Proveedores (nueva version)
    ProveedorPageListarDTO getAllProveedoresNew(Pageable pageable);

    //Servicio para obtener un Proveedor por ID
    ProveedorResponseDTO getProveedorById(Integer id);

    //Servicio para actualizar el Proveedor
    ProveedorResponseDTO updateProveedor(Integer id, ProveedorRequestDTO proveedorRequestDTO);

    // Servicio para listar representantes de un proveedor
    List<RepresentanteResponseDTO> getRepresentantesByProveedorId(Integer idProveedor);

    // Servicio para buscar un proveedor por filtros multiples
    ProveedorPageListarDTO buscarProveedorConFiltros(ProveedorFiltroDTO filtro, Pageable pageable);

    //Servicio para cambiar el nombre del proveedor
    ProveedorResponseDTO cambiarNombreProveedor(ProveedorCambiarNombreDTO nuevoNombre);
}