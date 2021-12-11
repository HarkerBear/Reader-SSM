package reader.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import reader.entity.Test;

public interface TestMapper extends BaseMapper<Test> {
    public void insertSample();
}
