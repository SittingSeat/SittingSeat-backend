package com.sittingseat.sittingseat.shopkeeper.controller;

import com.sittingseat.sittingseat.domain.Member;
import com.sittingseat.sittingseat.security.auth.PrincipalDetails;
import com.sittingseat.sittingseat.shopkeeper.dtos.RestaurantRequest;
import com.sittingseat.sittingseat.shopkeeper.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/restaurant")
public class RestaurantController {
    private final RestaurantService restaurantService;

    @PostMapping("/register")
    public ResponseEntity<Void> registerRestaurant(@RequestBody RestaurantRequest restaurantRequest){
        restaurantService.registerRestaurant(restaurantRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // update == register
//    @PostMapping("/update")
//    public ResponseEntity<Void> updateRestaurant(@RequestBody RestaurantRequest restaurantRequest){
//        return new ResponseEntity<>(HttpStatus.OK);
//    }

    @PostMapping("/remove/{restaurantId}")
    public ResponseEntity<Void> removeRestaurant(@PathVariable Long restaurantId){
        restaurantService.removeRestaurant(restaurantId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
