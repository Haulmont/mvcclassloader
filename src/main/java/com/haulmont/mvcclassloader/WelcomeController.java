package com.haulmont.mvcclassloader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller("welcomeController")
public class WelcomeController {
    @Autowired
    protected SomeBean someBean;

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public ModelAndView welcome() {
        ModelAndView model = new ModelAndView();
        model.setViewName("index");
        model.addObject("version", someBean.get());

        return model;

    }

}