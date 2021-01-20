package weather.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller

public class JavaScriptController {

        @GetMapping("JS/external")
        public String testJs() {
            return "/external";
        }

}
