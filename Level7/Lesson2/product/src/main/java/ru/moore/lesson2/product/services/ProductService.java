package ru.moore.lesson2.product.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.moore.lesson2.core.exeptions.PageNotFoundException;
import ru.moore.lesson2.product.models.dtos.ProductDto;
import ru.moore.lesson2.product.models.entities.Product;
import ru.moore.lesson2.product.repository.ProductRepository;
import ru.moore.lesson2.product.services.mappers.MapperUtils;

import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    MapperUtils mapperUtils;

    public Optional<ProductDto> getProductById(Long id) {
        return Optional.of(mapperUtils.map(productRepository.findById(id), ProductDto.class));
    }

    public Page<ProductDto> findAll(Specification<Product> spec, int page, int size) {
        if (page < 0 || size <= 0) {
            throw new PageNotFoundException("Неверный запрос");
        }
        return checkTheEnd(new PageImpl<>(mapperUtils.mapAll(productRepository.findAll(spec, PageRequest.of(page, size)).toList(), ProductDto.class)));
    }

    private Page<ProductDto> checkTheEnd(Page<ProductDto> result) {
        if (!result.hasContent()) {
            throw new PageNotFoundException("Запрашиваемой страницы не существует");
        }
        return result;
    }

    public ProductDto saveNewProduct(ProductDto productDto) {
        return mapperUtils.map(productRepository.save(mapperUtils.map(productDto, Product.class)), ProductDto.class);
    }

    public void deleteProductById(Long id) {
        productRepository.deleteById(id);
    }

}
