package com.sittingseat.sittingseat.shopkeeper.service;

import com.sittingseat.sittingseat.domain.ImageFile;
import com.sittingseat.sittingseat.domain.Review;
import com.sittingseat.sittingseat.enums.FoodCategoryEnum;
import com.sittingseat.sittingseat.enums.ImageEnum;
import com.sittingseat.sittingseat.enums.TableStateEnum;
import com.sittingseat.sittingseat.shopkeeper.domain.Restaurant;
import com.sittingseat.sittingseat.shopkeeper.domain.Seat;
import com.sittingseat.sittingseat.shopkeeper.dtos.ImageFileDto;
import com.sittingseat.sittingseat.shopkeeper.dtos.RestaurantDto;
import com.sittingseat.sittingseat.shopkeeper.dtos.RestaurantRequest;
import com.sittingseat.sittingseat.shopkeeper.dtos.RestaurantResponse;
import com.sittingseat.sittingseat.shopkeeper.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final SeatRepository seatRepository;
    private final ImageFileRepository imageFileRepository;
    private final ReviewRepository reviewRepository;

    @Transactional
    public Restaurant registerRestaurant(RestaurantRequest restaurantRequest){
        Restaurant restaurant = Restaurant.builder()
                .name(restaurantRequest.getName())
                .greeting(restaurantRequest.getGreeting())
                .openTime(restaurantRequest.getStartTime())
                .closeTime(restaurantRequest.getEndTime())
                .reservationTimeUnit(restaurantRequest.getReservationTimeUnit())
                .location(restaurantRequest.getLocation())
                .phone(restaurantRequest.getTelephoneNumber())
                .blogLink(restaurantRequest.getBlog())
                .snsLink(restaurantRequest.getInstagram())
                .totalTableCount(restaurantRequest.getSeatRequest().getTotal())
                .foodCategoryEnum(restaurantRequest.getCategory())
                .build();

        int totalSeat = restaurantRequest.getSeatRequest().getTotal();

        for (int i = 0; i < totalSeat; i++) {
            Seat seat = Seat.builder()
                    .restaurant(restaurant)
                    .tableNumber(i + 1)
                    .tableState(TableStateEnum.FREE)
                    .build();

            seatRepository.save(seat);
        }

        return restaurantRepository.save(restaurant);
    }

    @Transactional
    public void removeRestaurant(Long removeRestaurantId){
        restaurantRepository.deleteById(removeRestaurantId);
    }

    public List<RestaurantResponse> findAll(){
        List<Restaurant> restaurants = restaurantRepository.findAll();
        List<RestaurantDto> restaurantDtos = restaurants.stream()
                .map(r -> new RestaurantDto(
                        r.getId(),
                        r.getName(),
                        r.getGreeting(),
                        r.getOpenTime(),
                        r.getCloseTime(),
                        r.getReservationTimeUnit(),
                        r.getLocation(),
                        r.getPhone(),
                        r.getSnsLink(),
                        r.getBlogLink(),
                        r.getTotalTableCount(),
                        r.getCategory()
                ))
                .collect(Collectors.toList());

        return restaurantDtos.stream()
                .map(this::getRestaurantResponse)
                .collect(Collectors.toList());

    }

    public List<RestaurantResponse> findByName(String nameCond){
        List<RestaurantDto> restaurantsDto = restaurantRepository.searchByName(nameCond);

        return restaurantsDto.stream()
                .map(this::getRestaurantResponse)
                .collect(Collectors.toList());
    }

    public List<RestaurantResponse> findByCategory(List<FoodCategoryEnum> foodCategoryEnums){
        List<RestaurantDto> restaurantsDto = restaurantRepository.searchByCategories(foodCategoryEnums);

        return restaurantsDto.stream()
                .map(this::getRestaurantResponse)
                .collect(Collectors.toList());
    }

    private List<Restaurant> changeDtoToEntity(List<RestaurantDto> restaurantsDto) {
        return restaurantsDto.stream()
                .map((rd) -> Restaurant.builder()
                        .location(rd.getLocation())
                        .phone(rd.getPhone())
                        .blogLink(rd.getBlogLink())
                        .snsLink(rd.getSnsLink())
                        .totalTableCount(rd.getTotalTableCount())
                        .reservationTimeUnit(rd.getReservationTimeUnit())
                        .closeTime(rd.getCloseTime())
                        .openTime(rd.getOpenTime())
                        .greeting(rd.getGreeting())
                        .name(rd.getName())
                        .foodCategoryEnum(rd.getCategory())
                        .build())
                .collect(Collectors.toList());
    }

    private RestaurantResponse getRestaurantResponse(RestaurantDto restaurantDto) {
        List<Review> reviews = reviewRepository.findByRestaurantId(restaurantDto.getId());
        double restaurantAvgReview = reviews.stream()
                .mapToDouble(Review::getScore)
                .average().orElse(0);

        return new RestaurantResponse(
                restaurantDto.getId(),
                restaurantDto.getName(),
                restaurantDto.getGreeting(),
                restaurantDto.getOpenTime(),
                restaurantDto.getCloseTime(),
                restaurantDto.getReservationTimeUnit(),
                restaurantDto.getLocation(),
                restaurantDto.getPhone(),
                restaurantDto.getSnsLink(),
                restaurantDto.getBlogLink(),
                restaurantDto.getTotalTableCount(),
                restaurantDto.getCategory(),
                imageFileRepository.findByRestaurantId(restaurantDto.getId()).stream()
                        .map(i -> new ImageFileDto(i.getS3ImagePath(), i.getImageType()))
                        .collect(Collectors.toList()),
                restaurantAvgReview,
                reviews.size()
        );
    }
}
