package reader.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import reader.entity.Book;
import reader.entity.Evaluation;
import reader.entity.Member;
import reader.mapper.BookMapper;
import reader.mapper.EvaluationMapper;
import reader.mapper.MemberMapper;
import reader.service.EvaluationService;

import javax.annotation.Resource;
import javax.annotation.Resources;
import java.util.List;

@Service("evaluationService")
@Transactional(propagation = Propagation.NOT_SUPPORTED,readOnly = true)
public class EvaluationServiceImpl implements EvaluationService {
    @Resource
    private EvaluationMapper evaluationMapper;
    @Resource
    private MemberMapper memberMapper;
    @Resource
    private BookMapper bookMapper;

    public List<Evaluation> selectByBookId(Long bookId) {
        Book book=bookMapper.selectById(bookId);
        QueryWrapper<Evaluation> queryWrapper = new QueryWrapper<Evaluation>();
        queryWrapper.eq("book_Id",bookId);
        queryWrapper.eq("state","enable");
        queryWrapper.orderByDesc("create_time");
        List<Evaluation> evaluationList=evaluationMapper.selectList(queryWrapper);
        for(Evaluation eva:evaluationList){
            Member member=memberMapper.selectById(eva.getMemberId());
            eva.setMember(member);
            eva.setBook(book);
        }
        return evaluationList;
    }
}
