package com.fleckinger.noteapp;

import com.fleckinger.noteapp.config.WebAppInitializer;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;


@ComponentScan(basePackages = "com.fleckinger.noteapp")
public class NoteAppApplication {

    public static void main(String[] args) throws ClassNotFoundException {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(WebAppInitializer.class);
        context.register();
    }

}
