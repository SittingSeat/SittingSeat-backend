package com.sittingseat.sittingseat.controller;

import com.sittingseat.sittingseat.dto.ReservationDto;
import com.sittingseat.sittingseat.dto.ReservationRequest;
import com.sittingseat.sittingseat.dto.Result;
import com.sittingseat.sittingseat.security.auth.PrincipalDetails;
import com.sittingseat.sittingseat.service.ReservationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.Operation;
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

@Api(tags = "예약 API")
@Controller
@RequiredArgsConstructor
@RequestMapping("/reservation")
public class ReservationController {
    private final ReservationService reservationService;

    @Operation(summary = "예약 등록 API", description = "ReservationRequest를 받아 예약 등록을 진행한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "예약 성공"),
            @ApiResponse(code = 400, message = "회원 또는 식당 존재하지 않음")
    })
    @PostMapping("/register")
    public ResponseEntity<Void> registerReservation(@RequestBody ReservationRequest reservationRequest, @AuthenticationPrincipal PrincipalDetails principalDetails){
        Long memberId = principalDetails.getMember().getId();

        reservationService.doReservation(reservationRequest, memberId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "내 모든 예약 검색 API", description = "나의 모든 예약 내역을 검색한다. List<ReservationDto> 형태로 반환한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "나의 모든 예약 검색 성공"),
            @ApiResponse(code = 400, message = "회원 존재하지 않음")
    })
    @GetMapping("/my")
    public ResponseEntity<Result<List<ReservationDto>>> findByMember(@AuthenticationPrincipal PrincipalDetails principalDetails){
        Long memberId = principalDetails.getMember().getId();
        List<ReservationDto> reservations = reservationService.findByMember(memberId);
        return new ResponseEntity<>(new Result<>(reservations), HttpStatus.OK);
    }
}
