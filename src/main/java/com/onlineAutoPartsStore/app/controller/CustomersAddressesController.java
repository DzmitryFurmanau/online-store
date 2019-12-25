package com.onlineAutoPartsStore.app.controller;

import com.onlineAutoPartsStore.app.component.LocalizedMessageSource;
import com.onlineAutoPartsStore.app.dto.AddressDto;
import com.onlineAutoPartsStore.app.dto.CustomersAddressesDto;
import com.onlineAutoPartsStore.app.dto.request.CustomerRequestDto;
import com.onlineAutoPartsStore.app.dto.request.CustomersAddressesRequestDto;
import com.onlineAutoPartsStore.app.dto.response.CustomerResponseDto;
import com.onlineAutoPartsStore.app.dto.response.CustomersAddressesResponseDto;
import com.onlineAutoPartsStore.app.model.Address;
import com.onlineAutoPartsStore.app.model.Customer;
import com.onlineAutoPartsStore.app.model.CustomersAddresses;
import com.onlineAutoPartsStore.app.service.CustomerService;
import com.onlineAutoPartsStore.app.service.CustomersAddressesService;
import org.dozer.Mapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/customers_addresses")
public class CustomersAddressesController {

    private final Mapper mapper;

    private final CustomersAddressesService customersAddressesService;

    private final LocalizedMessageSource localizedMessageSource;


    public CustomersAddressesController(Mapper mapper, CustomersAddressesService customersAddressesService, LocalizedMessageSource localizedMessageSource) {
        this.mapper = mapper;
        this.customersAddressesService = customersAddressesService;
        this.localizedMessageSource = localizedMessageSource;
    }

    @GetMapping
    public ResponseEntity<List<CustomersAddressesResponseDto>> getAll() {
        final List<CustomersAddresses> customersAddresses = customersAddressesService.findAll();
        final List<CustomersAddressesResponseDto> customersAddressesResponseDtoList = customersAddresses.stream()
                .map((customerAddresses) -> mapper.map(customerAddresses, CustomersAddressesResponseDto.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(customersAddressesResponseDtoList, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<CustomersAddressesResponseDto> getOne(@PathVariable Long id) {
        final CustomersAddressesResponseDto customersAddressesResponseDto = mapper.map(customersAddressesService.findById(id), CustomersAddressesResponseDto.class);
        return new ResponseEntity<>(customersAddressesResponseDto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CustomersAddressesDto> save(@RequestBody CustomersAddressesDto customersAddressesDto) {
        customersAddressesDto.setId(null);
        final CustomersAddressesDto responseCustomersAddressesDto = mapper.map(customersAddressesService.save(mapper.map(customersAddressesDto, CustomersAddresses.class)), CustomersAddressesDto.class);
        return new ResponseEntity<>(responseCustomersAddressesDto, HttpStatus.OK);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<CustomersAddressesDto> update(@RequestBody CustomersAddressesDto customersAddressesDto, @PathVariable Long id) {
        if (!Objects.equals(id, customersAddressesDto.getId())) {
            throw new RuntimeException(localizedMessageSource.getMessage("controller.customers_addresses.unexpectedId", new Object[]{}));
        }
        final CustomersAddressesDto responseCustomersAddressesDto = mapper.map(customersAddressesService.update(mapper.map(customersAddressesDto, CustomersAddresses.class)), CustomersAddressesDto.class);
        return new ResponseEntity<>(responseCustomersAddressesDto, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public void delete(@PathVariable Long id) {
        customersAddressesService.deleteById(id);
    }
}
