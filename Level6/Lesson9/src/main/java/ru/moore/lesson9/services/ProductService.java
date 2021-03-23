package ru.moore.lesson9.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.moore.lesson9.exeptions.PageNotFoundException;
import ru.moore.lesson9.model.dtos.ProductDto;
import ru.moore.lesson9.model.entities.Product;
import ru.moore.lesson9.repository.ProductRepository;
import ru.moore.lesson9.services.mappers.ProductMapper;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    ProductMapper productMapper;

    public Optional<ProductDto> getProductById(Long id) {
        return productRepository.findById(id).map(this::convertToDto);
    }

    private ProductDto convertToDto(Product product) {
        ProductDto postDto = productMapper.modelMapper().map(product, ProductDto.class);
        return postDto;
    }

    public List<ProductDto> getAllProduct(int page, int size) {
        if (page < 0 || size <= 0) {
            throw new PageNotFoundException("Неверный запрос");
        }
        return checkTheEnd(productRepository.findAll(PageRequest.of(page, size)).map(this::convertToDto));
    }

    private List<ProductDto> checkTheEnd(Page<ProductDto> result) {
        if (!result.hasContent()) {
            throw new PageNotFoundException("Запрашиваемой страницы не существует");
        }
        return result.toList();
    }

    public List<ProductDto> sortCostUp() {
        return productRepository.findByOrderByCostAsc();
    }

    public List<ProductDto> sortCostDown() {
        return productRepository.findByOrderByCostDesc();
    }

    public List<ProductDto> sortByTitleAndCost(int sortTitle, int sortCost) {
        if (sortTitle == 0 & sortCost == 0) {
            return productRepository.findAll().stream().map(ProductDto::new).collect(Collectors.toList());
        } else if (sortTitle == 1 & sortCost == 0) {
            return productRepository.findAll(Sort.by("title").ascending().and(Sort.by("cost").descending())).stream().map(ProductDto::new).collect(Collectors.toList());
        } else if (sortTitle == 0 & sortCost == 1) {
            return productRepository.findAll(Sort.by("title").descending().and(Sort.by("cost").ascending())).stream().map(ProductDto::new).collect(Collectors.toList());
        } else {
            return productRepository.findAll(Sort.by("title").ascending().and(Sort.by("cost").ascending())).stream().map(ProductDto::new).collect(Collectors.toList());
        }
    }

    public ProductDto saveNewProduct(ProductDto productDto) {
        Product product = convertToEntity(productDto);
        return convertToDto(productRepository.save(product));
    }

    private Product convertToEntity(ProductDto productDto) {
        Product product = productMapper.modelMapper().map(productDto, Product.class);
        return product;
    }

    public void deleteProductById(Long id) {
        productRepository.deleteById(id);
    }

    public List<ProductDto> findProductByTitle(String title) {
        return productRepository.findAllByTitleContainingIgnoreCase(title);
    }

    public ProductDto findMinСost() {
        return productRepository.findFirstByOrderByCostAsc();
    }

    public ProductDto findMaxCost() {
        return productRepository.findFirstByOrderByCostDesc();
    }

    public List<ProductDto> findCostBetween(Integer min, Integer max) {
        return productRepository.findAllByCostBetween(min, max);
    }




}
