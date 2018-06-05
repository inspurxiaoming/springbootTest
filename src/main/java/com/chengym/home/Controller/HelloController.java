package com.chengym.home.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/")
public class HelloController {

    @RequestMapping(value = "/")
    public String index(){
        return "hello";
    }


    @RequestMapping(value = "/video")
    public String video(){
        return "video";
    }
}
