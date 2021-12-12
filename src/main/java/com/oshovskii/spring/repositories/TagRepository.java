package com.oshovskii.spring.repositories;

import com.oshovskii.spring.entities.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Long> {
    List<Tag> findAllByXMLFileId(Long id);
}
