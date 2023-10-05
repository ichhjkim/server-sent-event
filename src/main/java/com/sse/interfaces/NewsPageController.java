package com.sse.interfaces;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class NewsPageController {

    @RequestMapping("news/page")
    public ModelAndView getNewsPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("news");
        return modelAndView;
    }
}
