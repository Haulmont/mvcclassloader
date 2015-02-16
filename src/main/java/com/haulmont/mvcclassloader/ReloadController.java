package com.haulmont.mvcclassloader;

import com.haulmont.javacl.JavaClassLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ReloadController {
    @Autowired
    protected JavaClassLoader javaClassLoader;

    @RequestMapping(value = "/reload", method = RequestMethod.GET)
    public ModelAndView welcome() {
        try {
            javaClassLoader.loadClass("com.haulmont.mvcclassloader.SomeBeanImpl");
            javaClassLoader.loadClass("com.haulmont.mvcclassloader.WelcomeController");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        ModelAndView model = new ModelAndView();
        model.setViewName("reload");
        model.addObject("result", "ok");

        return model;

    }

}