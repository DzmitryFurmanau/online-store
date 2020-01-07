package com.onlineAutoPartsStore.app.service.impl;

import com.onlineAutoPartsStore.app.component.LocalizedMessageSource;
import com.onlineAutoPartsStore.app.model.CustomersAddresses;
import com.onlineAutoPartsStore.app.repository.CustomersAddressesRepository;
import com.onlineAutoPartsStore.app.service.AddressService;
import com.onlineAutoPartsStore.app.service.CustomerService;
import com.onlineAutoPartsStore.app.service.CustomersAddressesService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * The type Customers addresses service.
 */
@Service
@Transactional
public class CustomersAddressesServiceImpl implements CustomersAddressesService {

    private final CustomersAddressesRepository customersAddressesRepository;

    private final CustomerService customerService;

    private final AddressService addressService;

    private final LocalizedMessageSource localizedMessageSource;

    /**
     * Instantiates a new Customers addresses service.
     *
     * @param customersAddressesRepository the customers addresses repository
     * @param customerService              the customer service
     * @param addressService               the address service
     * @param localizedMessageSource       the localized message source
     */
    public CustomersAddressesServiceImpl(CustomersAddressesRepository customersAddressesRepository, CustomerService customerService, AddressService addressService, LocalizedMessageSource localizedMessageSource) {
        this.customersAddressesRepository = customersAddressesRepository;
        this.customerService = customerService;
        this.addressService = addressService;
        this.localizedMessageSource = localizedMessageSource;
    }

    @Override
    public List<CustomersAddresses> findAll() {
        return customersAddressesRepository.findAll();
    }

    @Override
    public CustomersAddresses findById(Long id) {
        return customersAddressesRepository.findById(id).orElseThrow(() -> new RuntimeException(localizedMessageSource.getMessage("error.customers_addresses.notExist", new Object[]{})));
    }

    @Override
    public CustomersAddresses save(CustomersAddresses customersAddresses) {
        validate(customersAddresses.getId() != null, localizedMessageSource.getMessage("error.customers_addresses.notHaveId", new Object[]{}));
        validate(customersAddressesRepository.existsById(customersAddresses.getId()), localizedMessageSource.getMessage("error.customers_addresses.id.notUnique", new Object[]{}));
        return saveAndFlush(customersAddresses);
    }

    @Override
    public CustomersAddresses update(CustomersAddresses customersAddresses) {
        final Long id = customersAddresses.getId();
        validate(id == null, localizedMessageSource.getMessage("error.customers_addresses.haveId", new Object[]{}));
        final Optional<CustomersAddresses> duplicateCustomersAddresses = customersAddressesRepository.findById(customersAddresses.getId());
        final boolean isDuplicateExists = duplicateCustomersAddresses.isPresent();
        validate(isDuplicateExists, localizedMessageSource.getMessage("error.customers_addresses.id.notUnique", new Object[]{}));
        findById(id);
        return saveAndFlush(customersAddresses);
    }


    @Override
    public void delete(CustomersAddresses customersAddresses) {
        final Long id = customersAddresses.getId();
        validate(id == null, localizedMessageSource.getMessage("error.customers_addresses.haveId", new Object[]{}));
        findById(id);
        customersAddressesRepository.delete(customersAddresses);
    }

    @Override
    public void deleteById(Long id) {
        findById(id);
        customersAddressesRepository.deleteById(id);
    }

    private CustomersAddresses saveAndFlush(CustomersAddresses customersAddresses) {
        validate(customersAddresses.getCustomer() == null || customersAddresses.getCustomer().getId() == null, localizedMessageSource.getMessage("error.customers_addresses.customer.isNull", new Object[]{}));
        validate(customersAddresses.getAddress() == null || customersAddresses.getAddress().getId() == null, localizedMessageSource.getMessage("error.customers_addresses.address.isNull", new Object[]{}));
        customersAddresses.setCustomer(customerService.findById(customersAddresses.getCustomer().getId()));
        customersAddresses.setAddress(addressService.findById(customersAddresses.getAddress().getId()));
        return customersAddressesRepository.saveAndFlush(customersAddresses);
    }

    private void validate(boolean expression, String errorMessage) {
        if (expression) {
            throw new RuntimeException(errorMessage);
        }
    }
}
