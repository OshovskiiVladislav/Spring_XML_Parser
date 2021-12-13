package com.oshovskii.spring.services;

import com.oshovskii.spring.entities.Tag;
import com.oshovskii.spring.entities.XMLFile;
import com.oshovskii.spring.parser.DOMParser;
import com.oshovskii.spring.repositories.TagRepository;
import com.oshovskii.spring.repositories.XmlRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Import;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.oshovskii.spring.factory.TestMultipartFileFactory.createMultipartFile;
import static com.oshovskii.spring.factory.TestTagFactory.createListTags;
import static com.oshovskii.spring.factory.TestXMLFileFactory.createListXML;
import static com.oshovskii.spring.factory.TestXMLFileFactory.createXMLFile;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@Import(XMLServiceImpl.class)
@ActiveProfiles("unit")
public class XMLServiceImplTest {
    @MockBean
    private XmlRepository xmlRepositoryMock;

    @MockBean
    private TagRepository tagRepositoryMock;

    @MockBean
    private DOMParser domParserMock;

    @SpyBean
    private XMLService xmlService;

    private final String XML_CONTENT = "Correct XML content";

    @DisplayName("Save XML file test")
    @Test
    public void saveXml_validMultipartFile_shouldReturnNothing() {
        // Config
        final MockMultipartFile mockMultipartFile = createMultipartFile(XML_CONTENT, 2);

        final XMLFile xmlFile = createXMLFile(1);

        when(xmlRepositoryMock.save(any(XMLFile.class))).thenReturn(xmlFile);
        when(tagRepositoryMock.saveAll(any(ArrayList.class))).thenReturn(Collections.EMPTY_LIST);
        when(domParserMock.parse(mockMultipartFile)).thenReturn(createListTags(2));

        // Call
        xmlService.saveXml(mockMultipartFile);

        // Verify
        verify(tagRepositoryMock, Mockito.times(1)).saveAll(ArgumentMatchers.any());
    }

    @DisplayName("Parse tags test")
    @Test
    public void parseTags_validMultipartFile_shouldReturnListTags() {
        // Config
        final MockMultipartFile mockMultipartFile = createMultipartFile(XML_CONTENT, 2);
        final List<Tag> sourceListTag = createListTags(2);

        when(domParserMock.parse(mockMultipartFile)).thenReturn(sourceListTag);

        // Call
        final List<Tag> result = xmlService.parseTags(mockMultipartFile);

        //Verify
        assertEquals(sourceListTag, result);
    }

    @DisplayName("Find all XML files test")
    @Test
    public void findAllXMLFiles_voidInput_shouldReturnListXMLFile() {
        // Config
        final List<XMLFile> sourceXMLFile = createListXML(2);

        when(xmlRepositoryMock.findAll()).thenReturn(sourceXMLFile);

        // Call
        final List<XMLFile> result = xmlService.findAllXMLFiles();

        // Verify
        assertEquals(sourceXMLFile, result);
    }

    @DisplayName("Find XML file by xml file id test")
    @Test
    public void findXMLFileById_validLongXMLFileId_shouldReturnXMLFile() {
        // Config
        XMLFile xmlFile = createXMLFile(1);

        when(xmlRepositoryMock.findById(xmlFile.getId())).thenReturn(Optional.of(xmlFile));

        // Call
        final XMLFile result = xmlService.findXMLFileById(xmlFile.getId());

        // Verify
        assertEquals(xmlFile, result);
    }

    @DisplayName("Find list tags by xml file id test")
    @Test
    public void findTagsByXMLFileId_validLongXMLFileId_shouldReturnListTag() {
        // Config
        final Long XML_FILE_ID = 1L;

        List<Tag> tagList = createListTags(4);

        when(tagRepositoryMock.findAllByXMLFileId(XML_FILE_ID)).thenReturn(tagList);

        // Call
        final List<Tag> result = xmlService.findTagsByXMLFileId(XML_FILE_ID);

        // Verify
        assertEquals(tagList, result);
    }
}
