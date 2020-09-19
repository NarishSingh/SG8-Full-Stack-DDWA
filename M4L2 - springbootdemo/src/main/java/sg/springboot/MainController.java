/*
http://localhost:8080/test
 */
package sg.springboot;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MainController {

    String name = "John";
    int number = 42;

    /**
     * GET - add obj's to page and get page name
     *
     * @param model {Model} put data to render to page, add as k/v pairs
     * @return {String} name of the page
     */
    @GetMapping("test")
    public String testPage(Model model) {
        model.addAttribute("number", number);
        model.addAttribute("firstName", name);

        return "test";
    }

    /**
     * POST - retrieve form inputs and store to variables, force redirect to
     * /test
     *
     * @param request {HttpServletRequest} used to pull the form data
     * @return {String} redirect back to base
     */
    @PostMapping("testForm")
    public String testForm(HttpServletRequest request) {
        name = request.getParameter("formFirstName");
        number = Integer.parseInt(request.getParameter("formNumber"));

        return "redirect:/test";
    }
}
