package com.jeeplus.modules.cultural.web.fore;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("to")
public class PageController {
    @RequestMapping("{toshow}")
    public String toShow(@PathVariable(value="toshow") String toshow) {
        return "modules/cultural/fore/"+toshow;
    }
}
