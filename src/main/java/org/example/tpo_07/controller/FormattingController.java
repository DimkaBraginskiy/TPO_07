package org.example.tpo_07.controller;

import org.example.tpo_07.serializable.CodeSnippet;
import org.example.tpo_07.service.CodeSnippetService;
import org.example.tpo_07.service.FormattingService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String text(@RequestParam(required = false) String submittedCode, Model model) {

        model.addAttribute("submittedCode", submittedCode);
        model.addAttribute("originalCode", submittedCode);


        try{
            String formatted = formattingService.format(submittedCode);
            model.addAttribute("formattedCode", formatted);
        }catch(RuntimeException e){
            model.addAttribute("errorMessage", e.getMessage());
        }


        return "formatting";
    }

    @GetMapping("/load")
    public String loadSnippet(@RequestParam String codeId, Model model){
        CodeSnippet snippet = codeSnippetService.loadSnippet(codeId);

        if(snippet != null){
            model.addAttribute("submittedCode", snippet.getOriginalCode());
            model.addAttribute("originalCode", snippet.getOriginalCode());
            model.addAttribute("formattedCode", snippet.getFormattedCode());
        }else{
            model.addAttribute("errorMessage", "CodeSnippet of ID: " + codeId + " not found");
        }

        return "formatting";
    }
}
