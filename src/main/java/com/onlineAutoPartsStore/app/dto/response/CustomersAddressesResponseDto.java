package com.onlineAutoPartsStore.app.dto.response;

import com.onlineAutoPartsStore.app.dto.AddressDto;
import com.onlineAutoPartsStore.app.dto.CustomerDto;

public class CustomersAddressesResponseDto {

    private Long id;

    private AddressDto address;

    private CustomerDto customer;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AddressDto getAddress() {
        return address;
    }

    public void setAddress(AddressDto address) {
        this.address = address;
    }

    public CustomerDto getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerDto customer) {
        this.customer = customer;
    }
}