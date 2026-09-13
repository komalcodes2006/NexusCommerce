package com.nexuscommerce.service;

import com.nexuscommerce.dto.AddressRequest;
import com.nexuscommerce.dto.AddressResponse;
import com.nexuscommerce.entity.Address;
import com.nexuscommerce.entity.User;
import com.nexuscommerce.repository.AddressRepository;
import com.nexuscommerce.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AddressService {

    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    public AddressService(AddressRepository addressRepository, UserRepository userRepository) {
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
    }

    // Helper method to get the currently authenticated user
    private User getAuthenticatedUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public AddressResponse addAddress(AddressRequest request) {
        User user = getAuthenticatedUser();

        Address address = new Address();
        address.setStreet(request.getStreet());
        address.setCity(request.getCity());
        address.setState(request.getState());
        address.setZipCode(request.getZipCode());
        address.setCountry(request.getCountry());
        address.setUser(user); // Link the address to the logged-in user

        Address savedAddress = addressRepository.save(address);
        return mapToResponse(savedAddress);
    }

    public List<AddressResponse> getUserAddresses() {
        User user = getAuthenticatedUser();

        List<Address> addresses = addressRepository.findByUser(user);

        return addresses.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private AddressResponse mapToResponse(Address address) {
        AddressResponse response = new AddressResponse();
        response.setId(address.getId());
        response.setStreet(address.getStreet());
        response.setCity(address.getCity());
        response.setState(address.getState());
        response.setZipCode(address.getZipCode());
        response.setCountry(address.getCountry());
        return response;
    }
}