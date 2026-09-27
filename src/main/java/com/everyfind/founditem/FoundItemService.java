package com.everyfind.founditem;

import com.everyfind.member.Member;
import com.everyfind.member.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class FoundItemService {
    private final FoundItemRepository foundItemRepository;
    private final MemberRepository memberRepository;

    public FoundItemService(FoundItemRepository foundItemRepository, MemberRepository memberRepository) {
        this.foundItemRepository = foundItemRepository;
        this.memberRepository = memberRepository;
    }

    /* 습득물 게시물 생성 */
    public FoundItemResponseDto createFoundItem(FoundItemRequestDto requestDto, String email) {
        Member member = memberRepository.findByEmail(email).orElseThrow(() ->
                new NoSuchElementException("존재하지 않는 회원입니다."));

        FoundItem foundItem = new FoundItem(
                requestDto.getTitle(),
                requestDto.getCategory(),
                requestDto.getFoundPlace(),
                requestDto.getFeature(),
                requestDto.getFoundDate(),
                member
        );

        FoundItem savedFoundItem = foundItemRepository.save(foundItem);

        return new FoundItemResponseDto(savedFoundItem);
    }

    /* 습득물 전체 조회 */
    public List<FoundItemResponseDto> getFoundItems(String email) {
        Member member = memberRepository.findByEmail(email).orElseThrow(() ->
                new NoSuchElementException("존재하지 않는 회원입니다."));

        Long schoolId = member.getSchool().getId();

        return foundItemRepository.findByMemberSchoolId(schoolId)
                .stream()
                .map(FoundItemResponseDto::new)
                .toList();
    }

    /* 습들물 단건 조회 */
    public FoundItemResponseDto getFoundItem(Long foundId, String email) {
        FoundItem foundItem = foundItemRepository.findById(foundId).orElseThrow(() ->
                new NoSuchElementException("존재하지 않는 습득물입니다."));

        Member member = memberRepository.findByEmail(email).orElseThrow(() ->
                new NoSuchElementException("존재하지 않는 회원입니다."));

        // 접근 제어
        if (!foundItem.getMember().getSchool().getId().equals(member.getSchool().getId())) {
            throw new IllegalArgumentException("같은 학교의 습득물만 조회할 수 있습니다.");
        }

        return new FoundItemResponseDto(foundItem);
    }

    /* 습득물 수정 */
    public void updateFoundItem(Long foundId, String email, FoundItemRequestDto requestDto) {

        FoundItem foundItem = foundItemRepository.findById(foundId).orElseThrow(() ->
                new NoSuchElementException("존재하지 않는 습득물입니다."));

        Member member = memberRepository.findByEmail(email).orElseThrow(() ->
                new NoSuchElementException("존재하지 않는 회원입니다."));

        // 접근 제어
        if (!foundItem.getMember().getId().equals(member.getId())) {
            throw new IllegalArgumentException("자신이 등록한 게시물만 수정할 수 있습니다.");
        }

        foundItem.updateFoundItem(
                requestDto.getTitle(),
                requestDto.getCategory(),
                requestDto.getFoundPlace(),
                requestDto.getFeature(),
                requestDto.getFoundDate()
        );

        foundItemRepository.save(foundItem);
    }

    /* 습득물 삭제 */
    public void deleteFoundItem(Long foundId, String email) {

        FoundItem foundItem = foundItemRepository.findById(foundId).orElseThrow(() ->
                new NoSuchElementException("존재하지 않는 습득물입니다."));


        Member member = memberRepository.findByEmail(email).orElseThrow(() ->
                new NoSuchElementException("존재하지 않는 회원입니다."));

        // 접근 제어
        if (!foundItem.getMember().getId().equals(member.getId())) {
            throw new IllegalArgumentException("자신이 등록한 게시물만 삭제할 수 있습니다.");
        }

        foundItemRepository.delete(foundItem);
    }
}