package com.oshovskii.spring.repositories;

import com.oshovskii.spring.entities.Tag;
import com.oshovskii.spring.entities.XMLFile;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.oshovskii.spring.factory.TestTagFactory.createTag;
import static com.oshovskii.spring.factory.TestXMLFileFactory.createXMLFile;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("unit")
@Transactional
public class RepositoryTest {
    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private XmlRepository xmlRepository;

    @DisplayName("Save tag test in tagRepository")
    @Test
    public void save_validTag_shouldReturnTag() {
        // Config
        final Tag tag = createTag(1);
        tag.setId(null);
        tag.setXMLFile(null);

        // Call
        final Tag result = tagRepository.save(tag);

        // Verify
        assertEquals(tag, result);
    }

    @DisplayName("Delete tag test in tagRepository")
    @Test
    public void delete_validTag_shouldReturnNull() {
        // Config
        final Tag tag = createTag(1);
        tag.setId(null);
        tag.setXMLFile(null);

        tagRepository.save(tag);

        // Call
        tagRepository.delete(tag);

        // Verify
        final Tag result = tagRepository.findById(tag.getId()).orElse(null);
        assertNull(result);
    }

    @DisplayName("Delete incorrect tag test in tagRepository")
    @Test
    public void save_invalidTag_shouldThrowDataIntegrityViolationException() {
        // Config
        final Tag tag = createTag(1);
        tag.setId(null);
        tag.setXMLFile(null);
        tag.setName(null);

        // Call and verify
        assertThrows(DataIntegrityViolationException.class, () -> tagRepository.save(tag));
    }

    @DisplayName("Find all tags by XML File Id test in tagRepository")
    @Test
    public void findAllByXMLFileId_TagId_shouldReturnListTag() {
        // Config
        final XMLFile XMLFile = createXMLFile(1);
        XMLFile.setId(null);

        xmlRepository.save(XMLFile);

        final Tag tag1 = createTag(1);
        tag1.setId(null);
        tag1.setXMLFile(XMLFile);

        final Tag tag2 = createTag(2);
        tag2.setId(null);
        tag2.setXMLFile(XMLFile);

        final List<Tag> listTags = new ArrayList<>();
        listTags.add(tag1);
        listTags.add(tag2);

        tagRepository.saveAll(listTags);

        // Call
        final List<Tag> resultList = tagRepository.findAllByXMLFileId(XMLFile.getId());

        // Verify
        assertEquals(listTags, resultList);
    }

    @DisplayName("Save valid XML file test in xmlRepository")
    @Test
    public void save_validXMLFile_shouldReturnXMLFile() {
        // Config
        final XMLFile xmlFile = createXMLFile(1);
        xmlFile.setId(null);

        // Call
        final XMLFile result = xmlRepository.save(xmlFile);

        // Verify
        assertEquals(xmlFile, result);
    }

    @DisplayName("Delete valid XML file test in xmlRepository")
    @Test
    public void delete_validXMLFile_shouldReturnNull() {
        // Config
        final XMLFile xmlFile = createXMLFile(1);
        xmlFile.setId(null);

        xmlRepository.save(xmlFile);

        // Call
        xmlRepository.delete(xmlFile);

        // Verify
        final XMLFile result = xmlRepository.findById(xmlFile.getId()).orElse(null);
        assertNull(result);
    }

    @DisplayName("Save invalid XML file test in xmlRepository")
    @Test
    public void save_invalidXML_shouldThrowDataIntegrityViolationException() {
        // Config
        final XMLFile xmlFile = createXMLFile(1);
        xmlFile.setId(null);
        xmlFile.setData(null);

        // Call and verify
        assertThrows(DataIntegrityViolationException.class, () -> xmlRepository.save(xmlFile));
    }
}
