package com.oshovskii.spring.factory;

import com.oshovskii.spring.entities.XMLFile;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

@ActiveProfiles("unit")
public class TestXMLFileFactory {
    public static XMLFile createXMLFile(int index) {
        XMLFile xmlFile = new XMLFile();
        xmlFile.setId((long)index);
        xmlFile.setData("Data " + index);
        return xmlFile;
    }

    public static List<XMLFile> createListXML(int listSize) {
        List<XMLFile> xmlFileList = new ArrayList<>();
        for (int i = 0; i < listSize; i++) {
            xmlFileList.add(createXMLFile(i));
        }
        return xmlFileList;
    }
}
