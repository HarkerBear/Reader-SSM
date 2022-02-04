package reader.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import reader.entity.Book;
import reader.entity.Evaluation;
import reader.entity.MemberReadState;
import reader.mapper.BookMapper;
import reader.mapper.EvaluationMapper;
import reader.mapper.MemberReadStateMapper;
import reader.service.BookService;

import javax.annotation.Resource;

@Service("bookService")
@Transactional(propagation = Propagation.NOT_SUPPORTED,readOnly = true)
public class BookServiceImpl implements BookService {
    @Resource
    private BookMapper bookMapper;
    @Resource
    private MemberReadStateMapper memberReadStateMapper;
    @Resource
    private EvaluationMapper evaluationMapper;

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

    @Transactional
    public Book createBook(Book book) {
        bookMapper.insert(book);
        return book;
    }

    @Transactional
    public Book updateBook(Book book) {
        bookMapper.updateById(book);
        return book;
    }

    @Transactional
    public void deleteBook(Long bookId) {
        bookMapper.deleteById(bookId);
        QueryWrapper<MemberReadState> mrsQueryWrapper = new QueryWrapper<MemberReadState>();
        mrsQueryWrapper.eq("book_id",bookId);
        memberReadStateMapper.delete(mrsQueryWrapper);
        QueryWrapper<Evaluation> evaluationQueryWrapper = new QueryWrapper<Evaluation>();
        evaluationQueryWrapper.eq("book_id",bookId);
        evaluationMapper.delete(evaluationQueryWrapper);
    }
}
