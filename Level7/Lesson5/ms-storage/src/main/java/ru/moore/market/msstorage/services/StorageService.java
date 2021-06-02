package ru.moore.market.msstorage.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.moore.market.msstorage.model.dto.StorageDto;
import ru.moore.market.msstorage.model.entities.Storage;
import ru.moore.market.msstorage.repositories.StorageRepository;
import ru.moore.market.msstorage.services.mappers.MapperUtils;

import java.util.List;

@Service
public class StorageService {

    @Autowired
    MapperUtils mapperUtils;

    @Autowired
    StorageRepository storageRepository;

    public void saveStorage(StorageDto storage) {
        storageRepository.save(mapperUtils.map(storage, Storage.class));
    }

    public List<StorageDto> findAll() {
        return mapperUtils.mapAll(storageRepository.findAll(), StorageDto.class);
    }

    public StorageDto findById(Long id) {
        return mapperUtils.map(storageRepository.findById(id), StorageDto.class);
    }
}
