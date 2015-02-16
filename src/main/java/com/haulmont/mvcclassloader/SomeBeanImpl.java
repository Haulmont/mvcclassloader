package com.haulmont.mvcclassloader;

import org.springframework.stereotype.Component;

/**
 * Created by Degtyarjov Eugene
 * Date: 14.02.15
 * $Id$
 */
@Component("someBean")
public class SomeBeanImpl implements SomeBean {
    @Override
    public String get() {
        return "not reloaded";
    }
}
