package com.example.sistema_inventario_back.dto.proveedor.pais;

import com.example.sistema_inventario_back.dto.proveedor.ciudad.CiudadResponseDTO;
import lombok.Data;

import java.util.List;

@Data
public class PaisResponseDTO {
    private Integer idPais;
    private String nombrePais;
    private List<CiudadResponseDTO> ciudades;
}