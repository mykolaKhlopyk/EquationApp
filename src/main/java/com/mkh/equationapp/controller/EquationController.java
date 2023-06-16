package com.mkh.equationapp.controller;

import com.mkh.equationapp.domain.Equation;
import com.mkh.equationapp.domain.exceptions.InputException;
import com.mkh.equationapp.service.EquationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/equation")
public class EquationController {

    private final EquationService equationService;

    @GetMapping("/input")
    public String getInputEquationPage(@RequestParam(value = "error", required = false) String error, Model model){
        model.addAttribute("error", error);
        return "input-equation";
    }

    @PostMapping("/input")
    public String saveEquation(@ModelAttribute("equationStr") String equationStr) {
        try {
            Equation equation = new Equation(equationStr);
            equationService.save(equation);
        }catch (Exception exception){
            return "redirect:/equation/input?error="+exception.getMessage();
        }
        return "redirect:/equation/index";
    }

    @GetMapping("/index")
    public String getAllEquationsPage(Model model){
        model.addAttribute("equations", equationService.getAll());
        return "index";
    }

    @GetMapping("/{id}/roots")
    public String getEquationPage(@PathVariable("id")long id, @RequestParam(value = "error", required = false) String error, Model model){
        Equation equation = equationService.getEquationById(id);
        model.addAttribute("error", error);
        model.addAttribute("equation", equation);
        return "show-equation";
    }

    @PostMapping("/{equation_id}/roots/{root_id}")
    public String deleteRoot(@PathVariable("equation_id")long equation_id, @PathVariable("root_id")long root_id){
        equationService.deleteRoot(root_id);
        return "redirect:/equation/"+equation_id+"/roots";
    }

    @PostMapping("/{equation_id}/roots")
    public String addRoot(@PathVariable("equation_id")long equation_id, @ModelAttribute("newRoot") String newRootStr){
        try{
            equationService.addRoot(equation_id, newRootStr);
        }catch (NumberFormatException exception){
            return "redirect:/equation/"+equation_id+"/roots?error=input correct number!";
        }catch (InputException exception){
            return "redirect:/equation/"+equation_id+"/roots?error="+exception.getMessage();
        }
        return "redirect:/equation/"+equation_id+"/roots";
    }

    @GetMapping("/find-equations-with-one-root")
    public String findEquationsWithOneRoot(Model model){
        List<Equation> equationsWithOneRoot = equationService.getEquationsWithOneRoot();
        model.addAttribute("equations", equationsWithOneRoot);
        return "index";
    }



}
