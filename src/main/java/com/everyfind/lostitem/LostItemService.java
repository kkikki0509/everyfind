package com.everyfind.lostitem;

import com.everyfind.member.Member;
import com.everyfind.member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class LostItemService {
    private final LostItemRepository lostItemRepository;
    private final MemberRepository memberRepository;
    @Autowired
    public LostItemService(LostItemRepository lostItemRepository, MemberRepository memberRepository) {
        this.lostItemRepository = lostItemRepository;
        this.memberRepository = memberRepository;
    }

    // 분실물 게시물 생성
    public LostItem createLostItem(LostItemRequestDto requestDto, String email) {
        Optional<Member> optMember = memberRepository.findByEmail(email);
        Member member = null;

        try{
            member = optMember.get();
        }
        catch(NoSuchElementException e){
            throw new NoSuchElementException("존재하지 않는 회원입니다.");
        }

        LostItem lostItem = new LostItem(
                requestDto.getTitle(),
                requestDto.getCategory(),
                requestDto.getLostPlace(),
                requestDto.getFeature(),
                requestDto.getLostDate(),
                member
        );

        return lostItemRepository.save(lostItem);
    }

    // 분실물 전체 조회
    public List<LostItem> getLostItems(){
        return lostItemRepository.findAll();
    }

    // 분실물 내용 수정
    public LostItem updateLostItem(Long lostId, Long memberId, LostItemRequestDto requestDto) {
        Optional<LostItem> optlostItem = lostItemRepository.findById(lostId);
        LostItem lostItem= null;

        try{
            lostItem = optlostItem.get();
        }
        catch(NoSuchElementException e){
            throw new NoSuchElementException("존재하지 않는 분실물입니다.");
        }

        if (!lostItem.getMember().getId().equals(memberId)) {
            throw new IllegalArgumentException("자신이 등록한 게시물만 수정할 수 있습니다.");
        }

        lostItem.updateLostItem(
                requestDto.getTitle(),
                requestDto.getCategory(),
                requestDto.getLostPlace(),
                requestDto.getFeature(),
                requestDto.getLostDate()
        );

        return lostItemRepository.save(lostItem);
    }

    // 분실물 단건 조회
    public LostItem getLostItem(Long lostId) {
        Optional<LostItem> optLostItem = lostItemRepository.findById(lostId);
        LostItem lostItem = null;

        try {
            lostItem = optLostItem.get();
        }
        catch (NoSuchElementException e) {
            throw new NoSuchElementException("존재하지 않는 분실물입니다.");
        }

        return lostItem;
    }

    // 분실물 삭제
    public void deleteLostItem(Long lostId, Long memberId) {
        Optional<LostItem> optLostItem = lostItemRepository.findById(lostId);
        LostItem lostItem = null;

        try {
            lostItem = optLostItem.get();
        }
        catch (NoSuchElementException e) {
            throw new NoSuchElementException("존재하지 않는 분실물입니다.");
        }

        if (!lostItem.getMember().getId().equals(memberId)) {
            throw new IllegalArgumentException("자신이 등록한 게시물만 삭제할 수 있습니다.");
        }

        lostItemRepository.delete(lostItem);
        System.out.println(lostId + " 번 게시물이 삭제되었습니다.");
    }

}
