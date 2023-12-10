package com.example.role;

import com.example.utiles.APIResponse.APIResponse;
import com.example.utiles.DTO.role.RoleCreationDTO;
import com.example.utiles.DTO.role.RoleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/role")
public class RoleController {
    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    public ResponseEntity<APIResponse<List<RoleDTO>>> getAllRoles(){
        APIResponse<List<RoleDTO>> response = roleService.getRoles();
        return ResponseEntity
                .status(HttpStatus.valueOf(response.getHttpStatus()))
                .body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<RoleDTO>> getRoleById(@PathVariable Long id){
        APIResponse<RoleDTO> response = roleService.findRoleById(id);
        return ResponseEntity
                .status(HttpStatus.valueOf(response.getHttpStatus()))
                .body(response);
    }

    @PostMapping
    public ResponseEntity<APIResponse<RoleDTO>> addRole(@RequestBody RoleCreationDTO roleCreationDTO){
        APIResponse<RoleDTO> response = roleService.addRole(roleCreationDTO);
        return ResponseEntity
                .status(HttpStatus.valueOf(response.getHttpStatus()))
                .body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<APIResponse<RoleDTO>> updateRole(@RequestBody RoleCreationDTO roleCreationDTO
                                                           ,@PathVariable Long id){
        APIResponse<RoleDTO> response = roleService.updateRole(roleCreationDTO,id);
        return  ResponseEntity
                .status(HttpStatus.valueOf(response.getHttpStatus()))
                .body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse<RoleDTO>> deleteRole(@PathVariable Long id){
        APIResponse<RoleDTO> response = roleService.deleteUserById(id);
        return ResponseEntity
                .status(HttpStatus.valueOf(response.getHttpStatus()))
                .body(response);
    }

}
