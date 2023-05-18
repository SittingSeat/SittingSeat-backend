package com.sittingseat.sittingseat.service;

import com.sittingseat.sittingseat.domain.Member;
import com.sittingseat.sittingseat.domain.Reservation;
import com.sittingseat.sittingseat.dto.ReservationDto;
import com.sittingseat.sittingseat.dto.ReservationRequest;
import com.sittingseat.sittingseat.enums.TableStateEnum;
import com.sittingseat.sittingseat.exception.SittingSeatErrorCode;
import com.sittingseat.sittingseat.exception.SittingSeatException;
import com.sittingseat.sittingseat.repository.MemberRepository;
import com.sittingseat.sittingseat.repository.ReservationRepository;
import com.sittingseat.sittingseat.shopkeeper.domain.Restaurant;
import com.sittingseat.sittingseat.shopkeeper.domain.Seat;
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
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final RestaurantRepository restaurantRepository;
    private final SeatRepository seatRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void doReservation(ReservationRequest reservationRequest, Long memberId){
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new SittingSeatException(SittingSeatErrorCode.MEMBER_NOT_FOUND));

        Restaurant restaurant = restaurantRepository.findById(reservationRequest.getRestaurantId())
                .orElseThrow(() -> new SittingSeatException(SittingSeatErrorCode.RESTAURANT_NOT_FOUND));

        List<Seat> allSeats = seatRepository.findByRestaurant(restaurant);

        List<Seat> requestSeats = allSeats.stream()
                .filter((s) ->
                        reservationRequest.getSeats().contains(s.getTableNumber()))
                .collect(Collectors.toList());

        requestSeats.forEach((rs)-> {
            rs.updateSeatState(TableStateEnum.RESERVATION);
        });


        Reservation reservation = Reservation.builder()
                .restaurant(restaurant)
                .time(reservationRequest.getReservationTime())
                .numberOfPeople(reservationRequest.getReservationNumber())
                .member(member)
                .build();

        reservationRepository.save(reservation);
    }

    @Transactional
    public void cancelReservation(Long reservationId){
        reservationRepository.deleteById(reservationId);
    }

    public List<ReservationDto> findByMember(Long memberId){
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new SittingSeatException(SittingSeatErrorCode.MEMBER_NOT_FOUND));

        return reservationRepository.findByMember(member)
                .stream().map((r) -> new ReservationDto(
                        r.getId(),
                        r.getMember().getName(),
                        r.getRestaurant().getName(),
                        r.getNumberOfPeople()
                ))
                .collect(Collectors.toList());
    }
}
