package org.example.tpo_07.service;

import org.example.tpo_07.serializable.CodeSnippet;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Service
public class CodeSnippetService {

    private final String FILE_PATH = "snippets.ser";


    public void saveCodeSnippet(String id, String originalCode, String formattedCode, LocalDate expDate) {
        Map<String, CodeSnippet> snippets = new HashMap<>();


        CodeSnippet snippet = new CodeSnippet(id, originalCode, formattedCode, expDate);


    }

    public CodeSnippet loadSnippet(String id) {
        return null;
    }
}
