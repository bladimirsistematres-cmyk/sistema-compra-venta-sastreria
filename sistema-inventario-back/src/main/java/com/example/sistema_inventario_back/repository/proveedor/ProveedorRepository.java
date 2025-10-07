package com.example.sistema_inventario_back.repository.proveedor;

import com.example.sistema_inventario_back.dto.proveedor.proveedor.ProveedorListarDTO;
import com.example.sistema_inventario_back.entity.proveedor.Proveedor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProveedorRepository extends JpaRepository<Proveedor, Integer>, JpaSpecificationExecutor<Proveedor> {

    @Query("""
            SELECT new com.example.sistema_inventario_back.dto.proveedor.proveedor.ProveedorListarDTO(
                p.idProveedor,
                p.nombreComercial,
                p.identificacionFiscal,
                p.fechaCreacion,
                p.tipoProveedor,
                SIZE(p.representantes) > 0
            )
            FROM Proveedor p
    """)
    Page<ProveedorListarDTO> findAllResumen(Pageable pageable);

    @Query("""
    SELECT new com.example.sistema_inventario_back.dto.proveedor.proveedor.ProveedorListarDTO(
        p.idProveedor,
        p.nombreComercial,
        p.identificacionFiscal,
        p.fechaCreacion,
        p.tipoProveedor,
        SIZE(p.representantes) > 0
    )
    FROM Proveedor p
    WHERE
        (:nombreComercial IS NULL OR p.nombreComercial ILIKE :nombreComercial)
        AND (:identificacionFiscal IS NULL OR p.identificacionFiscal ILIKE :identificacionFiscal)
""")
    Page<ProveedorListarDTO> findAllProveedoresConFiltros(
            @Param("nombreComercial") String nombreComercial,
            @Param("identificacionFiscal") String identificacionFiscal,
            Pageable pageable
    );
}