package com.chengym.home.Controller;

import com.chengym.home.Bean.Message;
import com.chengym.home.Service.GetMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping(value = "/info")
public class GetMessageController {

    @Autowired
    GetMessageService getMessageService;

    @RequestMapping(value = "")
    public String index(Model model){
        model.addAttribute("message",new Message());
        return "info";
    }

    @ResponseBody
    @RequestMapping(value = "/list")
    public List<Message> getListForPage(){

        return getMessageService.getListForPage();
    }

    @RequestMapping(value = "/add")
    public String save(@ModelAttribute(value="message") Message message) {
        System.out.println("123");
        return "";
    }
    @RequestMapping(value = "/update")
    public String update(@ModelAttribute(value="message") Message message){

        return "";
    }

    @RequestMapping(value = "/signIn")
    public String signIn(@ModelAttribute(value="message") Message message){

        return "";
    }
}
