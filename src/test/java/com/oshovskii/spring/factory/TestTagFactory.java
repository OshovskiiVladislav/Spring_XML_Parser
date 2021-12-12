package com.oshovskii.spring.factory;

import com.oshovskii.spring.entities.Tag;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

@ActiveProfiles("unit")
public class TestTagFactory {
    public static Tag createTag(int index) {
        Tag tag = new Tag();
        tag.setId((long) index);
        tag.setContent("Content " + index);
        tag.setName("Tag_name " + index);
        return tag;
    }

    public static List<Tag> createListTags(int listSize) {
        List<Tag> list = new ArrayList<>();
        for (int i = 0; i < listSize; i++) {
            list.add(createTag(i));
        }
        return list;
    }
}
