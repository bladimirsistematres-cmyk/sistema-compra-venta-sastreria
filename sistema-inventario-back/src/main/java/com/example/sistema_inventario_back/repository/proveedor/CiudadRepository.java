package com.example.sistema_inventario_back.repository.proveedor;

import com.example.sistema_inventario_back.dto.proveedor.ciudad.CiudadListarDTO;
import com.example.sistema_inventario_back.entity.proveedor.Ciudad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CiudadRepository extends JpaRepository<Ciudad, Integer> {

    @Query("SELECT new com.example.sistema_inventario_back.dto.proveedor.ciudad.CiudadListarDTO(c.idCiudad, c.nombreCiudad) FROM Ciudad c")
    List<CiudadListarDTO> findAllListar();
}