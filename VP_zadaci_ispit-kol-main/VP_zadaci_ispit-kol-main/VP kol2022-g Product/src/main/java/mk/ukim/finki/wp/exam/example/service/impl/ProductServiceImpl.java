package mk.ukim.finki.wp.exam.example.service.impl;

import mk.ukim.finki.wp.exam.example.model.Category;
import mk.ukim.finki.wp.exam.example.model.Product;
import mk.ukim.finki.wp.exam.example.model.exceptions.InvalidCategoryIdException;
import mk.ukim.finki.wp.exam.example.model.exceptions.InvalidProductIdException;
import mk.ukim.finki.wp.exam.example.repository.CategoryRepository;
import mk.ukim.finki.wp.exam.example.repository.ProductRepository;
import mk.ukim.finki.wp.exam.example.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Product> listAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product findById(Long id) {
        return productRepository.findById(id).orElseThrow(()->new InvalidProductIdException(id));
    }

    @Override
    public Product create(String name, Double price, Integer quantity, List<Long> categories) {

        List<Category> categories_obj = categoryRepository.findAllById(categories);
        Product product = new Product(name, price, quantity, categories_obj);
        return productRepository.save(product);
    }

    @Override
    public Product update(Long id, String name, Double price, Integer quantity, List<Long> categories) {

        Product product = productRepository.findById(id).orElseThrow(()->new InvalidProductIdException(id));
        List<Category> categories_obj = categoryRepository.findAllById(categories);

        product.setName(name);
        product.setPrice(price);
        product.setQuantity(quantity);
        product.setCategories(categories_obj);
        return productRepository.save(product);
    }

    @Override
    public Product delete(Long id) {
        Product product = productRepository.findById(id).orElseThrow(()->new InvalidProductIdException(id));
        productRepository.delete(product);
        return product;
    }

    @Override
    public List<Product> listProductsByNameAndCategory(String name, Long categoryId) {
        if(name == null && categoryId == null){
            return productRepository.findAll();
        }

        String nameSearch = '%' + name + '%';

        if( categoryId == null) {
            return productRepository.findAllByNameLike(nameSearch);
        }

        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new InvalidCategoryIdException(categoryId));

        if( name == null || name.equals("")) {
            return productRepository.findAllByCategoriesContaining(category);
        }
        return productRepository.findAllByNameLikeAndCategoriesContaining(nameSearch, category);

    }
}
