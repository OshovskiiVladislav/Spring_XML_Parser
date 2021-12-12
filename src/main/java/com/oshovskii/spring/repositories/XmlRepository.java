package com.oshovskii.spring.repositories;

import com.oshovskii.spring.entities.XMLFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface XmlRepository extends JpaRepository<XMLFile, Long> {
}
