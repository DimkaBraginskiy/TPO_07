package org.example.tpo_07.controller;

import org.example.tpo_07.serializable.CodeSnippet;
import org.example.tpo_07.service.CodeSnippetService;
import org.example.tpo_07.service.FormattingService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class FormattingController {

    private final FormattingService formattingService;
    private final CodeSnippetService codeSnippetService;

    public FormattingController(FormattingService formattingService, CodeSnippetService codeSnippetService) {
        this.formattingService = formattingService;
        this.codeSnippetService = codeSnippetService;
    }

    @GetMapping("/format")
    public String showForm(){
        return "formatting";
    }

    @PostMapping("/format")
    public String text(@RequestParam String submittedCode,
                       @RequestParam String codeId,
                       @RequestParam long expDateSeconds,
                       Model model) {

        model.addAttribute("submittedCode", submittedCode);
        model.addAttribute("originalCode", submittedCode);


        try{
            String formatted = formattingService.format(submittedCode);
            model.addAttribute("formattedCode", formatted);

            codeSnippetService.saveCodeSnippet(codeId, submittedCode, formatted, expDateSeconds);
            model.addAttribute("successMessage", "Snippet by ID: " + codeId + " saved successfully");
        }catch(RuntimeException e){
            model.addAttribute("errorMessage", e.getMessage());
        }


        return "formatting";
    }

    @GetMapping("/load")
    public String loadSnippet(@RequestParam String codeId, Model model){
        Optional<CodeSnippet> snippet = codeSnippetService.loadSnippet(codeId);

        return snippet.map(s -> {
            model.addAttribute("submittedCode", s.getOriginalCode());
            model.addAttribute("originalCode", s.getOriginalCode());
            model.addAttribute("formattedCode", s.getFormattedCode());
            model.addAttribute("successMessage", "Snippet by ID: " + codeId + " loaded successfully");
            return "formatting";
        }).orElseGet(() -> {
            model.addAttribute("errorMessage", "CodeSnippet of ID: " + codeId +
                    " not found. The snippet has been removed due to expiration date or does not exist.");
            return "formatting";
        });
    }
}
