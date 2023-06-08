package com.sittingseat.sittingseat.shopkeeper.service;

import com.sittingseat.sittingseat.domain.ImageFile;
import com.sittingseat.sittingseat.enums.ImageEnum;
import com.sittingseat.sittingseat.shopkeeper.domain.Restaurant;
import com.sittingseat.sittingseat.shopkeeper.repository.ImageFileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ImageFileService {
    private final ImageFileRepository imageFileRepository;

    @Transactional
    public void saveImage(ImageEnum type, String s3ImagePath, String originalImageName, Restaurant restaurant){
        ImageFile image = ImageFile.builder()
                .imageType(type)
                .originalImageName(originalImageName)
                .s3ImagePath(s3ImagePath)
                .restaurant(restaurant)
                .build();
        imageFileRepository.save(image);
    }

}
