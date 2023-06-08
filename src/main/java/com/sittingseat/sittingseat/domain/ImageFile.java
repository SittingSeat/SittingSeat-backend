package com.sittingseat.sittingseat.domain;

import com.sittingseat.sittingseat.enums.ImageEnum;
import com.sittingseat.sittingseat.shopkeeper.domain.Restaurant;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ImageFile extends BaseEntity{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_file_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    private ImageEnum imageType;

    private String s3ImagePath;
    private String originalImageName;

    @Builder
    public ImageFile(String s3ImagePath, String originalImageName, Restaurant restaurant, ImageEnum imageType){
        this.s3ImagePath = s3ImagePath;
        this.originalImageName = originalImageName;
        this.restaurant = restaurant;
        this.imageType = imageType;
    }
}
