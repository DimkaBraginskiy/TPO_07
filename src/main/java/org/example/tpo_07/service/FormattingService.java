package org.example.tpo_07.service;

import com.google.googlejavaformat.java.Formatter;
import com.google.googlejavaformat.java.FormatterException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.fasterxml.jackson.databind.type.LogicalType.Collection;

@Service
public class FormattingService {

    public String format(String code) {

        if (code == null || code.trim().isEmpty()) {
            throw new RuntimeException("Code formatting failed. Can not format an Empty field.");
        }

        try {
            Formatter formatter = new Formatter();
            return formatter.formatSource(code);
        } catch (FormatterException e) {
            throw new RuntimeException("Code formatting failed. Please try to paste the code with a correct syntax.");
        }
    }
}