package com.oshovskii.spring.dto;

import com.oshovskii.spring.entities.XMLFile;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class TagDto {
    private Long id;
    private String name;
    private String content;
    private XMLFile XMLFile;
}
