package com.example.sistema_inventario_back.dto.materia_prima;
import lombok.Data;

@Data
public class MateriaPrimaResponseDTO {
    private Integer idMateriaPrima;
    private String nombreMateriaPrima;
    private Double stockActual;
    private String descripcion;
    private String usuario;
    private String proveedor;
    private Integer idProveedor;
}