package com.wasu.es;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * Created by MASTER_L on 2017/12/27.
 */
public class JspInitializer extends SpringBootServletInitializer {
    @Override
    protected final SpringApplicationBuilder configure(final SpringApplicationBuilder application) {
        return application.sources(StatWebApplication.class);
    }
}
