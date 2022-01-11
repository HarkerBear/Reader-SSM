package reader.service;

import reader.entity.Member;
import reader.entity.MemberReadState;

public interface MemberService {
    public Member createMember(String username,String password, String nickname);

    public Member checkLogin(String username, String password);

    public MemberReadState selectMemberReadState(Long memberId, Long bookId);

    public MemberReadState updateMemberReadState(Long memberId,Long bookId,Integer readState);
}
