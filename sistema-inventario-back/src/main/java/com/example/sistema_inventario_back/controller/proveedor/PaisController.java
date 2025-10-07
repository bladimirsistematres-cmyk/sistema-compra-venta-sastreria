package com.example.sistema_inventario_back.controller.proveedor;

import com.example.sistema_inventario_back.dto.proveedor.pais.PaisDTO;
import com.example.sistema_inventario_back.dto.proveedor.pais.PaisListarDTO;
import com.example.sistema_inventario_back.dto.proveedor.pais.PaisResponseDTO;
import com.example.sistema_inventario_back.entity.proveedor.Pais;
import com.example.sistema_inventario_back.service.proveedor.proveedor_interface.PaisService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@AllArgsConstructor
@RequestMapping("/api/pais")
public class PaisController {

    @Autowired
    private PaisService paisService;

    //Controlador para listar paises con sus ciudades
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/listar_paises")
    public ResponseEntity<List<PaisListarDTO>> getAllPaises(){
        return ResponseEntity.ok(paisService.getAllPaises());
    }

    //Controlador para crear un nuevo pais
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/crear_pais")
    public ResponseEntity<?> createPaisController(@RequestBody PaisDTO paisDTO){
        try{
            Pais paisCreada = paisService.createPais(paisDTO);
            return ResponseEntity.ok(paisCreada);
        }catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //Controlador para actualizar el pais
    @PutMapping("/actualizar_pais/{id}")
    public ResponseEntity<?> actualizarPaisController(
            @PathVariable Integer id,
            @RequestBody PaisDTO paisDTO
    ){
        PaisResponseDTO pais = paisService.updatePais(id, paisDTO);
        return ResponseEntity.ok(pais);
    }

    //Controlador para llamar un pais por el id
    @GetMapping("/buscar_pais/{id}")
    public ResponseEntity<PaisResponseDTO> getPaisById(@PathVariable Integer id){
        PaisResponseDTO paisResponseDTO = paisService.getPaisById(id);
        return ResponseEntity.ok(paisResponseDTO);
    }
}