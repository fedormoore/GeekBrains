package ru.moore.lesson3.product.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.moore.lesson3.core.exeptions.PageNotFoundException;
import ru.moore.lesson3.product.models.dtos.ProductDto;
import ru.moore.lesson3.product.models.entities.Product;
import ru.moore.lesson3.product.repository.ProductRepository;
import ru.moore.lesson3.product.services.mappers.MapperUtils;

import java.util.List;
import java.util.Optional;

@Service
@CacheConfig(cacheNames = "productCache")
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    MapperUtils mapperUtils;

    @Cacheable(cacheNames = "product", key = "#id", unless = "#result == null")
    public Optional<ProductDto> getProductById(Long id) {
        return Optional.of(mapperUtils.map(productRepository.findAllById(id), ProductDto.class));
    }

    @Cacheable(cacheNames = "productSpec")
    public List<ProductDto> findAll(Specification<Product> spec, int page, int size) {
        if (page < 0 || size <= 0) {
            throw new PageNotFoundException("Неверный запрос");
        }
        //return checkTheEnd(new PageImpl<>(mapperUtils.mapAll(productRepository.findAll(spec, PageRequest.of(page, size)).toList(), ProductDto.class)));
        return mapperUtils.mapAll(productRepository.findAll(spec, PageRequest.of(page, size)).toList(), ProductDto.class);
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
