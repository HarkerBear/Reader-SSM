package reader.service.impl;

import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import reader.service.MemberService;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class MemberServiceImplTest extends TestCase {

    @Resource
    private MemberService memberService;

    @Test
    public void testCreateMember() {
        memberService.createMember("u123456","123456","test");
    }
}