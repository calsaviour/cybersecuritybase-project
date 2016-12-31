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


@Controller
public class BlogController {

    @Autowired
    private BlogRepository blogRepository;

    @RequestMapping(value = "/blogs")
    public String postBlogs(Model model) {
        model.addAttribute("blogs", blogRepository.findAll());
        return "cross-site-scripting";
    }

    @Transactional
    @RequestMapping(value = "/blogs", method = RequestMethod.POST)
    public String insertBlogJpa(@RequestParam String name, @RequestParam String comment) {
        Blog blog = new Blog();
        blog.setName(name);
        blog.setComment(comment);
        blogRepository.save(blog);
        return "redirect:/cross-site-scripting";
    }
}
