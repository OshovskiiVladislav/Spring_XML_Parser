package com.oshovskii.spring.parser;

import com.oshovskii.spring.SpringParserApplication;
import com.oshovskii.spring.entities.Tag;
import com.oshovskii.spring.exceptions_handling.ParserException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static com.oshovskii.spring.factory.TestMultipartFileFactory.createMultipartFile;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = SpringParserApplication.class)
@ActiveProfiles("unit")
public class DOMParserTest {
    @Autowired
    private DOMParser domParser;

    private final String XML_CONTENT = "<User>\n" +
            "    <Name>Petya</Name>\n" +
            "    <Age>30</Age>\n" +
            "    <Hobby>JavaScript</Hobby>\n" +
            "</User>";
    private final String INCORRECT_XML_CONTENT = "Incorrect";

    @DisplayName("Parse correct MockMultipartFile test")
    @Test
    public void parse_MultipartFile_shouldReturnListTag() {
        // Config
        MockMultipartFile mockMultipartFile = createMultipartFile(XML_CONTENT,1);

        // Call
        List<Tag> result = domParser.parse(mockMultipartFile);

        // Verify
        assertEquals(4, result.size());
    }

    @DisplayName("Parse incorrect MockMultipartFile test")
    @Test
    public void parse_IncorrectMultipartFile_shouldReturnException(){
        // Config
        MockMultipartFile mockMultipartFile = createMultipartFile(INCORRECT_XML_CONTENT,1);

        // Call and Verify
        assertThrows(ParserException.class,() -> domParser.parse(mockMultipartFile));
    }
}
