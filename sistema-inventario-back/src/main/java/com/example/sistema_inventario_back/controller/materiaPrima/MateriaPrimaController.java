package com.example.sistema_inventario_back.controller.materiaPrima;

import com.example.sistema_inventario_back.dto.materia_prima.MateriaPrimaPageListarDTO;
import com.example.sistema_inventario_back.dto.materia_prima.MateriaPrimaRequestDTO;
import com.example.sistema_inventario_back.dto.materia_prima.MateriaPrimaResponseDTO;
import com.example.sistema_inventario_back.service.materia_prima.materia_prima_interface.MateriaPrimaService;
import lombok.AllArgsConstructor;
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
@RequestMapping("/api/materiaPrima")
public class MateriaPrimaController {

    private final MateriaPrimaService materiaPrimaService;

    // Controlador para crear una nueva materia prima
    @PostMapping("/crearMateriaPrima")
    public ResponseEntity<MateriaPrimaResponseDTO> createMateriaPrimaController(
            @RequestBody MateriaPrimaRequestDTO dto,
            Principal principal) {

        String username = principal.getName();
        MateriaPrimaResponseDTO response = materiaPrimaService.createMateriaPrima(dto, username);

        return ResponseEntity.ok(response);
    }

    // Controlador para listar materias primas con paginacion
    @GetMapping("/listarMateriaPrima")
    public ResponseEntity<MateriaPrimaPageListarDTO> listMateriaPrimaController(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "idMateriaPrima") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir){

        // Validacion de parametros
        int paginaActual = Math.max(page, 0);
        int tamanioPagina = Math.min(Math.max(size, 1), 100);

        // Construccion de ordenamiento
        Sort sort = sortDir.equalsIgnoreCase("asc") ?
                Sort.by(sortBy).ascending() :
                Sort.by(sortBy).descending();

        // Paginacion con validacion incluida
        Pageable pageable = PageRequest.of(paginaActual, tamanioPagina, sort);

        MateriaPrimaPageListarDTO response = materiaPrimaService.getAllMateriaPrima(pageable);
        return ResponseEntity.ok(response);
    }

    // Controlador para listar a materia prima segun el id del proveedor
    @GetMapping("/listarMateriaPrima/{idProveedor}")
    public ResponseEntity<List<MateriaPrimaResponseDTO>> findByProveedorIdProveedorController(@PathVariable Integer idProveedor){
        return ResponseEntity.ok(materiaPrimaService.findByProveedorIdProveedor(idProveedor));
    }
}