package ru.moore.market.msstorage.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.moore.market.msstorage.model.entities.Storage;

import java.util.List;

public interface StorageRepository extends JpaRepository<Storage, Long> {

}
