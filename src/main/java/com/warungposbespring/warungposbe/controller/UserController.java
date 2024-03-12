package com.warungposbespring.warungposbe.controller;

import com.warungposbespring.warungposbe.dto.RegistrasiDtoRequest;
import com.warungposbespring.warungposbe.dto.UserForJwtResponse;
import com.warungposbespring.warungposbe.service.UserService;
import com.warungposbespring.warungposbe.utils.HttpResponse;
import com.warungposbespring.warungposbe.utils.SecurityUtil;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@CrossOrigin("*")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/findAllUser")
    public ResponseEntity<?> findAllUser(
            @Parameter(name = "page", example = "1", required = false) @RequestParam String page,
            @Parameter(name = "size", example = "10", required = false) @RequestParam String size,
            @Parameter(name = "sort", example = "createdAt, desc", required = true) @RequestParam(defaultValue = "desc") String sort
    ){
        return HttpResponse.generateResponse(
                "success",
                HttpStatus.OK,
                userService.findAllUser(page, size, sort),
                true
        );
    }

    @GetMapping("/findByOwner")
    public ResponseEntity<?> findByOwner(
            @Parameter(name = "page", example = "1", required = false) @RequestParam String page,
            @Parameter(name = "size", example = "10", required = false) @RequestParam String size,
            @Parameter(name = "sort", example = "createdAt, desc", required = true) @RequestParam(defaultValue = "desc") String sort
    ){

        UserForJwtResponse user = SecurityUtil.getAuthUser();

        return HttpResponse.generateResponse(
                "success",
                HttpStatus.OK,
                userService.findByOwner( user.getWarung_pos_identity(),page, size, sort),
                true
        );
    }

    @PostMapping("/createNewUserByOwner")
    public ResponseEntity<?> createNewUserByOwner(@RequestBody RegistrasiDtoRequest request){
        UserForJwtResponse user = SecurityUtil.getAuthUser();

        userService.createNewUserByOwner(request, user.getWarung_pos_identity());

        return HttpResponse.generateResponse(
                "Berhasil",
                HttpStatus.OK,
                "Berhasil",
                true
        );
    }

    @PostMapping("/registrasi")
    public ResponseEntity<?> registrasi(@RequestBody RegistrasiDtoRequest request){

        userService.registrasi(request);

        return HttpResponse.generateResponse(
                "Harap menunggu konfirmasi dari admin via email",
                HttpStatus.OK,
                "Pendaftaran Berhasil",
                true
        );
    }
}
