package reader.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import reader.entity.Book;
import reader.mapper.BookMapper;
import reader.service.BookService;

import javax.annotation.Resource;

@Service("bookService")
@Transactional(propagation = Propagation.NOT_SUPPORTED,readOnly = true)
public class BookServiceImpl implements BookService {
    @Resource
    private BookMapper bookMapper;

    public IPage<Book> paging(Long categoryId, String order,Integer page, Integer rows) {
        Page<Book> p=new Page<Book>(page,rows);
        QueryWrapper<Book> queryWrapper=new QueryWrapper<Book>();
        if(categoryId!=null && categoryId!=-1){
            queryWrapper.eq("category_id",categoryId);
        }
        if(order!=null){
            if(order.equals("quantity")){
                queryWrapper.orderByDesc("evaluation_quantity");
            }else if(order.equals("score")){
                queryWrapper.orderByDesc("evaluation_score");
            }
        }

        IPage<Book> pageObject=bookMapper.selectPage(p,queryWrapper);
        return pageObject;
    }

    public Book selectById(Long bookId) {
        Book book=bookMapper.selectById(bookId);
        return book;
    }

    @Transactional
    public void updateEvaluation() {
        bookMapper.updateEvaluation();
    }
}
