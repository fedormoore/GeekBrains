package ru.moore.lesson1.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.moore.lesson1.model.dtos.CartDto;
import ru.moore.lesson1.model.dtos.UserDto;
import ru.moore.lesson1.model.entities.Cart;
import ru.moore.lesson1.repository.CartRepository;
import ru.moore.lesson1.repository.ProductRepository;
import ru.moore.lesson1.repository.UserRepository;
import ru.moore.lesson1.services.mappers.MapperUtils;

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
