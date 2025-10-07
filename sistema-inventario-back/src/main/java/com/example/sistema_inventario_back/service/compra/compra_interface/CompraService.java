package com.example.sistema_inventario_back.service.compra.compra_interface;

import com.example.sistema_inventario_back.dto.compra.CompraPageListarDTO;
import com.example.sistema_inventario_back.dto.compra.CompraRequestDTO;
import com.example.sistema_inventario_back.dto.compra.CompraResponseDTO;
import com.example.sistema_inventario_back.dto.compra.CompraUpdateDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CompraService {

    // Servicio para listar compras realizadas por un proveedor especifico
    List<CompraResponseDTO> getAllComprasByProveedor(Integer idProveedor);

    // Servicio para comprar materia prima
    CompraResponseDTO createCompra(CompraRequestDTO compraRequestDTO, String username);

    // Servicio para listar todas las compras realizadas de una materia prima
    List<CompraResponseDTO> getAllComprasByMateriaPrima(Integer idMateriaPrima);

    // Servicio para actualizar la compra realizada
    CompraResponseDTO updateCompra(CompraUpdateDTO dto);

    // Servicio para listar todas las compras de manera paginada
    CompraPageListarDTO getAllCompras(Pageable pageable);
}