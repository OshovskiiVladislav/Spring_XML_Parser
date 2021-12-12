package com.oshovskii.spring.controllers;

import com.oshovskii.spring.dto.XMLFileDto;
import com.oshovskii.spring.dto.TagDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequestMapping("/api/v1/xml")
public interface XMLController {

    @PostMapping("/upload")
    void saveXml(MultipartFile file);

    @PostMapping("/view/content")
    List<TagDto> getFileContent(MultipartFile file);

    @GetMapping("/view/{id}/tags")
    List<TagDto> getTagsByFileId(Long id);

    @GetMapping("/view")
    List<XMLFileDto> getAllXMLFiles();

    @GetMapping("/view/{id}")
    XMLFileDto getXMLFileById(Long id);
}
