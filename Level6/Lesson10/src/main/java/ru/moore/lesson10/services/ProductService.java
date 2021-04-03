package ru.moore.lesson10.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
<<<<<<< HEAD
import org.springframework.data.domain.Sort;
=======
import org.springframework.data.jpa.domain.Specification;
>>>>>>> Level6Lesson10
import org.springframework.stereotype.Service;
import ru.moore.lesson10.exeptions.PageNotFoundException;
import ru.moore.lesson10.model.dtos.ProductDto;
import ru.moore.lesson10.model.entities.Product;
import ru.moore.lesson10.repository.ProductRepository;
<<<<<<< HEAD
import ru.moore.lesson10.services.mappers.ProductMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
=======
import ru.moore.lesson10.services.mappers.MapperUtils;

import java.util.Optional;
>>>>>>> Level6Lesson10

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;
<<<<<<< HEAD
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
=======

    @Autowired
    MapperUtils mapperUtils;

    public Optional<ProductDto> getProductById(Long id) {
        return Optional.of(mapperUtils.map(productRepository.findById(id), ProductDto.class));
    }

    public Page<ProductDto> findAll(Specification<Product> spec, int page, int size) {
        if (page < 0 || size <= 0) {
            throw new PageNotFoundException("Неверный запрос");
        }
        //return checkTheEnd(productRepository.findAll(spec, PageRequest.of(page, size)).map(this::convertToDto));
        return checkTheEnd((Page<ProductDto>) mapperUtils.map(productRepository.findAll(spec, PageRequest.of(page, size)), ProductDto.class));
    }

    private Page<ProductDto> checkTheEnd(Page<ProductDto> result) {
        if (!result.hasContent()) {
            throw new PageNotFoundException("Запрашиваемой страницы не существует");
        }
        return result;
    }

    public ProductDto saveNewProduct(ProductDto productDto) {
        return mapperUtils.map(productRepository.save(mapperUtils.map(productDto, Product.class)), ProductDto.class);
>>>>>>> Level6Lesson10
    }

    public void deleteProductById(Long id) {
        productRepository.deleteById(id);
    }

<<<<<<< HEAD
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




=======
>>>>>>> Level6Lesson10
}
