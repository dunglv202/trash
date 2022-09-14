package com.example.shop.service.impl;

import com.example.shop.entity.Review;
import com.example.shop.entity.User;
import com.example.shop.exception.ItemNotExistsException;
import com.example.shop.repository.ReviewRepository;
import com.example.shop.service.ProductService;
import com.example.shop.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import com.example.shop.utils.AuthenticationUtils;

import javax.transaction.Transactional;
import javax.validation.Valid;

@Service
@Transactional
@Validated
public class ReviewServiceImpl implements ReviewService {
    private ReviewRepository reviewRepo;
    private ProductService productService;

    @Autowired
    public ReviewServiceImpl(ReviewRepository reviewRepo, ProductService productService) {
        this.reviewRepo = reviewRepo;
        this.productService = productService;
    }

    @Override
    public Page<Review> getAllReviews(Integer productId, Pageable pagination) {
        return reviewRepo.findAllByProductId(productId, pagination);
    }

    @Override
    public Review getReview(Integer reviewId) {
        return reviewRepo.findById(reviewId).orElseThrow(()->new ItemNotExistsException("Review - ID: " + reviewId));
    }

    @Override
    public Review addReview(Review review, Integer productId, Authentication auth) {
        review.setProduct(productService.getSingleProduct(productId));
        review.setUser(AuthenticationUtils.getUser(auth));

        review.setId(null);
        return reviewRepo.save(review);
    }

    @Override
    public Review updateReview(@Valid Review updatedReview, Authentication auth) {
        Review foundReview = getReview(updatedReview.getId());
        foundReview.merge(updatedReview);

        User loggedUser = AuthenticationUtils.getUser(auth);
        if (!isOwner(loggedUser, foundReview))
            throw new AuthorizationServiceException("This review does not belong to you");

        return reviewRepo.save(foundReview);
    }

    @Override
    public Review deleteReview(Integer reviewId, Authentication auth) {
        Review foundReview = getReview(reviewId);

        User loggedUser = AuthenticationUtils.getUser(auth);
        if (!isOwner(loggedUser, foundReview))
            throw new AuthorizationServiceException("This review does not belong to you");

        reviewRepo.delete(foundReview);
        return foundReview;
    }

    private boolean isOwner(User user, Review review) {
        return review.getUser().equals(user);
    }
}
