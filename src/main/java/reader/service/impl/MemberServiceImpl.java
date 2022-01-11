package reader.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import reader.entity.Member;
import reader.entity.MemberReadState;
import reader.mapper.MemberMapper;
import reader.mapper.MemberReadStateMapper;
import reader.service.MemberService;
import reader.service.exception.BusinessException;
import reader.utils.MD5Utils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Service("memberService")
@Transactional
public class MemberServiceImpl implements MemberService {
    @Resource
    private MemberMapper memberMapper;
    @Resource
    private MemberReadStateMapper memberReadStateMapper;

    public Member createMember(String username, String password, String nickname) {
        QueryWrapper<Member> queryWrapper = new QueryWrapper<Member>();
        queryWrapper.eq("username",username);
        List<Member> memberList=memberMapper.selectList(queryWrapper);
        if(memberList.size() > 0) {
            throw new BusinessException("MO1","The username already exists.");
        }
        Member member=new Member();
        member.setUsername(username);
        member.setNickname(nickname);
        int salt=new Random().nextInt(1000) + 1000;
        String md5=MD5Utils.md5Digest(password,salt);
        member.setSalt(salt);
        member.setPassword(md5);
        member.setCreateTime(new Date());
        memberMapper.insert(member);
        return member;
    }

    public Member checkLogin(String username, String password) {
        QueryWrapper<Member> queryWrapper = new QueryWrapper<Member>();
        queryWrapper.eq("username",username);
        Member member = memberMapper.selectOne(queryWrapper);
        if (member==null) {
            throw new BusinessException("M02","The user doesn't exist.");
        }
        String md5=MD5Utils.md5Digest(password,member.getSalt());
        if(!md5.equals(member.getPassword())){
            throw new BusinessException("M03","Wrong password");
        }
        return member;
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED,readOnly = true)
    public MemberReadState selectMemberReadState(Long memberId, Long bookId) {
        QueryWrapper<MemberReadState> queryWrapper = new QueryWrapper();
        queryWrapper.eq("book_id",bookId);
        queryWrapper.eq("member_id",memberId);
        MemberReadState memberReadState=memberReadStateMapper.selectOne(queryWrapper);
        return memberReadState;
    }

    public MemberReadState updateMemberReadState(Long memberId, Long bookId, Integer readState) {
        QueryWrapper<MemberReadState> queryWrapper = new QueryWrapper<MemberReadState>();
        queryWrapper.eq("book_id",bookId);
        queryWrapper.eq("member_id",memberId);
        MemberReadState memberReadState=memberReadStateMapper.selectOne(queryWrapper);
        if(memberReadState==null){
            memberReadState = new MemberReadState();
            memberReadState.setMemberId(memberId);
            memberReadState.setBookId(bookId);
            memberReadState.setReadState(readState);
            memberReadState.setCreateTime(new Date());
            memberReadStateMapper.insert(memberReadState);
        }else{
            memberReadState.setReadState(readState);
            memberReadStateMapper.updateById(memberReadState);
        }
        return memberReadState;
    }


}
