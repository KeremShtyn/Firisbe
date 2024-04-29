package com.example.firisbe.authentication.api;


import com.example.firisbe.authentication.service.dto.TokenRequestDTO;
import com.example.firisbe.authentication.service.dto.UserDTO;
import com.example.firisbe.util.response.FirisbeResponse;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static com.example.firisbe.util.api.FirisbeAPIEnpoint.*;

@CrossOrigin(origins = "*")
@RequestMapping(AUTH_PATH)
public interface AuthAPI {

    @PostMapping(CREATE_TOKEN)
    public FirisbeResponse<Object> token(@RequestBody TokenRequestDTO request);

    @PostMapping(CREATE_USER)
    public FirisbeResponse<Object> saveUser(@RequestBody UserDTO userDTO);

    @PutMapping(CREATE_USER)
    public FirisbeResponse<Object> update(@RequestBody UserDTO userDTO);

    @GetMapping("/getByUsername/{username}")
    public FirisbeResponse<Object> getByUsername(@PathVariable("username") String username);

    @GetMapping("/users")
    public FirisbeResponse<Object> getPage(@RequestHeader Map<String, Object> header, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(value = "sort", defaultValue = "modifyDate:DESC", required = false) String[] sortBy);



}
