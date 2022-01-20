package reader.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import reader.entity.Book;

public interface BookService {
    public IPage<Book> paging(Long categoryId, String order,Integer page,Integer rows);

    public Book selectById(Long bookId);

    public void updateEvaluation();

}
