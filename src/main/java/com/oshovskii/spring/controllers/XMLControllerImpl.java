package com.oshovskii.spring.controllers;

import com.oshovskii.spring.dto.XMLFileDto;
import com.oshovskii.spring.dto.TagDto;
import com.oshovskii.spring.entities.XMLFile;
import com.oshovskii.spring.entities.Tag;
import com.oshovskii.spring.services.XMLService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class XMLControllerImpl implements XMLController {
    private final XMLService xmlService;
    private final ModelMapper modelMapper;

    @Override
    public void saveXml(@RequestParam final MultipartFile file) {
        xmlService.saveXml(file);
    }

    @Override
    public List<TagDto> getFileContent(@RequestParam final MultipartFile file) {
        List<Tag> tagList = xmlService.parseTags(file);
        return tagList
                .stream()
                .map(tag -> modelMapper.map(tag, TagDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<TagDto> getTagsByFileId(@PathVariable final Long id) {
        List<Tag> tagList = xmlService.findTagsByXMLFileId(id);
        return tagList
                .stream()
                .map(tag -> modelMapper.map(tag, TagDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<XMLFileDto> getAllXMLFiles() {
        List<XMLFile> xmlFileList = xmlService.findAllXMLFiles();
        return xmlFileList
                .stream()
                .map(xmlFile -> modelMapper.map(xmlFile, XMLFileDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public XMLFileDto getXMLFileById(@PathVariable final Long id) {
        return modelMapper.map(xmlService.findXMLFileById(id), XMLFileDto.class);
    }
}
