package org.example.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static java.lang.Math.round;

@Controller
@RequestMapping("/first")
public class FirstController {

    @GetMapping("/calculator")
    public String helloPage(@RequestParam(value = "value1", required = false) int value1,
                            @RequestParam(value = "value2", required = false) int value2,
                            @RequestParam(value = "op", required = false) String op,
                            Model model) {
        double result = 0;
        switch (op) {
            case "add":
              result = value1 + value2;
              break;
            case "sub":
                result = value1 - value2;
                break;
            case "mul":
                result = value1 * value2;
                break;
            case "div":
                if (value2 != 0) result = (double)value1 / (double)value2;
                else result = 0;
                break;
            default:
                result = 0;
                break;
        }
        model.addAttribute("result", result);
        System.out.println(result);

        return "first/calculator";
    }

    @GetMapping("/goodbye")
    public String goodByePage(@RequestParam(value = "name", required = false) String name,
                              @RequestParam(value = "surname", required = false) String surname) {
        System.out.println("GoodBye " +name +" " + surname);
        return "first/goodbye";
    }
}
