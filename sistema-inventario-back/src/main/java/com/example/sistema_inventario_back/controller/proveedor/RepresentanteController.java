package com.example.sistema_inventario_back.controller.proveedor;

import com.example.sistema_inventario_back.dto.proveedor.representante.RepresentanteListarDTO;
import com.example.sistema_inventario_back.dto.proveedor.representante.RepresentantePageResponseDTO;
import com.example.sistema_inventario_back.dto.proveedor.representante.RepresentanteRequestDTO;
import com.example.sistema_inventario_back.dto.proveedor.representante.RepresentanteResponseDTO;
import com.example.sistema_inventario_back.service.proveedor.proveedor_interface.RepresentanteService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@AllArgsConstructor
@RequestMapping("/api/representante")
public class RepresentanteController {

    @Autowired
    private RepresentanteService representanteService;

    //Controlador para crear un nuevo Representante
    @PostMapping("/crear_representante")
    public ResponseEntity<RepresentanteResponseDTO> createRepresentanteController(
            @Valid @RequestBody RepresentanteRequestDTO representanteRequestDTO
            ){
        RepresentanteResponseDTO responseDTO = representanteService.createRepresentante(representanteRequestDTO);
        return ResponseEntity.ok(responseDTO);
    }

    //Controlador para listar a todos los representantes
    @GetMapping("/listar_representantes")
    public RepresentantePageResponseDTO getAllRepresentantesController(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "4") int size,
            @RequestParam(defaultValue = "idRepresentante") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir){

        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        return representanteService.getAllRepresentantes(pageable);
    }

    //Controlador para buscar un Representante por id
    @GetMapping("/buscar_representante/{id}")
    public ResponseEntity<RepresentanteResponseDTO> getRepresentanteByIdController(@PathVariable Integer id){
        RepresentanteResponseDTO response = representanteService.getRepresentanteById(id);
        return ResponseEntity.ok(response);
    }

    //Controlador para actualizar el representante por id
    @PutMapping("/actualizar_representante/{id}")
    public ResponseEntity<RepresentanteResponseDTO> updateRepresentanteController(
            @PathVariable Integer id,
            @Valid @RequestBody RepresentanteRequestDTO representanteRequestDTO){

        RepresentanteResponseDTO responseDTO = representanteService.updateRepresentante(id, representanteRequestDTO);
        return ResponseEntity.ok(responseDTO);
    }

    //Controlador para buscar por cualquier campo
    @GetMapping("/filtrar_representante")
    public ResponseEntity<RepresentantePageResponseDTO> filtrarPorCampoRepresentanteController(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) String cargo,
            @RequestParam(required = false) String cedulaIdentidad,
            @RequestParam(required = false) String correoElectronico,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "idRepresentante") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir
    ){
        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        RepresentantePageResponseDTO result = representanteService
                .buscarRepresentantesConFiltros(nombre, cargo, cedulaIdentidad, correoElectronico, pageable);

        return ResponseEntity.ok(result);
    }

    // Controlador para listar a todos los represenantes segun el del proveedor
    @GetMapping("/listar_representantes/{idProveedor}")
    public ResponseEntity<List<RepresentanteListarDTO>> getAllRepresentantesByIdProveedorController(@PathVariable Integer idProveedor){
        return ResponseEntity.ok(representanteService.getAllRepresentantesByIdProveedor(idProveedor));
    }
}