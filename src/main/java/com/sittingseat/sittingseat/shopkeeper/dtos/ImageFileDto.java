package com.sittingseat.sittingseat.shopkeeper.dtos;

import com.sittingseat.sittingseat.enums.ImageEnum;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ImageFileDto {
    private String imagePath;
    private ImageEnum type;
}
