package com.warungposbespring.warungposbe.controller;


import com.warungposbespring.warungposbe.service.WarungService;
import com.warungposbespring.warungposbe.utils.HttpResponse;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/warung")
@CrossOrigin(origins = "*", maxAge = 3600)
public class WarungController {

    private final WarungService warung;

    public WarungController(WarungService warung) {
        this.warung = warung;
    }

    @GetMapping("/findAllWarung")
    public ResponseEntity<?> findAllWarung(
            @Parameter(name = "page", example = "1", required = false) @RequestParam String page,
            @Parameter(name = "size", example = "10", required = false) @RequestParam String size,
            @Parameter(name = "sort", example = "createdAt, desc", required = true) @RequestParam(defaultValue = "desc") String sort
    ){
        return HttpResponse.generateResponse(
                "success",
                HttpStatus.OK,
                warung.findAllWarung(page, size, sort),
                true
        );
    }
}
