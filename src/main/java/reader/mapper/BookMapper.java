package reader.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import reader.entity.Book;

public interface BookMapper extends BaseMapper<Book> {

    public void updateEvaluation();
}
