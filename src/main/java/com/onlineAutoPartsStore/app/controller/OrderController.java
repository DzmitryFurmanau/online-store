package com.onlineAutoPartsStore.app.controller;

import com.onlineAutoPartsStore.app.component.LocalizedMessageSource;
import com.onlineAutoPartsStore.app.dto.request.OrderRequestDto;
import com.onlineAutoPartsStore.app.dto.response.OrderResponseDto;
import com.onlineAutoPartsStore.app.model.CustomersAddresses;
import com.onlineAutoPartsStore.app.model.DetailsStocks;
import com.onlineAutoPartsStore.app.model.Order;
import com.onlineAutoPartsStore.app.model.Seller;
import com.onlineAutoPartsStore.app.service.OrderService;
import org.dozer.Mapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * The type Order controller.
 */
@RestController
@RequestMapping("/orders")
public class OrderController {

    private final Mapper mapper;

    private final OrderService orderService;

    private final LocalizedMessageSource localizedMessageSource;

    /**
     * Instantiates a new Order controller.
     *
     * @param mapper                 the mapper
     * @param orderService           the order service
     * @param localizedMessageSource the localized message source
     */
    public OrderController(Mapper mapper, OrderService orderService, LocalizedMessageSource localizedMessageSource) {
        this.mapper = mapper;
        this.orderService = orderService;
        this.localizedMessageSource = localizedMessageSource;
    }

    /**
     * Gets all.
     *
     * @return the all
     */
    @GetMapping
    public ResponseEntity<List<OrderResponseDto>> getAll() {
        final List<Order> orders = orderService.findAll();
        final List<OrderResponseDto> orderResponseDtoList = orders.stream()
                .map((order) -> mapper.map(order, OrderResponseDto.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(orderResponseDtoList, HttpStatus.OK);
    }

    /**
     * Gets one.
     *
     * @param id the id
     * @return the one
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<OrderResponseDto> getOne(@PathVariable Long id) {
        final OrderResponseDto orderResponseDto = mapper.map(orderService.findById(id), OrderResponseDto.class);
        return new ResponseEntity<>(orderResponseDto, HttpStatus.OK);
    }

    /**
     * Save response entity.
     *
     * @param orderRequestDto the order request dto
     * @return the response entity
     */
    @PostMapping
    public ResponseEntity<OrderResponseDto> save(@RequestBody OrderRequestDto orderRequestDto) {
        orderRequestDto.setId(null);
        final OrderResponseDto orderResponseDto = mapper.map(orderService.save(getOrder(orderRequestDto)), OrderResponseDto.class);
        return new ResponseEntity<>(orderResponseDto, HttpStatus.OK);
    }

    /**
     * Update response entity.
     *
     * @param orderRequestDto the order request dto
     * @param id              the id
     * @return the response entity
     */
    @PutMapping(value = "/{id}")
    public ResponseEntity<OrderResponseDto> update(@RequestBody OrderRequestDto orderRequestDto, @PathVariable Long id) {
        if (!Objects.equals(id, orderRequestDto.getId())) {
            throw new RuntimeException(localizedMessageSource.getMessage("controller.order.unexpectedId", new Object[]{}));
        }
        final OrderResponseDto orderResponseDto = mapper.map(orderService.update(getOrder(orderRequestDto)), OrderResponseDto.class);
        return new ResponseEntity<>(orderResponseDto, HttpStatus.OK);
    }

    /**
     * Delete.
     *
     * @param id the id
     */
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public void delete(@PathVariable Long id) {
        orderService.deleteById(id);
    }

    private Order getOrder(OrderRequestDto orderRequestDto) {
        final Order order = mapper.map(orderRequestDto, Order.class);
        final Seller seller = new Seller();
        final DetailsStocks detailsStocks = new DetailsStocks();
        final CustomersAddresses customersAddresses = new CustomersAddresses();
        seller.setId(orderRequestDto.getSellerId());
        detailsStocks.setId(orderRequestDto.getDetailsStocksId());
        customersAddresses.setId(orderRequestDto.getCustomersAddressesId());
        order.setSeller(seller);
        order.setDetailsStocks(detailsStocks);
        order.setCustomersAddresses(customersAddresses);
        return order;
    }
}
