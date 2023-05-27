package com.sittingseat.sittingseat.shopkeeper.repository;

import com.sittingseat.sittingseat.shopkeeper.domain.Restaurant;
import com.sittingseat.sittingseat.shopkeeper.dtos.RestaurantDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class RestaurantQueryRepositoryImplTest {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    EntityManager em;

    @Test
    public void searchByNameLike(){
        //given
        Restaurant restaurantA = Restaurant.builder()
                .name("ResA")
                .greeting("Hello!")
                .openTime(LocalDateTime.now())
                .closeTime(LocalDateTime.now())
                .reservationTimeUnit(60)
                .totalTableCount(30)
                .snsLink("instaResA")
                .blogLink("blogResA")
                .phone("000-1111-2222")
                .location("Seoul")
                .build();

        Restaurant restaurantB = Restaurant.builder()
                .name("ResB")
                .greeting("hi!")
                .openTime(LocalDateTime.now())
                .closeTime(LocalDateTime.now())
                .reservationTimeUnit(30)
                .totalTableCount(100)
                .snsLink("instaResB")
                .blogLink("blogResB")
                .phone("000-1234-5678")
                .location("Busan")
                .build();

        Restaurant restaurantC = Restaurant.builder()
                .name("Pizza")
                .greeting("Pizza restaurant!")
                .openTime(LocalDateTime.now())
                .closeTime(LocalDateTime.now())
                .reservationTimeUnit(60)
                .totalTableCount(10)
                .snsLink("nothing")
                .blogLink("nothing")
                .phone("000-5555-6666")
                .location("Daegu")
                .build();

        restaurantRepository.save(restaurantA);
        restaurantRepository.save(restaurantB);
        restaurantRepository.save(restaurantC);

        em.flush();
        em.clear();

        //when
        String searchName1 = "Res";
        String searchName2 = "ResB";
        String searchName3 = "";
        String searchName4 = "es";

        List<RestaurantDto> result1 = restaurantRepository.searchByName(searchName1);
        List<RestaurantDto> result2 = restaurantRepository.searchByName(searchName2);
        List<RestaurantDto> result3 = restaurantRepository.searchByName(searchName3);
        List<RestaurantDto> result4 = restaurantRepository.searchByName(searchName4);

        //then
        assertThat(result1).extracting("name").containsExactly("ResA", "ResB");
        assertThat(result2).extracting("name").containsExactly("ResB");
        assertThat(result3).extracting("name").containsExactly("ResA", "ResB", "Pizza");
        assertThat(result4).extracting("name").containsExactly("ResA", "ResB");

    }

}