package com.onlineAutoPartsStore.app.controller;

import com.onlineAutoPartsStore.app.component.LocalizedMessageSource;
import com.onlineAutoPartsStore.app.dto.request.DetailsStocksRequestDto;
import com.onlineAutoPartsStore.app.dto.response.DetailsStocksResponseDto;
import com.onlineAutoPartsStore.app.model.Detail;
import com.onlineAutoPartsStore.app.model.DetailsStocks;
import com.onlineAutoPartsStore.app.model.Stock;
import com.onlineAutoPartsStore.app.service.DetailsStocksService;
import org.dozer.Mapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * The type Details stocks controller.
 */
@RestController
@RequestMapping("/details_stocks")
public class DetailsStocksController {

    private final Mapper mapper;

    private final DetailsStocksService detailsStocksService;

    private final LocalizedMessageSource localizedMessageSource;

    /**
     * Instantiates a new Details stocks controller.
     *
     * @param mapper                 the mapper
     * @param detailsStocksService   the details stocks service
     * @param localizedMessageSource the localized message source
     */
    public DetailsStocksController(Mapper mapper, DetailsStocksService detailsStocksService, LocalizedMessageSource localizedMessageSource) {
        this.mapper = mapper;
        this.detailsStocksService = detailsStocksService;
        this.localizedMessageSource = localizedMessageSource;
    }

    /**
     * Gets all.
     *
     * @return the all
     */
    @GetMapping
    public ResponseEntity<List<DetailsStocksResponseDto>> getAll() {
        final List<DetailsStocks> detailsStocks = detailsStocksService.findAll();
        final List<DetailsStocksResponseDto> detailsStocksResponseDtoList = detailsStocks.stream()
                .map((detailStocks) -> mapper.map(detailStocks, DetailsStocksResponseDto.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(detailsStocksResponseDtoList, HttpStatus.OK);
    }

    /**
     * Gets one.
     *
     * @param id the id
     * @return the one
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<DetailsStocksResponseDto> getOne(@PathVariable Long id) {
        final DetailsStocksResponseDto detailsStocksResponseDto = mapper.map(detailsStocksService.findById(id), DetailsStocksResponseDto.class);
        return new ResponseEntity<>(detailsStocksResponseDto, HttpStatus.OK);
    }

    /**
     * Save response entity.
     *
     * @param detailsStocksRequestDto the details stocks request dto
     * @return the response entity
     */
    @PostMapping
    public ResponseEntity<DetailsStocksResponseDto> save(@RequestBody DetailsStocksRequestDto detailsStocksRequestDto) {
        detailsStocksRequestDto.setId(null);
        final DetailsStocksResponseDto detailsStocksResponseDto = mapper.map(detailsStocksService.save(getDetailsStocks(detailsStocksRequestDto)), DetailsStocksResponseDto.class);
        return new ResponseEntity<>(detailsStocksResponseDto, HttpStatus.OK);
    }

    /**
     * Update response entity.
     *
     * @param detailsStocksRequestDto the details stocks request dto
     * @param id                      the id
     * @return the response entity
     */
    @PutMapping(value = "/{id}")
    public ResponseEntity<DetailsStocksResponseDto> update(@RequestBody DetailsStocksRequestDto detailsStocksRequestDto, @PathVariable Long id) {
        if (!Objects.equals(id, detailsStocksRequestDto.getId())) {
            throw new RuntimeException(localizedMessageSource.getMessage("controller.details_stocks.unexpectedId", new Object[]{}));
        }
        final DetailsStocksResponseDto detailsStocksResponseDto = mapper.map(detailsStocksService.update(getDetailsStocks(detailsStocksRequestDto)), DetailsStocksResponseDto.class);
        return new ResponseEntity<>(detailsStocksResponseDto, HttpStatus.OK);
    }

    /**
     * Delete.
     *
     * @param id the id
     */
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public void delete(@PathVariable Long id) {
        detailsStocksService.deleteById(id);
    }

    private DetailsStocks getDetailsStocks(DetailsStocksRequestDto detailsStocksRequestDto) {
        final DetailsStocks detailsStocks = mapper.map(detailsStocksRequestDto, DetailsStocks.class);
        final Detail detail = new Detail();
        final Stock stock = new Stock();
        detail.setId(detailsStocksRequestDto.getDetailId());
        stock.setId(detailsStocksRequestDto.getStockId());
        detailsStocks.setDetail(detail);
        detailsStocks.setStock(stock);
        return detailsStocks;
    }
}
