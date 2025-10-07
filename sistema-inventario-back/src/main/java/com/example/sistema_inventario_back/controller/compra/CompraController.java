package com.example.sistema_inventario_back.controller.compra;

import com.example.sistema_inventario_back.dto.compra.CompraPageListarDTO;
import com.example.sistema_inventario_back.dto.compra.CompraRequestDTO;
import com.example.sistema_inventario_back.dto.compra.CompraResponseDTO;
import com.example.sistema_inventario_back.service.compra.compra_interface.CompraService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin
@AllArgsConstructor
@RequestMapping("/api/compra")
public class CompraController {

    @Autowired
    private final CompraService compraService;

    // Controlador para crear una compra
    @PostMapping("/compraMateriaPrima")
    public ResponseEntity<CompraResponseDTO> createCompraController(
            @RequestBody CompraRequestDTO compraRequestDTO,
            Principal principal){

        String username = principal.getName();
        CompraResponseDTO response = compraService.createCompra(compraRequestDTO, username);

        return ResponseEntity.ok(response);
    }

    // Controlador para listar compras realizadas de un proveedor
    @GetMapping("/listarComprasProveedor/{idProveedor}")
    public ResponseEntity<List<CompraResponseDTO>> getAllComprasByProveedorController(@PathVariable Integer idProveedor){
        return ResponseEntity.ok(compraService.getAllComprasByProveedor(idProveedor));
    }

    // Controlador para listar las compras de una materia prima
    @GetMapping("/listarComprasMateriaPrima/{idMateriaPrima}")
    public ResponseEntity<List<CompraResponseDTO>> getAllComprasByMateriaPrimaController(@PathVariable Integer idMateriaPrima){
        return ResponseEntity.ok(compraService.getAllComprasByMateriaPrima(idMateriaPrima));
    }

    // Controlador para listar a todas las compras
    @GetMapping("/listarCompras")
    public ResponseEntity<CompraPageListarDTO> listarComprasController(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "idCompra") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir){

        // Validacion de parametros
        int paginaActual = Math.max(page, 0);
        int tamanioPagina = Math.min(Math.max(size, 1), 100);

        // Construccion de ordenamiento
        Sort sort = sortDir.equalsIgnoreCase("asc") ?
                Sort.by(sortBy).ascending() :
                Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(paginaActual, tamanioPagina, sort);

        CompraPageListarDTO response = compraService.getAllCompras(pageable);

        return ResponseEntity.ok(response);
    }
}