package org.example.tpo_07.controller;

import org.example.tpo_07.service.FormattingService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class FormattingController {

    private final FormattingService formattingService;

    public FormattingController(FormattingService formattingService) {
        this.formattingService = formattingService;
    }

    @GetMapping("/")
    public String showForm(){
        return "formatting";
    }

    @PostMapping("/format")
    public String text(@RequestParam(required = false) String submittedCode, Model model) {

        model.addAttribute("submittedCode", submittedCode);
        model.addAttribute("originalCode", submittedCode);


        String formatted = formattingService.format(submittedCode);
        model.addAttribute("formattedCode", formatted);

        return "formatting";
    }
}
