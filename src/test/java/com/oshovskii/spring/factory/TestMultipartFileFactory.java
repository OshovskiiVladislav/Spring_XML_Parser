package com.oshovskii.spring.factory;

import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("unit")
public class TestMultipartFileFactory {
    public static MockMultipartFile createMultipartFile(String xmlContent, int index) {
        return new MockMultipartFile(
                 "file",
                "hello" + index + ".txt",
                MediaType.TEXT_XML_VALUE,
                xmlContent.getBytes()
        );
    }
}
