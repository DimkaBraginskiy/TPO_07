package org.example.tpo_07.serializable;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;

public class CodeSnippet implements Serializable {

    private String id;
    private String originalCode;
    private String formattedCode;
    private LocalDateTime expirationDate;

    public CodeSnippet(String id, String originalCode, String formattedCode, LocalDateTime expirationDate) {
        this.id = id;
        this.originalCode = originalCode;
        this.formattedCode = formattedCode;
        this.expirationDate = expirationDate;
    }

    public String getId() {
        return id;
    }

    public String getOriginalCode() {
        return originalCode;
    }

    public String getFormattedCode() {
        return formattedCode;
    }

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setOriginalCode(String originalCode) {
        this.originalCode = originalCode;
    }

    public void setFormattedCode(String formattedCode) {
        this.formattedCode = formattedCode;
    }

    public void setExpirationDate(LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }
}
