package ru.moore.msproduct.services;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.moore.msproduct.models.Product;
import ru.moore.msproduct.repositories.ProductRepository;
import ru.moore.routinglib.dtos.ProductDto;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ModelMapper modelMapper;

    private final ProductRepository productRepository;

    public Optional<ProductDto> findProductDtoById(Long id) {
        return productRepository.findById(id).map(this::toDto);
    }

    public List<ProductDto> findProductDtosByIds(List<Long> ids) {
        return productRepository.findByIdIn(ids).stream().map(this::toDto).collect(Collectors.toList());
    }

    public Page<ProductDto> findAll(Specification<Product> spec, int page, int pageSize) {
        return productRepository.findAll(spec, PageRequest.of(page - 1, pageSize)).map(this::toDto);
    }

    public ProductDto saveOrUpdate(ProductDto product) throws ParseException {
        return toDto(productRepository.save(toEntity(product)));
    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    private ProductDto toDto(Product product) {
        return modelMapper.map(product, ProductDto.class);
    }

    private Product toEntity(ProductDto productDto) throws ParseException {
        return modelMapper.map(productDto, Product.class);
    }

    public Optional<ProductDto> findProductDtoByTitle(String title) {
        return productRepository.findByTitle(title).map(this::toDto);
    }

    public Product findProductByTitleTest(String title) {
        return productRepository.findByTitle(title).get();
    }
}
