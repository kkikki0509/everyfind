package com.everyfind.founditem;

import com.everyfind.member.Member;
import com.everyfind.member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class FoundItemService {
    private final FoundItemRepository foundItemRepository;
    private final MemberRepository memberRepository;

    @Autowired
    public FoundItemService(FoundItemRepository foundItemRepository, MemberRepository memberRepository) {
        this.foundItemRepository = foundItemRepository;
        this.memberRepository = memberRepository;
    }

    // 습득물 게시물 생성
    public FoundItem createFoundItem(FoundItemRequestDto requestDto, Long memberId) {
        Optional<Member> optMember = memberRepository.findById(memberId);
        Member member = null;

        try {
            member = optMember.get();
        }
        catch (NoSuchElementException e) {
            throw new NoSuchElementException("존재하지 않는 회원입니다.");
        }

        FoundItem foundItem = new FoundItem(
                requestDto.getTitle(),
                requestDto.getCategory(),
                requestDto.getFoundPlace(),
                requestDto.getFeature(),
                requestDto.getFoundDate(),
                member
        );

        return foundItemRepository.save(foundItem);
    }

    // 습득물 전체 조회
    public List<FoundItem> getFoundItems() {

        return foundItemRepository.findAll();
    }

    // 습들물 단건 조회
    public FoundItem getFoundItem(Long foundId) {
        Optional<FoundItem> optFoundItem = foundItemRepository.findById(foundId);
        FoundItem foundItem = null;

        try {
            foundItem = optFoundItem.get();
        }
        catch (NoSuchElementException e) {
            throw new NoSuchElementException("존재하지 않는 습득물입니다.");
        }

        return foundItem;
    }

    // 습득물 수정
    public FoundItem updateFoundItem(Long foundId, Long memberId, FoundItemRequestDto requestDto) {

        Optional<FoundItem> optFoundItem = foundItemRepository.findById(foundId);
        FoundItem foundItem = null;

        try {
            foundItem = optFoundItem.get();
        }
        catch (NoSuchElementException e) {
            throw new NoSuchElementException("존재하지 않는 습득물입니다.");
        }

        if (!foundItem.getMember().getId().equals(memberId)) {
            throw new IllegalArgumentException("자신이 등록한 게시물만 수정할 수 있습니다.");
        }

        foundItem.updateFoundItem(
                requestDto.getTitle(),
                requestDto.getCategory(),
                requestDto.getFoundPlace(),
                requestDto.getFeature(),
                requestDto.getFoundDate()
        );

        return foundItemRepository.save(foundItem);
    }

    // 습득물 삭제
    public void deleteFoundItem(Long foundId, Long memberId) {

        Optional<FoundItem> optFoundItem = foundItemRepository.findById(foundId);
        FoundItem foundItem = null;

        try {
            foundItem = optFoundItem.get();
        }
        catch (NoSuchElementException e) {
            throw new NoSuchElementException("존재하지 않는 습득물입니다.");
        }

        if (!foundItem.getMember().getId().equals(memberId)) {
            throw new IllegalArgumentException("자신이 등록한 게시물만 삭제할 수 있습니다.");
        }

        foundItemRepository.delete(foundItem);
    }
}