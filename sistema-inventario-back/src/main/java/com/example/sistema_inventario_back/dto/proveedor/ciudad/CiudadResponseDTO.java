package com.example.sistema_inventario_back.dto.proveedor.ciudad;

import com.example.sistema_inventario_back.dto.proveedor.proveedor.ProveedorResponseDTO;
import lombok.Data;

import java.util.List;

@Data
public class CiudadResponseDTO {
    private Integer idCiudad;
    private String nombreCiudad;
    private Integer idPais;
    private String nombrePais;
    private List<ProveedorResponseDTO> proveedor;
}