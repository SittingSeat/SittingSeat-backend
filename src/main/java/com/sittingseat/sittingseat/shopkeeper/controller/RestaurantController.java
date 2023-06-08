package com.sittingseat.sittingseat.shopkeeper.controller;

import com.sittingseat.sittingseat.domain.Member;
import com.sittingseat.sittingseat.enums.ImageEnum;
import com.sittingseat.sittingseat.security.auth.PrincipalDetails;
import com.sittingseat.sittingseat.shopkeeper.domain.Restaurant;
import com.sittingseat.sittingseat.shopkeeper.dtos.RestaurantCategoryRequest;
import com.sittingseat.sittingseat.shopkeeper.dtos.RestaurantDto;
import com.sittingseat.sittingseat.shopkeeper.dtos.RestaurantRequest;
import com.sittingseat.sittingseat.shopkeeper.dtos.RestaurantResponse;
import com.sittingseat.sittingseat.shopkeeper.service.RestaurantService;
import com.sittingseat.sittingseat.shopkeeper.service.S3Service;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Api(tags = "식당 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/restaurant")
public class RestaurantController {
    private final RestaurantService restaurantService;
    private final S3Service s3Service;

    @Operation(summary = "식당 등록 API", description = "form-data형태로 요청 필요. data-RestaurantRequest, menus-List<Multipart>, interiors-List<Multipart> ")
    @ApiResponses({
            @ApiResponse(code = 200, message = "식당 정상 등록"),
            @ApiResponse(code = 401, message = "S3 접근 실패"),

    })
    @PostMapping(value = "/register", consumes = {"multipart/form-data"})
    public ResponseEntity<Void> registerRestaurant(
            @RequestPart("data") RestaurantRequest restaurantRequest,
            @RequestPart("menus")List<MultipartFile> menuImages,
            @RequestPart("interiors") List<MultipartFile> interiorImages
            ){
        String restaurantName = restaurantRequest.getName();
        String dirName = "restaurant/" + restaurantName;
        Restaurant restaurant = restaurantService.registerRestaurant(restaurantRequest);

        s3Service.uploadFiles(menuImages, dirName, "menus", restaurant, ImageEnum.MENU);
        s3Service.uploadFiles(interiorImages, dirName, "interiors", restaurant, ImageEnum.INTERIOR);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // update == register
//    @PostMapping("/update")
//    public ResponseEntity<Void> updateRestaurant(@RequestBody RestaurantRequest restaurantRequest){
//        return new ResponseEntity<>(HttpStatus.OK);
//    }

    @Operation(summary = "식당 삭제 API", description = "PathVariable로 삭제할 식당 id 전송 필요.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "식당 삭제 성공"),
    })
    @PostMapping("/remove/{restaurantId}")
    public ResponseEntity<Void> removeRestaurant(@PathVariable Long restaurantId){
        restaurantService.removeRestaurant(restaurantId);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @Operation(summary = "전체 식당 검색 API", description = "전체 식당을 검색한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "식당 전체 조회 성공"),
    })
    @GetMapping("/all")
    public ResponseEntity<List<RestaurantResponse>> findAllRestaurant(){
        List<RestaurantResponse> restaurants = restaurantService.findAll();
        return new ResponseEntity<>(restaurants, HttpStatus.OK);
    }

    @Operation(summary = "식당 이름 검색 API", description = "식당 이름을 검색한다. 이름이 포함되어 있는 식당 다 검색된다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "식당 이름 검색 성공")
    })
    @GetMapping("/{restaurantName}")
    public ResponseEntity<List<RestaurantResponse>> findRestaurantByName(@PathVariable String restaurantName){
        List<RestaurantResponse> restaurants = restaurantService.findByName(restaurantName);
        return new ResponseEntity<>(restaurants, HttpStatus.OK);
    }

    @Operation(summary = "식당 종류 검색 API", description = "식당 종류를 통해 필터링할 수 있다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "식당 카테고리 필터링 성공")
    })

    @GetMapping("/categories")
    public ResponseEntity<List<RestaurantResponse>> findRestaurantByCategories(@RequestBody RestaurantCategoryRequest categoryRequest){
        List<RestaurantResponse> restaurants = restaurantService.findByCategory(categoryRequest.getCategories());
        return new ResponseEntity<>(restaurants, HttpStatus.OK);
    }

}
