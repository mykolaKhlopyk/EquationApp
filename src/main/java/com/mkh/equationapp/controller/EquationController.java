package com.mkh.equationapp.controller;

import com.mkh.equationapp.domain.Equation;
import com.mkh.equationapp.service.EquationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@AllArgsConstructor
@RequestMapping("/equation")
public class EquationController {

    private final EquationService equationService;

    @GetMapping("/input")
    public String getInputEquationPage(@RequestParam(value = "error", required = false) String error, Model model){
        model.addAttribute("error", error);
        return "inputEquation";
    }

    @PostMapping("/input")
    public String saveEquation(@ModelAttribute("equationStr") String equationStr) {
        try {
            Equation equation = new Equation(equationStr);
            equationService.save(equation);
        }catch (Exception exception){
            return "redirect:/equation/input?error="+exception.getMessage();
        }
        return "index";
    }


}
