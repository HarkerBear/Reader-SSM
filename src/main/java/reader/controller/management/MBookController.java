package reader.controller.management;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import reader.entity.Book;
import reader.service.BookService;
import reader.service.exception.BusinessException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/management/book")
public class MBookController {

    @Resource
    private BookService bookService;

    @GetMapping("/index.html")
    public ModelAndView showBook(){
        return new ModelAndView("/management/book");
    }

    @PostMapping("/upload")
    @ResponseBody
    public Map upload(@RequestParam("img") MultipartFile file, HttpServletRequest request) throws IOException {
        String uploadPath = request.getServletContext().getResource("/").getPath()+"/upload/";
        String fileName = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
        String suffix=file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        String newPath=uploadPath+fileName+suffix;
        file.transferTo(new File(newPath));
        Map result = new HashMap();
        result.put("errno",0);
        result.put("data",new String[]{"/upload/"+fileName+suffix});
        return result;
    }

    @PostMapping("create")
    @ResponseBody
    public Map createBook(Book book) {
        Map result = new HashMap();
        try {
            book.setEvaluationQuantity(0);
            book.setEvaluationScore(0f);
            Document document=Jsoup.parse(book.getDescription());
            Element img=document.select("img").first();
            String cover= img.attr("src");
            book.setCover(cover);
            bookService.createBook(book);
            result.put("code","0");
            result.put("msg","success");
        } catch (BusinessException e) {
            e.printStackTrace();
            result.put("code",e.getCode());
            result.put("msg",e.getMsg());
        }
        return result;
    }

    @GetMapping("list")
    @ResponseBody
    public Map list(Integer page, Integer limit){
        if(page==null){
            page=1;
        }
        if(limit==null){
            limit=10;
        }

        IPage<Book> pageObject = bookService.paging(null,null,page,limit);
        Map result=new HashMap();
        result.put("code","0");
        result.put("msg","success");
        result.put("data",pageObject.getRecords());
        result.put("count",pageObject.getTotal());
        return result;
    }

    @GetMapping("/id/{id}")
    @ResponseBody
    public Map selectById(@PathVariable("id") Long bookId){
        Book book=bookService.selectById(bookId);
        Map result = new HashMap();
        result.put("code","0");
        result.put("msg","success");
        result.put("data",book);
        return result;
    }

    @PostMapping("/update")
    @ResponseBody
    public Map updateBook(Book book) {
        Map result=new HashMap();
        try {
            Book rawBook=bookService.selectById(book.getBookId());
            rawBook.setBookName(book.getBookName());
            rawBook.setSubTitle(book.getSubTitle());
            rawBook.setAuthor(book.getAuthor());
            rawBook.setCategoryId(book.getCategoryId());
            rawBook.setDescription(book.getDescription());
            Document doc=Jsoup.parse(book.getDescription());
            String cover=doc.select("img").first().attr("src");
            rawBook.setCover(cover);
            bookService.updateBook(rawBook);
            result.put("code","0");
            result.put("msg","success");
        } catch (BusinessException e) {
            e.printStackTrace();
            result.put("code",e.getCode());
            result.put("msg",e.getMsg());
        }
        return result;
    }

    @GetMapping("delete/{id}")
    @ResponseBody
    public Map deleteBook(@PathVariable("id") Long bookId) {
        Map result=new HashMap();
        try {
            bookService.deleteBook(bookId);
            result.put("code","0");
            result.put("msg","success");
        } catch (BusinessException e) {
            e.printStackTrace();
            result.put("code",e.getCode());
            result.put("msg",e.getMsg());
        }
        return result;
    }
}
