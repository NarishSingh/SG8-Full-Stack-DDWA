package sg.springboot;

import java.util.ArrayList;
import java.util.List;
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
     * GET at http://localhost:8080/test - add obj's to page and get page name
     *
     * @param model {Model} put data to render to page, add as k/v pairs
     * @return {String} subdomain
     */
    @GetMapping("test")
    public String testPage(Model model) {
        model.addAttribute("number", number);
        model.addAttribute("firstName", name);

        return "test";
    }

    /**
     * POST at http://localhost:8080/test - retrieve form inputs and store to
     * variables, force redirect to /test
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

    /**
     * GET at http://localhost:8080/testList - create a list and add numbers to
     * it
     *
     * @param model {Model} obj with a list of numbers as a field
     * @return {String} the subdomain
     */
    @GetMapping("testList")
    public String testList(Model model) {
        List<Integer> numbers = new ArrayList<>();

        numbers.add(0);
        numbers.add(10);
        numbers.add(6);
        numbers.add(35);

        model.addAttribute("numberList", numbers);

        return "testList";
    }

    /**
     * GET at http://localhost:8080/testConditional - create a obj for use with
     * a condition
     *
     * @param model {Model} obj with a boolean, int, and string field
     * @return the subdomain
     */
    @GetMapping("testConditional")
    public String testConditional(Model model) {
        model.addAttribute("truth", true);
        model.addAttribute("theNumber", 33);
        model.addAttribute("aString", "testing");

        return "testConditional";
    }
}
