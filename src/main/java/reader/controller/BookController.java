package reader.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import reader.entity.Category;
import reader.service.CategoryService;

import javax.annotation.Resource;
import java.util.List;

@Controller
public class BookController {
    @Resource
    private CategoryService categoryService;

    @GetMapping("/")
    public ModelAndView showIndex(){
        ModelAndView mav=new ModelAndView("/index");
        List<Category> list=categoryService.selectAll();
        mav.addObject("categoryList",list);
        return mav;
    }
}
