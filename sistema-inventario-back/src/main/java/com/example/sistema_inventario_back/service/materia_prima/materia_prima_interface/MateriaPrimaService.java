package com.example.sistema_inventario_back.service.materia_prima.materia_prima_interface;

import com.example.sistema_inventario_back.dto.materia_prima.MateriaPrimaPageListarDTO;
import com.example.sistema_inventario_back.dto.materia_prima.MateriaPrimaRequestDTO;
import com.example.sistema_inventario_back.dto.materia_prima.MateriaPrimaResponseDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MateriaPrimaService {
    MateriaPrimaResponseDTO createMateriaPrima(MateriaPrimaRequestDTO materiaPrimaRequestDTO, String username);

    MateriaPrimaPageListarDTO getAllMateriaPrima(Pageable pageable);

    List<MateriaPrimaResponseDTO> findByProveedorIdProveedor(Integer idProveedor);
}