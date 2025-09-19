package com.ecommerce.ProjectEcommerce.service;

import java.util.List;
import com.ecommerce.ProjectEcommerce.model.WishList;
import com.ecommerce.ProjectEcommerce.repository.WishListRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;


@Service
@Transactional
public class WishListService {

    private final WishListRepository wishListRepository;

    public WishListService(WishListRepository wishListRepository) {
        this.wishListRepository = wishListRepository;
    }

    public void createWishlist(WishList wishList) {
        wishListRepository.save(wishList);
    }

    public List<WishList> readWishList(Integer userId) {
        return wishListRepository.findAllByUserIdOrderByCreatedDateDesc(userId);
    }

    public void removeFromWishlist(Integer userId, Integer productId) {
        wishListRepository.deleteByUserIdAndProductId(userId, productId);
    }

}