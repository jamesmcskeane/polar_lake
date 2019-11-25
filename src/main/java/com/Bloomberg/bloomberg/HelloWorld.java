package com.Bloomberg.bloomberg;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/")
@RestController
public class HelloWorld {

    @RequestMapping(method = RequestMethod.GET)
    public String helloWorld()
    {
        return "Hello World";
    }

}
