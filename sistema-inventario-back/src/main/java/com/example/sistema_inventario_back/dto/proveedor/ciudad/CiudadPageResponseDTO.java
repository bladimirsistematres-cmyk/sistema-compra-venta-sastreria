package com.example.sistema_inventario_back.dto.proveedor.ciudad;

import lombok.Data;

import java.util.List;

@Data
public class CiudadPageResponseDTO {
    private List<CiudadResponseDTO> ciudades;
    private int paginaActual;
    private int totalPaginas;
    private long totalEmentos;
    private int tamanioPagina;
}