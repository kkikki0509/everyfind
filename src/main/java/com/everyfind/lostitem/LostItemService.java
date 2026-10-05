package com.everyfind.lostitem;

import com.everyfind.match.MatchResult;
import com.everyfind.match.MatchService;
import com.everyfind.member.Member;
import com.everyfind.member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class LostItemService {
    private final LostItemRepository lostItemRepository;
    private final MemberRepository memberRepository;
    private final MatchService matchService;

    @Autowired
    public LostItemService(LostItemRepository lostItemRepository, MemberRepository memberRepository,
                           MatchService matchService) {
        this.lostItemRepository = lostItemRepository;
        this.memberRepository = memberRepository;
        this.matchService = matchService;
    }

    /* 분실물 게시물 생성 */
    public LostItemMatchResponseDto createLostItem(LostItemRequestDto requestDto, String email) {
        Member member = memberRepository.findByEmail(email).orElseThrow(() ->
                new NoSuchElementException("존재하지 않는 회원입니다."));

        LostItem lostItem = new LostItem(
                requestDto.getTitle(),
                requestDto.getCategory(),
                requestDto.getLostPlace(),
                requestDto.getFeature(),
                requestDto.getLostDate(),
                member
        );

        // DB에 저장
        LostItem savedLostItem = lostItemRepository.save(lostItem);

        // 후보 3개
        List<MatchResult> matches = matchService.findMatches(savedLostItem.getId());

        return new LostItemMatchResponseDto(savedLostItem, matches);
    }

    /* 분실물 전체 조회 */
    public List<LostItemResponseDto> getLostItems(String email){
        Member member = memberRepository.findByEmail(email).orElseThrow(() ->
                new NoSuchElementException("존재하지 않는 회원입니다."));

        // 회원의 학교 pk
        Long schoolId = member.getSchool().getId();

        // 해당 학교의 분실물
        return lostItemRepository.findByMemberSchoolId(schoolId)
                .stream()
                .map(LostItemResponseDto::new)
                .toList();
    }

    /* 분실물 찾았을 때 */
    public void confirmMatch(Long lostId, Long foundId, String email) {
        Member member = memberRepository.findByEmail(email).orElseThrow(() ->
                new NoSuchElementException("존재하지 않는 회원입니다."));

        LostItem lostItem = lostItemRepository.findById(lostId).orElseThrow(() ->
                new NoSuchElementException("존재하지 않는 분실물입니다."));

        // 접근 제어
        if (!lostItem.getMember().getId().equals(member.getId())) {
            throw new IllegalArgumentException("자신의 분실물만 매칭 확정할 수 있습니다.");
        }

        matchService.confirmMatch(lostId);
    }

    /* 분실물 단건 조회 */
    public LostItemMatchResponseDto getLostItem(Long lostId, String email) {
        LostItem lostItem = lostItemRepository.findById(lostId).orElseThrow(() ->
                new NoSuchElementException("존재하지 않는 분실물입니다."));

        Member member = memberRepository.findByEmail(email).orElseThrow(() ->
                new NoSuchElementException("존재하지 않는 회원입니다."));

        // 접근 제어
        if (!lostItem.getMember().getSchool().getId().equals(member.getSchool().getId())) {
            throw new IllegalArgumentException("같은 학교의 분실물만 조회할 수 있습니다.");
        }

        // 후보 3개
        List<MatchResult> matches = matchService.findMatches(lostId);

        return new LostItemMatchResponseDto(lostItem, matches);
    }

    /* 분실물 내용 수정 */
    public void updateLostItem(Long lostId, String email, LostItemRequestDto requestDto) {

        LostItem lostItem = lostItemRepository.findById(lostId).orElseThrow(() ->
                new NoSuchElementException("존재하지 않는 분실물입니다."));

        Member member = memberRepository.findByEmail(email).orElseThrow(() ->
                new NoSuchElementException("존재하지 않는 회원입니다."));

        // 접근 제어
        if (!lostItem.getMember().getId().equals(member.getId())) {
            throw new IllegalArgumentException("자신이 등록한 게시물만 수정할 수 있습니다.");
        }

        // 엔티티 속성값 수정
        lostItem.updateLostItem(
                requestDto.getTitle(),
                requestDto.getCategory(),
                requestDto.getLostPlace(),
                requestDto.getFeature(),
                requestDto.getLostDate()
        );

        lostItemRepository.save(lostItem);
    }

    /* 분실물 삭제 */
    public void deleteLostItem(Long lostId, String email) {
        LostItem lostItem = lostItemRepository.findById(lostId).orElseThrow(() ->
                new NoSuchElementException("존재하지 않는 분실물입니다."));

        Member member = memberRepository.findByEmail(email).orElseThrow(() ->
                new NoSuchElementException("존재하지 않는 회원입니다."));

        // 접근 제어
        if (!lostItem.getMember().getId().equals(member.getId())) {
            throw new IllegalArgumentException("자신이 등록한 게시물만 삭제할 수 있습니다.");
        }

        // 삭제
        lostItemRepository.delete(lostItem);
    }

}
