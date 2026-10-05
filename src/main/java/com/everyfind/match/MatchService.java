package com.everyfind.match;

import com.everyfind.founditem.FoundItem;
import com.everyfind.founditem.FoundItemRepository;
import com.everyfind.lostitem.LostItem;
import com.everyfind.lostitem.LostItemRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MatchService {

    private final LostItemRepository lostItemRepository;
    private final FoundItemRepository foundItemRepository;

    public MatchService(LostItemRepository lostItemRepository, FoundItemRepository foundItemRepository) {
        this.lostItemRepository = lostItemRepository;
        this.foundItemRepository = foundItemRepository;
    }

    /* 분실물 찾기 */
    public LostItem getLostItem(Long lostId) {
        LostItem lostItem = lostItemRepository.findById(lostId).orElseThrow(() ->
            new NoSuchElementException("존재하지 않는 분실물입니다."));

        return lostItem;
    }

    /* 해당 학교의 습득물 전체 조회 */
    public List<FoundItem> getFoundItemsBySchool(LostItem lostItem) {
        Long schoolId = lostItem.getMember().getSchool().getId();

        return foundItemRepository.findByMemberSchoolId(schoolId);
    }

    /* 앞뒤 공백 제거 */
    private String deleteSpace(String value) {
        if (value == null) {
            return "";
        }

        return value.trim();
    }

    /* 핵심 키워드 추출 */
    private List<String> extractKeywords(String text) {
        String s = deleteSpace(text);

        if (s.isBlank()) { // 비어있는지 검사
            return List.of(); // 요소가 하나도 없는 리스트
        }

        return Arrays.stream(s.split("\\s+"))
                .distinct() // 중복 단어 제거
                .toList();
    }

    /* 제목 및 특징 비율 계산 */
    private double calculateKeywordScore(String lostText, String foundText) {
        List<String> lostKeywords = extractKeywords(lostText);
        List<String> foundKeywords = extractKeywords(foundText);

        if (lostKeywords.isEmpty() || foundKeywords.isEmpty()) {
            return 0.0;
        }

        long matchedCount = lostKeywords.stream()
                .filter(foundKeywords::contains) // .filter(keyword -> foundKeywords.contains(keyword))를 축약한 메서드 참조 문법
                .count();

        return (double) matchedCount / lostKeywords.size();
    }

    /* 카테고리 비교 */
    private double calculateCategoryScore(LostItem lostItem, FoundItem foundItem) {
        String lostCategory = deleteSpace(lostItem.getCategory());
        String foundCategory = deleteSpace(foundItem.getCategory());

        if (lostCategory.isBlank() || foundCategory.isBlank()) {
            return 0;
        }

        return lostCategory.equals(foundCategory) ? 25 : 0; // 25점 부여
    }

    /* 장소 비교 */
    private double calculatePlaceScore(LostItem lostItem, FoundItem foundItem) {
        String lostPlace = deleteSpace(lostItem.getLostPlace());
        String foundPlace = deleteSpace(foundItem.getFoundPlace());

        if (lostPlace.isBlank() || foundPlace.isBlank()) {
            return 0;
        }

        if (lostPlace.equals(foundPlace)) { // 완전 동일 - 20점 부여
            return 20;
        }

        else if (lostPlace.contains(foundPlace) || foundPlace.contains(lostPlace)) { // 부분 동일 - 15점 부여
            return 15;
        }

        return 0;
    }

    /* 날짜 비교 */
    private boolean isDateCandidate(LostItem lostItem, FoundItem foundItem) {
        if (lostItem.getLostDate() == null || foundItem.getFoundDate() == null) {
            return false;
        }

        return !lostItem.getLostDate().isAfter(foundItem.getFoundDate()); // 분실일 <= 습득일 -> true
    }

    /* 유사도 점수 계산 */
    private double calculateMatchScore(LostItem lostItem, FoundItem foundItem) {
        double score = 0;

        score += calculateCategoryScore(lostItem, foundItem); // 카테고리
        score += calculatePlaceScore(lostItem, foundItem); // 장소

        score += calculateKeywordScore(lostItem.getFeature(), foundItem.getFeature()) * 25; // 세부 특징
        score += calculateKeywordScore(lostItem.getTitle(), foundItem.getTitle()) * 10; // 제목

        return score;
    }

    /* 후보 결과 생성 */
    private MatchResult createMatchResult(LostItem lostItem, FoundItem foundItem) {
        double score = calculateMatchScore(lostItem, foundItem);

        return new MatchResult(foundItem, score);
    }

    /* 상위 3개 후보 조회 */
    public List<MatchResult> findMatches(Long lostId) {
        LostItem lostItem = getLostItem(lostId);

        List<FoundItem> foundItems = getFoundItemsBySchool(lostItem);
        List<MatchResult> results = new ArrayList<>();

        for (FoundItem foundItem : foundItems) {
            if (!isDateCandidate(lostItem, foundItem)) { // 날짜 조건
                continue;
            }

            MatchResult result = createMatchResult(lostItem, foundItem);

            if (result.getScore() > 0) {
                results.add(result);
            }
        }

        results.sort((a, b) -> Double.compare(b.getScore(), a.getScore()));

        return results.stream().limit(3).toList();
    }

    /* 매칭 성공 */
    public void confirmMatch(Long lostId) {
        LostItem lostItem = getLostItem(lostId);

        lostItem.markAsFound();
        lostItemRepository.save(lostItem);
    }
}