package ru.moore.market.msstorage.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StorageDto {

    private Long id;
    private String name;
    private String location;

}
