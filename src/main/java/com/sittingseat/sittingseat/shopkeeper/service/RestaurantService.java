package com.sittingseat.sittingseat.shopkeeper.service;

import com.sittingseat.sittingseat.domain.Member;
import com.sittingseat.sittingseat.enums.TableStateEnum;
import com.sittingseat.sittingseat.shopkeeper.domain.Restaurant;
import com.sittingseat.sittingseat.shopkeeper.domain.Seat;
import com.sittingseat.sittingseat.shopkeeper.dtos.RestaurantRequest;
import com.sittingseat.sittingseat.shopkeeper.repository.RestaurantRepository;
import com.sittingseat.sittingseat.shopkeeper.repository.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final SeatRepository seatRepository;

    @Transactional
    public void registerRestaurant(RestaurantRequest restaurantRequest){
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

        restaurantRepository.save(restaurant);
    }

    @Transactional
    public void removeRestaurant(Long removeRestaurantId){
        restaurantRepository.deleteById(removeRestaurantId);
    }
}
