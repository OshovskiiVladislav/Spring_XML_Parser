package com.oshovskii.spring.parser;

import com.oshovskii.spring.entities.Tag;
import com.oshovskii.spring.exceptions_handling.ParserException;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;
import java.util.ArrayList;
import java.util.List;

@Component
public class DOMParser {

    private static final String ROOT_NAME = "User";
    private static final String TAG_NAME = "Name";
    private static final String TAG_AGE = "Age";
    private static final String TAG_HOBBY = "Hobby";

    public List<Tag> parse(MultipartFile multipartFile) {
        List<Tag> tags = new ArrayList<>();
        Document document;
        try {
            document = buildDocument(multipartFile);
        } catch (Exception e) {
            throw new ParserException("Build document exception");
        }

        tags.add(new Tag(ROOT_NAME, null));

        Node rootNode = document.getFirstChild();
        NodeList rootChilds = rootNode.getChildNodes();

        for (int i = 0; i < rootChilds.getLength(); i++) {
            if (rootChilds.item(i).getNodeType() != Node.ELEMENT_NODE) {
                continue;
            }
            switch (rootChilds.item(i).getNodeName()) {
                case TAG_NAME: {
                    tags.add(new Tag(TAG_NAME, rootChilds.item(i).getTextContent()));
                    break;
                }
                case TAG_AGE: {
                    tags.add(new Tag(TAG_AGE, rootChilds.item(i).getTextContent()));
                    break;
                }
                case TAG_HOBBY: {
                    tags.add(new Tag(TAG_HOBBY, rootChilds.item(i).getTextContent()));
                    break;
                }
                default:
                    throw new ParserException("Unexpected tag");
            }
        }

        StringBuilder str = new StringBuilder();
        tags.subList(1, tags.size()).forEach(t -> str.append(t.toString()));
        tags.get(0).setContent(str.toString());

        return tags;
    }

    private Document buildDocument(MultipartFile multipartFile) throws Exception {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        return documentBuilderFactory.newDocumentBuilder().parse(multipartFile.getInputStream());
    }
}
