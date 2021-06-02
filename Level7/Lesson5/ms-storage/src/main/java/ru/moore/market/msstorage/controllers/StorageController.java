package ru.moore.market.msstorage.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.moore.market.msstorage.model.dto.StorageDto;
import ru.moore.market.msstorage.services.StorageService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/")
public class StorageController {

    @Autowired
    private StorageService storageService;

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public String saveStorage(@RequestBody StorageDto storage) {
        storageService.saveStorage(storage);
        return "OK";
    }

    @GetMapping("/findAll")
    public List<StorageDto> findAll() {
        return storageService.findAll();
    }

    @GetMapping("/findById/{id}")
    public StorageDto findById(@PathVariable Long id) {
        return storageService.findById(id);
    }
}
