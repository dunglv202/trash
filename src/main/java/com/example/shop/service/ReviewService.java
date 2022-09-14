package com.example.shop.service;

import com.example.shop.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

import javax.validation.Valid;

public interface ReviewService {
    Page<Review> getAllReviews(Integer productId, Pageable pagination);
    Review getReview(Integer reviewId);
    Review addReview(@Valid Review review, Integer productId, Authentication auth);
    Review updateReview(@Valid Review updatedReview, Authentication auth);
    Review deleteReview(Integer reviewId, Authentication auth);
}
