package com.sse.interfaces.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SseViewController {

    @RequestMapping("/sse-view")
    public ModelAndView getSSeView() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("sseView");
        return modelAndView;
    }
}
