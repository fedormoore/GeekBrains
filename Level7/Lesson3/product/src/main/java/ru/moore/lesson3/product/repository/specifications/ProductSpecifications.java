package ru.moore.lesson3.product.repository.specifications;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.MultiValueMap;
import ru.moore.lesson3.product.models.entities.Product;

public class ProductSpecifications {

    private static Specification<Product> costGreaterThanOrEqualsThan(int minCost) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("cost"), minCost);
    }

    private static Specification<Product> costLessThanOrEqualsThan(int maxCost) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("cost"), maxCost);
    }

    private static Specification<Product> titleLessThanOrEqualsThan(String titlePart) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get("title"), String.format("%%%s%%", titlePart));
    }

    private static Specification<Product> titleSortAsc() {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaQuery.orderBy(criteriaBuilder.asc(root.get("title"))).getRestriction();
    }

    private static Specification<Product> titleSortDesc() {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaQuery.orderBy(criteriaBuilder.desc(root.get("title"))).getRestriction();
    }

    public static Specification<Product> build(MultiValueMap<String, String> params) {
        Specification<Product> spec = Specification.where(null);
        if (params.containsKey("min_cost") && !params.getFirst("min_cost").isBlank()) {
            spec = spec.and(ProductSpecifications.costGreaterThanOrEqualsThan(Integer.parseInt(params.getFirst("min_cost"))));
        }
        if (params.containsKey("max_cost") && !params.getFirst("max_cost").isBlank()) {
            spec = spec.and(ProductSpecifications.costLessThanOrEqualsThan(Integer.parseInt(params.getFirst("max_cost"))));
        }
        if (params.containsKey("title") && !params.getFirst("title").isBlank()) {
            spec = spec.and(ProductSpecifications.titleLessThanOrEqualsThan(params.getFirst("title")));
        }
        if (params.containsKey("title_sort") && !params.getFirst("title_sort").isBlank()) {
            if (params.getFirst("title_sort").equals("asc")) {
                spec = spec.and(ProductSpecifications.titleSortAsc());
            } else {
                spec = spec.and(ProductSpecifications.titleSortDesc());
            }

        }
        return spec;
    }
}
