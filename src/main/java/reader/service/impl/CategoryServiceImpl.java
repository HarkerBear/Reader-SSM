package reader.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import reader.entity.Category;
import reader.mapper.CategoryMapper;
import reader.service.CategoryService;

import javax.annotation.Resource;
import java.util.List;
@Service("categoryService")
@Transactional(propagation = Propagation.NOT_SUPPORTED,readOnly = true)
public class CategoryServiceImpl implements CategoryService {
    @Resource
    private CategoryMapper categoryMapper;

    /**
     * get all book categories
     * @return book categories list
     */
    public List<Category> selectAll() {
        List<Category> list=categoryMapper.selectList(new QueryWrapper<Category>());
        return list;
    }
}
