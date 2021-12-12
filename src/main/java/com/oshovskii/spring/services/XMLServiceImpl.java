package com.oshovskii.spring.services;

import com.oshovskii.spring.entities.XMLFile;
import com.oshovskii.spring.entities.Tag;
import com.oshovskii.spring.exceptions_handling.ResourceNotFoundException;
import com.oshovskii.spring.parser.DOMParser;
import com.oshovskii.spring.repositories.TagRepository;
import com.oshovskii.spring.repositories.XmlRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class XMLServiceImpl implements XMLService {
    private final TagRepository tagRepository;
    private final XmlRepository xmlRepository;
    private final DOMParser parser;

    @Override
    public void saveXml(final MultipartFile file) {
        String data = "";
        try {
            data = new String(file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        final XMLFile XMLFile = new XMLFile();
        XMLFile.setData(data);

        final XMLFile savedFile = xmlRepository.save(XMLFile);

        final List<Tag> tags = parse(file);
        tags.forEach(t -> t.setXMLFile(savedFile));
        saveTags(tags);
    }

    @Override
    public List<Tag> parseTags(final MultipartFile file) {
        return parse(file);
    }

    @Override
    public List<XMLFile> findAllXMLFiles() {
        return xmlRepository.findAll();
    }

    @Override
    public XMLFile findXMLFileById(final Long xmlFileId) {
        return xmlRepository.findById(xmlFileId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(String.format("File with id %s not found!", xmlFileId)));
    }

    @Transactional
    @Override
    public List<Tag> findTagsByXMLFileId(final Long xmlFileId) {
        return tagRepository.findAllByXMLFileId(xmlFileId);
    }

    private List<Tag> parse(final MultipartFile file) {
        return parser.parse(file);
    }

    private void saveTags(final List<Tag> tags) {
        tagRepository.saveAll(tags);
    }
}