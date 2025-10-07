package com.example.sistema_inventario_back.dto.proveedor.proveedor;

import com.example.sistema_inventario_back.entity.proveedor.TipoProveedor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProveedorListarDTO {
    private Integer idProveedor;
    private String nombreComercial;
    private String identificacionFiscal;
    private LocalDateTime fechaCreacion;
    private TipoProveedor tipoProveedor;
    private boolean tieneRepresentantes;
}