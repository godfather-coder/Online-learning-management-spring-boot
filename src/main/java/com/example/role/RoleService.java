package com.example.role;

import com.example.user.User;
import com.example.user.UserRepository;
import com.example.utiles.APIResponse.APIResponse;
import com.example.utiles.DTO.role.RoleCreationDTO;
import com.example.utiles.DTO.role.RoleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public APIResponse<List<RoleDTO>> getRoles() {
        List<RoleDTO> roles = roleRepository
                .findAll()
                .stream()
                .map(this::roleToDTO)
                .toList();
        return APIResponse.ok(roles, Map.of("Success","Operation was successfully"),"Success");
    }

    public APIResponse<RoleDTO> findRoleById(Long id){
        Optional<Role> role = roleRepository.findById(id);
        if(role.isEmpty()){
            return APIResponse.ok(null, Map.of("Failed"
                    ,"Role such id does not found")
                    ,"Failed");
        }
        return APIResponse.ok(
                roleToDTO(role.get())
                , Map.of("Success","Operation was successfully")
                ,"Success");
    }

    public APIResponse<RoleDTO> addRole(RoleCreationDTO roleCreationDTO){
        Optional<Role> roleOptional = roleRepository.findRoleByName(roleCreationDTO.getName());
        if(roleOptional.isPresent()){
            return APIResponse.ok(null, Map.of("Failed"
                            ,"Role such name already not found")
                    ,"Failed");
        }
        return APIResponse.ok(
                roleToDTO(roleRepository
                        .save(
                                new Role(roleCreationDTO.getName())
                        )
                )
                , Map.of("Success","Operation was successfully")
                ,"Success");
    }

    public APIResponse<RoleDTO> updateRole(RoleCreationDTO roleCreationDTO,Long id){
        Optional<Role> optionalRole = roleRepository.findRoleById(id);
        if(optionalRole.isEmpty()){
            return APIResponse.ok(null, Map.of("Failed"
                            ,"Role such id does not found")
                    ,"Failed");
        }
        Role updateRole = optionalRole.get();
        updateRole.setName(roleCreationDTO.getName());

        return APIResponse.ok(
                roleToDTO( roleRepository.save( updateRole ) )
                , Map.of("Success","Operation was successfully")
                ,"Success");
    }

    public APIResponse<RoleDTO> deleteUserById(Long id){
        Optional<Role> optionalRole = roleRepository.findRoleById(id);
        if(optionalRole.isEmpty()){
            return APIResponse.ok(null
                            , Map.of("Failed"
                            ,"Role such id does not found")
                            ,"Failed");
        }
        roleRepository.deleteById(id);

            return APIResponse.ok(null
                    ,Map.of("Success","Operation was successfully")
                    ,"Success");

    }



    private RoleDTO roleToDTO(Role role){
        return RoleDTO.builder()
                .name(role.getName())
                .id(role.getId())
                .build();
    }
}
