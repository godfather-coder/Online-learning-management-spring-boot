package com.example.user;


import com.example.utiles.APIResponse.APIResponse;
import com.example.utiles.DTO.user.UserCreationDTO;
import com.example.utiles.DTO.user.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("api/v1/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<APIResponse<List<UserDTO>>> getUsers() throws Exception {
        APIResponse<List<UserDTO>> response = userService.getAllUser();
        return ResponseEntity
                .status(HttpStatus.valueOf(response.getHttpStatus()))
                .body(response);
    }

    @PostMapping
    public ResponseEntity<APIResponse<UserDTO>> addUser(@RequestBody UserCreationDTO userCreationDTO) {
        APIResponse<UserDTO> response = userService.addUser(userCreationDTO);
        return ResponseEntity
                .status(HttpStatus.valueOf(response.getHttpStatus()))
                .body(response);
    }

    @PutMapping(path ="{id}")
    public ResponseEntity<APIResponse<UserDTO>> updateUser(
            @RequestBody UserCreationDTO userCreationDTO,
            @PathVariable("id") Long id)
    {

        APIResponse<UserDTO> response = userService.updateUser(userCreationDTO,id);

        return ResponseEntity
                .status(HttpStatus.valueOf(response.getHttpStatus()))
                .body(response);
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<APIResponse<UserDTO>> deleteUser(@PathVariable("id") Long id){

        APIResponse<UserDTO> response = userService.deleteUser(id);

        return ResponseEntity
                .status(HttpStatus.valueOf(response.getHttpStatus()))
                .body(response);
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<APIResponse<UserDTO>> getUser(@PathVariable("id") Long id) {

        APIResponse<UserDTO> response = userService.getUser(id);

        return ResponseEntity
                .status(HttpStatus.valueOf(response.getHttpStatus()))
                .body(response);
    }

}
