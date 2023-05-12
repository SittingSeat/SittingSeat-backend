package com.sittingseat.sittingseat.shopkeeper.controller;

import com.sittingseat.sittingseat.domain.Member;
import com.sittingseat.sittingseat.security.auth.PrincipalDetails;
import com.sittingseat.sittingseat.shopkeeper.domain.Restaurant;
import com.sittingseat.sittingseat.shopkeeper.dtos.RestaurantRequest;
import com.sittingseat.sittingseat.shopkeeper.service.RestaurantService;
import com.sittingseat.sittingseat.shopkeeper.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/restaurant")
public class RestaurantController {
    private final RestaurantService restaurantService;
    private final S3Service s3Service;

    @PostMapping(value = "/register", consumes = {"multipart/form-data"})
    public ResponseEntity<Void> registerRestaurant(
            @RequestPart("data") RestaurantRequest restaurantRequest,
            @RequestPart("menus")List<MultipartFile> menuImages,
            @RequestPart("interiors") List<MultipartFile> interiorImages
            ){
        String restaurantName = restaurantRequest.getName();
        String dirName = "restaurant/" + restaurantName;
        Restaurant restaurant = restaurantService.registerRestaurant(restaurantRequest);

        s3Service.uploadFiles(menuImages, dirName, "menus", restaurant);
        s3Service.uploadFiles(interiorImages, dirName, "interiors", restaurant);
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
