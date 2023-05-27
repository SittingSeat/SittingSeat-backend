package com.sittingseat.sittingseat.shopkeeper.service;

import com.sittingseat.sittingseat.enums.TableStateEnum;
import com.sittingseat.sittingseat.shopkeeper.domain.Restaurant;
import com.sittingseat.sittingseat.shopkeeper.domain.Seat;
import com.sittingseat.sittingseat.shopkeeper.dtos.RestaurantDto;
import com.sittingseat.sittingseat.shopkeeper.dtos.RestaurantRequest;
import com.sittingseat.sittingseat.shopkeeper.repository.RestaurantRepository;
import com.sittingseat.sittingseat.shopkeeper.repository.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final SeatRepository seatRepository;

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

    public List<RestaurantDto> findAll(){
        List<Restaurant> restaurants = restaurantRepository.findAll();
        List<RestaurantDto> result = restaurants.stream()
                .map((r) -> new RestaurantDto(
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
                        r.getTotalTableCount()
                ))
                .collect(Collectors.toList());
        return result;
    }
}
