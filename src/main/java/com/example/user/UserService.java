package com.example.user;

import com.example.role.Role;
import com.example.role.RoleRepository;
import com.example.utiles.APIResponse.APIResponse;
import com.example.utiles.DTO.user.UserCreationDTO;
import com.example.utiles.DTO.user.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }


    public APIResponse<List<UserDTO>> getAllUser(){
        List<User> lst = userRepository.findAll();
        List<UserDTO> userDTOList = lst
                .stream()
                .map(this::userToDTO
                ).toList();
        return APIResponse.ok(userDTOList, Map.of("Success","Operation was successfully"),"Success");
    }
    public APIResponse<UserDTO> addUser(UserCreationDTO user){

        Optional<User> userOptional = userRepository.findUserByEmail(user.getEmail());

        Optional<Role> roleOptional = roleRepository.findRoleById(user.getRoleId());

        // check role if exist
        if(roleOptional.isEmpty()){
            return APIResponse.notFound(null,Map.of("Failed","Role does not exist"),"Failed");
        }

        // check if email is already taken
        if(userOptional.isPresent()){
            return APIResponse.notFound(null,Map.of("Failed","Email is already taken"),"Failed");
        }

        User usr = User.builder()
                .name(user.getName())
                .email(user.getEmail())
                .password(user.getPassword())
                .role(roleOptional.get())
                .build();

        User user1 = userRepository.save(usr);

        UserDTO userDTO = UserDTO.builder()
                .id(user1.getId())
                .name(user1.getName())
                .email(user1.getEmail())
                .password(user1.getPassword())
                .role(user1.getRole())
                .build();

        return APIResponse.ok(
                userDTO,
                Map.of("Success","Operation was successfully"),
                "Success");
    }

    public APIResponse<UserDTO> updateUser(UserCreationDTO user, Long id) {

        Optional<User> user1 = userRepository.findUserById(id);

        Optional<Role> roleOptional = roleRepository.findRoleById(user.getRoleId());

        // check role if exist
        if(roleOptional.isEmpty()){
            return APIResponse.notFound(null,Map.of("Failed","Role does not exist"),"Failed");
        }

        if(user1.isEmpty()){
            return APIResponse.notFound(null,Map.of("Failed","Email is already taken"),"Failed");
        }

        User userUpdate = user1.get();

        userUpdate.setEmail(user.getEmail());
        userUpdate.setName(user.getName());
        userUpdate.setPassword(user.getPassword());
        userUpdate.setRole(roleOptional.get());

        UserDTO userDTO = userToDTO(userRepository.save(userUpdate));

        return APIResponse.ok(userDTO, Map.of("Success","Operation was successfully"),"Success");

    }
    public APIResponse<UserDTO> deleteUser(Long id){
        Optional<User> user1 = userRepository.findUserById(id);
        if(user1.isEmpty()){
            return APIResponse.notFound(null,Map.of(
                    "Failed"
                    ,"User such id does not exist")
                    ,"Failed");
        }
        userRepository.delete(user1.get());
        return APIResponse.ok(
                null
                , Map.of("Success","Operation was successfully")
                ,"Success");

    }
    public APIResponse<UserDTO> getUser(Long id)  {
        Optional<User> user1 = userRepository.findUserById(id);
        if(user1.isEmpty()){
            return APIResponse.notFound(null,Map.of(
                            "Failed"
                            ,"User such id does not exist")
                    ,"Failed");
        }

        UserDTO userDTO = userToDTO( user1.get() );

        return APIResponse.ok(userDTO, Map.of("Success","Operation was successfully"),"Success");
    }

    private UserDTO userToDTO(User user){
        return UserDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .password(user.getPassword())
                .role(user.getRole())
                .build();
    }

}
