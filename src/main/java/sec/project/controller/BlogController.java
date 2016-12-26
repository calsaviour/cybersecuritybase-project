package sec.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import sec.project.domain.Blog;
import sec.project.repository.BlogRepository;

import javax.transaction.Transactional;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Controller
public class BlogController {

    @Autowired
    private BlogRepository blogRepository;

    @RequestMapping(value = "/blogs")
    public String posts(Model model) {
        model.addAttribute("blogs", blogRepository.findAll());
        return "blogs";
    }

    @Transactional
    @RequestMapping(value = "/blogs", method = RequestMethod.POST)
    public String insertJpa(@RequestParam String name, @RequestParam String comment) {
        Blog blog = new Blog();
        blog.setName(name);
        blog.setComment(comment);
        blogRepository.save(blog);
        return "redirect:/blogs";
    }
}
