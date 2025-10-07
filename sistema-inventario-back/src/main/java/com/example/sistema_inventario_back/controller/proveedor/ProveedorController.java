package com.example.sistema_inventario_back.controller.proveedor;

import com.example.sistema_inventario_back.dto.proveedor.proveedor.*;
import com.example.sistema_inventario_back.dto.proveedor.representante.RepresentanteResponseDTO;
import com.example.sistema_inventario_back.pdfexporter.ProveedorPdfExporter;
import com.example.sistema_inventario_back.service.proveedor.proveedor_interface.ProveedorService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin
@AllArgsConstructor
@RequestMapping("/api/proveedor")
public class ProveedorController {

    @Autowired
    private final ProveedorService proveedorService;

    // Controlador para buscar un Proveedor
    @GetMapping("/buscar_proveedor")
    public ResponseEntity<ProveedorPageListarDTO> filtrarProveedorController(
            @RequestParam(required = false) String nombreComercial,
            @RequestParam(required = false) String identificacionFiscal,
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "10") int tamanio
    ){
        ProveedorFiltroDTO filtro = new ProveedorFiltroDTO();
        filtro.setNombreComercial(nombreComercial);
        filtro.setIdentificacionFiscal(identificacionFiscal);

        Pageable pageable = PageRequest.of(pagina, tamanio);
        ProveedorPageListarDTO result = proveedorService.buscarProveedorConFiltros(filtro, pageable);

        return ResponseEntity.ok(result);
    }

    //Controlador para crear un nuevo Proveedor
    @PostMapping("/crear_proveedor")
    public ResponseEntity<ProveedorResponseDTO> createProveedorController(
            @Valid @RequestBody ProveedorRequestDTO proveedorRequestDTO
            ){
        ProveedorResponseDTO responseDTO = proveedorService.createProveedor(proveedorRequestDTO);
        return ResponseEntity.ok(responseDTO);
    }

    // Controlador para listar a todos los Proveedores (nueva version)
    @GetMapping("/listar_proveedores/nueva")
    public ResponseEntity<ProveedorPageListarDTO> getAllProveedoresNewVersionController(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "idProveedor") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir
    ){

        // Validacion de parametros
        int paginaActual = Math.max(page, 0);
        int tamanioPagina = Math.min(Math.max(size, 1), 100);

        // Construccion de ordenamiento
        Sort sort = sortDir.equalsIgnoreCase("asc") ?
                Sort.by(sortBy).ascending() :
                Sort.by(sortBy).descending();

        // Paginacion con validacion incluida
        Pageable pageable = PageRequest.of(paginaActual, tamanioPagina, sort);

        ProveedorPageListarDTO response = proveedorService.getAllProveedoresNew(pageable);
        return ResponseEntity.ok(response);
    }

    //Controlador para listar a todos los Proveedores (antigua version)
    @GetMapping("/listar_proveedores")
    public ProveedorPageResponseDTO getAllProveedoresController(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "idProveedor") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir
    ){
        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        return proveedorService.getAllProveedores(pageable);
    }

    //Controlador para buscar un proveedor por id
    @GetMapping("/buscar_proveedor/{id}")
    public ResponseEntity<ProveedorResponseDTO> getProveedorByIdController(
            @PathVariable Integer id
    ){
        ProveedorResponseDTO responseDTO = proveedorService.getProveedorById(id);
        return ResponseEntity.ok(responseDTO);
    }

    //Controlador para actualizar el proveedor
    @PutMapping("/actualizar_proveedor/{id}")
    public ResponseEntity<ProveedorResponseDTO> getUpdateProveedorController(
            @PathVariable Integer id,
            @Valid @RequestBody ProveedorRequestDTO proveedorRequestDTO
    ){
        ProveedorResponseDTO proveedor = proveedorService.updateProveedor(id, proveedorRequestDTO);
        return ResponseEntity.ok(proveedor);
    }

    //Controlador para exportar pdf
    @GetMapping("/exportar/pdf/{id}")
    public void exportarProveedorPorId(
            @PathVariable Integer id,
            HttpServletResponse response
    ) throws IOException {
        ProveedorResponseDTO proveedor = proveedorService.getProveedorById(id);
        ProveedorPdfExporter exporter = new ProveedorPdfExporter();

        byte[] pdfBytes = exporter.exportToPDF(proveedor);

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=proveedor_" + id + ".pdf");
        response.getOutputStream().write(pdfBytes);
        response.getOutputStream().flush();
    }

    @GetMapping("/representantes/{idProveedor}")
    public ResponseEntity<List<RepresentanteResponseDTO>> getRepresentantesByProveedorIdController(
            @PathVariable Integer idProveedor
    ){
        List<RepresentanteResponseDTO> representantes = proveedorService.getRepresentantesByProveedorId(idProveedor);
        return ResponseEntity.ok(representantes);
    }

    @PutMapping("/cambiar_nombre")
    public ResponseEntity<ProveedorResponseDTO> cambiarNombreProveedorController(@Valid @RequestBody ProveedorCambiarNombreDTO nuevoNombre){
        ProveedorResponseDTO proveedorActualizado = proveedorService.cambiarNombreProveedor(nuevoNombre);
        return ResponseEntity.ok(proveedorActualizado);
    }
}