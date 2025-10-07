package com.example.sistema_inventario_back.controller.proveedor;

import com.example.sistema_inventario_back.dto.proveedor.ciudad.CiudadListarDTO;
import com.example.sistema_inventario_back.dto.proveedor.ciudad.CiudadRequestDTO;
import com.example.sistema_inventario_back.dto.proveedor.ciudad.CiudadPageResponseDTO;
import com.example.sistema_inventario_back.dto.proveedor.ciudad.CiudadResponseDTO;
import com.example.sistema_inventario_back.service.proveedor.proveedor_interface.CiudadService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@AllArgsConstructor
@RequestMapping("/api/ciudad")
public class CiudadController {

    @Autowired
    private CiudadService ciudadService;

    //Controlador para crear una nueva Ciudad
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/crear_ciudad")
    public ResponseEntity<?> createCiudadController(@RequestBody CiudadRequestDTO ciudadRequestDTO){
        try{
            CiudadResponseDTO ciudadCreada = ciudadService.createCiudad(ciudadRequestDTO);
            return ResponseEntity.ok(ciudadCreada);
        }catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //Controlador para listar a todas las ciudades sin pagina
    @GetMapping("/listar/listar_ciudades")
    public ResponseEntity<List<CiudadListarDTO>> getAllCiudadesSinPageController(){
        return ResponseEntity.ok(ciudadService.getAllCiudadesNew());
    }

    //Controlador para listar a todas las ciudades
    @GetMapping("/listar_ciudades")
    public ResponseEntity<?> getAllCiudadesController(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "idCiudad") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir
    ){
        try{
            Sort sort = sortDir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
            Pageable pageable = PageRequest.of(page, size, sort);

            CiudadPageResponseDTO responseDTO = ciudadService.getAllCiudadesPage(pageable);
            return ResponseEntity.ok(responseDTO);

        }catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //Controlador para llamar una ciudad segun el id
    @GetMapping("/buscar/{id}")
    public ResponseEntity<CiudadResponseDTO> getCiudadById(@PathVariable Integer id){
        CiudadResponseDTO ciudadResponseDTO = ciudadService.getCiudadById(id);
        return ResponseEntity.ok(ciudadResponseDTO);
    }

    //Controlador para actualizar la ciudad
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<CiudadResponseDTO> updateCiudad(@PathVariable Integer id, @RequestBody CiudadRequestDTO ciudadRequestDTO){
        CiudadResponseDTO ciudad = ciudadService.updateCiudad(id, ciudadRequestDTO);
        return ResponseEntity.ok(ciudad);
    }
}
