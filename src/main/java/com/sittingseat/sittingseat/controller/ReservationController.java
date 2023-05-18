package com.sittingseat.sittingseat.controller;

import com.sittingseat.sittingseat.domain.Member;
import com.sittingseat.sittingseat.dto.ReservationDto;
import com.sittingseat.sittingseat.dto.ReservationRequest;
import com.sittingseat.sittingseat.security.auth.PrincipalDetails;
import com.sittingseat.sittingseat.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/reservation")
public class ReservationController {
    private final ReservationService reservationService;

    @PostMapping("/register")
    public ResponseEntity<Void> registerReservation(@RequestBody ReservationRequest reservationRequest, @AuthenticationPrincipal PrincipalDetails principalDetails){
        Long memberId = principalDetails.getMember().getId();

        reservationService.doReservation(reservationRequest, memberId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/my")
    public ResponseEntity<List<ReservationDto>> findByMember(@AuthenticationPrincipal PrincipalDetails principalDetails){
        Long memberId = principalDetails.getMember().getId();
        List<ReservationDto> reservations = reservationService.findByMember(memberId);
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }
}
