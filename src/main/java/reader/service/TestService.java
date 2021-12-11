package reader.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reader.mapper.TestMapper;

import javax.annotation.Resource;

@Service
public class TestService {
    @Resource
    private TestMapper testMapper;
    @Transactional
    public void batchImport(){
        for (int i = 0; i < 5; i++) {
//            if(i==3){
//                throw new RuntimeException("runtime exception");
//            }
            testMapper.insertSample();
        }
    }
}
