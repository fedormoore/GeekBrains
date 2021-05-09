package ru.moore.market.msorder.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderDto {

    private Long id;
    private String name;
    private String location;

}
