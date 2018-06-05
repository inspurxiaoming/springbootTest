package com.chengym.home.Controller;

import com.chengym.home.Bean.Message;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/info")
public class GetMessageController {

    @RequestMapping(value = "")
    public String index(Model model){
        model.addAttribute("message",new Message());
        return "info";
    }


    @RequestMapping(value = "/add")
    public String save(@ModelAttribute(value="message") Message message) {
        System.out.println("123");
        return "";
    }
}
