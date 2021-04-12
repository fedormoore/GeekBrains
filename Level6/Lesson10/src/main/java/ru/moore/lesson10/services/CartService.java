package ru.moore.lesson10.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.moore.lesson10.model.dtos.AuthRequestDto;
import ru.moore.lesson10.model.dtos.CartDto;
import ru.moore.lesson10.model.dtos.ProductDto;
import ru.moore.lesson10.model.dtos.UserDto;
import ru.moore.lesson10.model.entities.Cart;
import ru.moore.lesson10.repository.CartRepository;
import ru.moore.lesson10.repository.ProductRepository;
import ru.moore.lesson10.repository.UserRepository;
import ru.moore.lesson10.services.mappers.MapperUtils;

import java.util.List;

@Service
public class CartService {

    @Autowired
    CartRepository cartRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    MapperUtils mapperUtils;


    public void addToCart(String login, String product) {
        Cart cart = new Cart();
        cart.setUser(userRepository.findByLogin(login));
        cart.setProduct(productRepository.findByTitle(product));
        cartRepository.save(cart);
    }

    public List<CartDto> showUserCarts(String login) {
        return mapperUtils.mapAll(cartRepository.findAllByUser_Id(mapperUtils.map(userRepository.findByLogin(login), UserDto.class).getId()), CartDto.class);
    }
}
