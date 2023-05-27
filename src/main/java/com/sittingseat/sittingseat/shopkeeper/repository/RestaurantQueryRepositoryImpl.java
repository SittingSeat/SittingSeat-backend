package com.sittingseat.sittingseat.shopkeeper.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sittingseat.sittingseat.shopkeeper.domain.QRestaurant;
import com.sittingseat.sittingseat.shopkeeper.dtos.QRestaurantDto;
import com.sittingseat.sittingseat.shopkeeper.dtos.RestaurantDto;

import javax.persistence.EntityManager;
import java.util.List;

import static com.sittingseat.sittingseat.shopkeeper.domain.QRestaurant.*;

public class RestaurantQueryRepositoryImpl implements RestaurantQueryRepository {

    private final JPAQueryFactory query;

    public RestaurantQueryRepositoryImpl(EntityManager em) {
        this.query = new JPAQueryFactory(em);
    }

    @Override
    public List<RestaurantDto> searchByName(String name) {
        return query
                .select(new QRestaurantDto(
                        restaurant.id,
                        restaurant.name,
                        restaurant.greeting,
                        restaurant.openTime,
                        restaurant.closeTime,
                        restaurant.reservationTimeUnit,
                        restaurant.location,
                        restaurant.phone,
                        restaurant.snsLink,
                        restaurant.blogLink,
                        restaurant.totalTableCount
                ))
                .from(restaurant)
                .where(
                        restaurant.name.like("%" + name + "%")
                )
                .fetch();
    }
}
