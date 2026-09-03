package com.everyfind.match;

import com.everyfind.founditem.FoundItem;
import com.everyfind.founditem.FoundItemRepository;
import com.everyfind.lostitem.LostItem;
import com.everyfind.lostitem.LostItemRepository;
import com.everyfind.member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class MatchService {

    private final LostItemRepository lostItemRepository;
    private final FoundItemRepository foundItemRepository;

    @Autowired
    public MatchService(LostItemRepository lostItemRepository, FoundItemRepository foundItemRepository) {
        this.lostItemRepository = lostItemRepository;
        this.foundItemRepository = foundItemRepository;
    }

    // 분실물 찾기
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

    // 해당 학교의 습득물 전체 조회
    public List<FoundItem> getFoundItemsBySchool(LostItem lostItem) {
        Long schoolId = lostItem.getMember().getSchool().getId();

        return foundItemRepository.findByMemberSchoolId(schoolId);
    }

    // 핵심 키워드 추출
    private List<String> extractKeywords(String feature) {
        return List.of(feature.split("\\s+"));
    }

    // 분실물 및 습득물 관련 키워드 수
    private int countKeywords(List<String> lostKeywords, List<String> foundKeywords) {

        int count = 0;

        for (String keyword : lostKeywords) {
            if (foundKeywords.contains(keyword)) {
                count++;
            }
        }

        return count;
    }

    // 유사도 점수 매기기
    private double calculateMatchScore(List<String> lostKeywords, List<String> foundKeywords) {
        int keywordsCount = countKeywords(lostKeywords, foundKeywords);

        return (double) keywordsCount / lostKeywords.size() * 100;
    }

    // 유사도 계산 후 결과 생성
    private MatchResult createMatchResult(LostItem lostItem, FoundItem foundItem) {

        List<String> lostKeywords = extractKeywords(lostItem.getFeature());
        List<String> foundKeywords = extractKeywords(foundItem.getFeature());

        double score = calculateMatchScore(lostKeywords, foundKeywords);

        return new MatchResult(foundItem, score);
    }

    // 후보 등록
    public List<MatchResult> findMatches(Long lostId) {

        LostItem lostItem = getLostItem(lostId);

        List<FoundItem> foundItems = getFoundItemsBySchool(lostItem);

        List<MatchResult> results = new ArrayList<>();

        for (FoundItem foundItem : foundItems) {
            MatchResult result = createMatchResult(lostItem, foundItem);
            results.add(result);
        }

        results.sort((a, b) -> Double.compare(b.getScore(), a.getScore()));

        int limit = Math.min(results.size(), 3);
        return results.subList(0, limit);
    }

    // 매칭 성공 시 상태 변화
    public void confirmMatch(Long lostId, Long foundId) {

        LostItem lostItem = getLostItem(lostId);

        lostItem.markAsFound();

        lostItemRepository.save(lostItem);
    }
}