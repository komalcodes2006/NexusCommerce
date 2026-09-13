package com.nexuscommerce.controller;

import com.nexuscommerce.dto.AddressRequest;
import com.nexuscommerce.dto.AddressResponse;
import com.nexuscommerce.service.AddressService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users/me/addresses")
public class AddressController {

    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @PostMapping
    public ResponseEntity<AddressResponse> addAddress(@Valid @RequestBody AddressRequest request) {
        AddressResponse response = addressService.addAddress(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<AddressResponse>> getUserAddresses() {
        List<AddressResponse> responses = addressService.getUserAddresses();
        return ResponseEntity.ok(responses);
    }
}