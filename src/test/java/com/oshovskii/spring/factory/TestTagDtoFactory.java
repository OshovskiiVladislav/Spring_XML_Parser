package com.oshovskii.spring.factory;

import com.oshovskii.spring.dto.TagDto;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("unit")
public class TestTagDtoFactory {
    public static TagDto createTagDto(int index) {
        TagDto tagDto = new TagDto();
        tagDto.setId((long) index);
        tagDto.setContent("Content " + index);
        tagDto.setName("Tag_name " + index);
        return tagDto;
    }
}
