package com.oshovskii.spring.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oshovskii.spring.SpringParserApplication;
import com.oshovskii.spring.config.ConfigApp;
import com.oshovskii.spring.dto.TagDto;
import com.oshovskii.spring.dto.XMLFileDto;
import com.oshovskii.spring.entities.Tag;
import com.oshovskii.spring.entities.XMLFile;
import com.oshovskii.spring.services.XMLService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.oshovskii.spring.factory.TestMultipartFileFactory.createMultipartFile;
import static com.oshovskii.spring.factory.TestTagDtoFactory.createTagDto;
import static com.oshovskii.spring.factory.TestTagFactory.createTag;
import static com.oshovskii.spring.factory.TestXMLFileDtoFactory.createXMLFileDto;
import static com.oshovskii.spring.factory.TestXMLFileFactory.createXMLFile;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = {XMLController.class})
@AutoConfigureMockMvc
@ContextConfiguration(classes = {ConfigApp.class, SpringParserApplication.class})
@ActiveProfiles("unit")
public class XMLControllerImplTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    public ObjectMapper objectMapper;

    @MockBean
    private XMLService xmlService;

    @MockBean
    private ModelMapper modelMapperMock;

    private final String XML_CONTENT = "Correct XML content";

    @DisplayName("Save valid XML test")
    @Test
    public void saveXml_validXML_shouldReturnNull() throws Exception {
        // Config
        final MockMultipartFile mockMultipartFile = createMultipartFile(XML_CONTENT, 1);

        doNothing().when(xmlService).saveXml(mockMultipartFile);

        // Call and verify
        mockMvc.perform(multipart("/api/v1/xml/upload")
                .file(mockMultipartFile))
                .andExpect(status().isOk());

        verify(xmlService, times(1)).saveXml(mockMultipartFile);
    }

    @DisplayName("Get file Content test")
    @Test
    public void getFileContentByMultipartFile_validMultipartFile_shouldReturnListTagDto() throws Exception {
        // Config
        final MockMultipartFile mockMultipartFile = createMultipartFile(XML_CONTENT, 2);
        final TagDto tagDto = createTagDto(1);
        final Tag tag = createTag(1);
        final List<Tag> tagList = List.of(tag);

        when(xmlService.parseTags(mockMultipartFile)).thenReturn(tagList);
        when(modelMapperMock.map(tag, TagDto.class)).thenReturn(tagDto);

        final String targetJson = objectMapper.writeValueAsString(tagList);

        // Call and verify
        mockMvc.perform(multipart("/api/v1/xml/view/content")
                .file(mockMultipartFile))
                .andExpect(content().string(targetJson))
                .andExpect(status().isOk());
    }

    @DisplayName("Get tags by file id test")
    @Test
    public void getTagsByFileId_validXMLFileId_shouldReturnListTagDto() throws Exception {
        // Config
        final Long xmlFileId = 1L;
        final TagDto tagDto = createTagDto(1);
        final Tag tag = createTag(1);
        final List<Tag> tagList = List.of(tag);

        when(xmlService.findTagsByXMLFileId(xmlFileId)).thenReturn(tagList);
        when(modelMapperMock.map(tag, TagDto.class)).thenReturn(tagDto);

        final String targetJson = objectMapper.writeValueAsString(tagList);

        // Call and verify
        mockMvc.perform(get("/api/v1/xml/view/{xmlFileId}/tags", xmlFileId))
                .andExpect(content().string(targetJson))
                .andExpect(status().isOk());
    }

    @DisplayName("Get all XML files test")
    @Test
    public void getAllXMLFiles_void_shouldReturnAllXMLFileDto() throws Exception {
        // Config
        final XMLFile xmlFile = createXMLFile(1);
        final XMLFileDto xmlFileDto = createXMLFileDto(1);
        final List<XMLFile> xmlFileList = List.of(xmlFile);

        when(xmlService.findAllXMLFiles()).thenReturn(xmlFileList);
        when(modelMapperMock.map(xmlFile, XMLFileDto.class)).thenReturn(xmlFileDto);

        final String targetJson = objectMapper.writeValueAsString(xmlFileList);

        // Call and verify
        mockMvc.perform(get("/api/v1/xml/view"))
                .andExpect(content().string(targetJson))
                .andExpect(status().isOk());
    }

    @DisplayName("Get XML file by id test")
    @Test
    public void getXMLFileById_validLongId_shouldReturnXMLFileDto() throws Exception {
        // Config
        final Long xmlFileId = 1L;
        final XMLFile xmlFile = createXMLFile(1);
        final XMLFileDto xmlFileDto = createXMLFileDto(1);

        when(xmlService.findXMLFileById(xmlFileId)).thenReturn(xmlFile);
        when(modelMapperMock.map(xmlFile, XMLFileDto.class)).thenReturn(xmlFileDto);

        final String targetJson = objectMapper.writeValueAsString(xmlFileDto);

        // Call and verify
        mockMvc.perform(get("/api/v1/xml/view/{xmlFileId}", xmlFileId))
                .andExpect(content().string(targetJson))
                .andExpect(status().isOk());
    }
}
