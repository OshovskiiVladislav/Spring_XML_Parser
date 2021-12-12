package com.oshovskii.spring.integration;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("integration")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class XMLControllerImplIntegrationTest {
    @Autowired
    private MockMvc mvc;

    private final static String CORRECT_XML = "<User>\n" +
            "    <Name>Petya</Name>\n" +
            "    <Age>30</Age>\n" +
            "    <Hobby>JavaScript</Hobby>\n" +
            "</User>";

    @Test
    @DisplayName("Save valid XML test")
    @Order(1)
    public void saveXml_validXML_shouldReturnNull() throws Exception {
        MockMultipartFile mockMultipartFile = new MockMultipartFile(
                "file",
                "filename.txt",
                "text/plain",
                CORRECT_XML.getBytes());

        mvc.perform(multipart("/api/v1/xml/upload")
                .file(mockMultipartFile))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Get XML file by id")
    @Order(2)
    public void getXmlFile_validLongIdXmlFile_shouldReturnXmlFileDto() throws Exception {
        final Long xmlfileId = 1L;

        mvc.perform(get("/api/v1/xml/view/{xmlfileId}", xmlfileId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", is(CORRECT_XML)))
                .andExpect(jsonPath("$.id", is(1)))
                .andDo(MockMvcResultHandlers.print());
    }
}
