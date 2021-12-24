package reader.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import reader.entity.Book;
import reader.service.BookService;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class BookServiceImplTest extends TestCase {
    @Resource
    private BookService bookService;

    @Test
    public void testPaging() {
        IPage<Book> pageObject=bookService.paging(2l,"quantity",1,10);
        List<Book> list=pageObject.getRecords();
        for (Book b:list){
            System.out.println(b);
        }
    }
}