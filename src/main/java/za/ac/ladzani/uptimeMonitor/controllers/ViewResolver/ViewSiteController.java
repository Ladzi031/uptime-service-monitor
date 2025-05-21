package za.ac.ladzani.uptimeMonitor.controllers.ViewResolver;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class ViewSiteController {

    @GetMapping()
    public String getHomePage() {
        return "index.html";
    }
}
