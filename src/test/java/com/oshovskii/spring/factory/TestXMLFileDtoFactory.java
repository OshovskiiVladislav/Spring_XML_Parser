package com.oshovskii.spring.factory;

import com.oshovskii.spring.dto.XMLFileDto;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("unit")
public class TestXMLFileDtoFactory {
    public static XMLFileDto createXMLFileDto(int index) {
        XMLFileDto xmlFileDto = new XMLFileDto();
        xmlFileDto.setId((long) index);
        xmlFileDto.setData("Data " + index);
        return xmlFileDto;
    }
}
