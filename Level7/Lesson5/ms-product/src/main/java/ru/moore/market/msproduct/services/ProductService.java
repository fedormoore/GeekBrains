package ru.moore.market.msproduct.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.moore.market.msproduct.model.dto.ProductDto;
import ru.moore.market.msproduct.model.entities.Product;
import ru.moore.market.msproduct.repositories.ProductRepository;
import ru.moore.market.msproduct.services.mappers.MapperUtils;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    MapperUtils mapperUtils;

    @Autowired
    ProductRepository productRepository;

    public void saveStorage(ProductDto storage) {
        productRepository.save(mapperUtils.map(storage, Product.class));
    }

    public List<ProductDto> finAll() {
        return mapperUtils.mapAll(productRepository.findAll(), ProductDto.class);
    }

}
