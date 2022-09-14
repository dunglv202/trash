package com.example.shop.rest;

import com.example.shop.entity.Review;
import com.example.shop.service.ProductService;
import com.example.shop.service.ReviewService;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products/{productId}/reviews")
public class ReviewRestController {
    private ProductService productService;
    private ReviewService reviewService;

    public ReviewRestController(ProductService productService, ReviewService reviewService) {
        this.productService = productService;
        this.reviewService = reviewService;
    }

    @GetMapping("")
    public List<Review> getAllReviews(@PathVariable("productId") Integer productId,
                                      @RequestParam(value = "page", defaultValue = "0") Integer page,
                                      @RequestParam(value = "size", defaultValue = "20") Integer size) {
        return reviewService.getAllReviews(productId, PageRequest.of(page, size)).toList();
    }

    @GetMapping("/{reviewId}")
    public Review getReview(@PathVariable("reviewId") Integer reviewId) {
        return reviewService.getReview(reviewId);
    }

    @PostMapping("")
    public Review addReview(@PathVariable("productId") Integer productId,
                            @RequestBody Review review,
                            Authentication auth) {
        return reviewService.addReview(review, productId, auth);
    }

    @PutMapping("/{reviewId}")
    public Review editReview(@PathVariable("productId") Integer productId,
                             @PathVariable("reviewId") Integer reviewId,
                             @RequestBody Review review,
                             Authentication auth) {
        // ensure that users do update on their expected review
        review.setId(reviewId);

        return reviewService.updateReview(review, auth);
    }

    @DeleteMapping("/{reviewId}")
    public Review deleteReview(@PathVariable("reviewId") Integer reviewId,
                               Authentication auth) {
        return reviewService.deleteReview(reviewId, auth);
    }
}
