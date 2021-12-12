package com.oshovskii.spring.services;

import com.oshovskii.spring.entities.XMLFile;
import com.oshovskii.spring.entities.Tag;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface XMLService {
    void saveXml(MultipartFile file);

    List<XMLFile> findAllXMLFiles();

    XMLFile findXMLFileById(Long id);

    List<Tag> parseTags(MultipartFile file);

    List<Tag> findTagsByXMLFileId(Long rawFileId);
}
