package com.ssafy.polaris.book.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import com.ssafy.polaris.essay.repository.EssayRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ssafy.polaris.book.domain.UserBook;
import com.ssafy.polaris.book.dto.BookListRequestDto;
import com.ssafy.polaris.book.dto.BookRequestDto;
import com.ssafy.polaris.book.dto.SearchUserBookListResponseDto;
import com.ssafy.polaris.book.dto.SearchUserBookResponseDto;
import com.ssafy.polaris.book.dto.UserBookDeleteRequestDto;
import com.ssafy.polaris.book.dto.UserBookListDeleteRequestDto;
import com.ssafy.polaris.book.dto.UserBookMapper;
import com.ssafy.polaris.book.dto.UserBookResponseDto;
import com.ssafy.polaris.book.dto.UserBookUpdateRequestDto;
import com.ssafy.polaris.book.dto.WeeklyBooksDto;
import com.ssafy.polaris.book.repository.BookRepository;
import com.ssafy.polaris.book.repository.UserBookRepository;
import com.ssafy.polaris.series.dto.SeriesMapper;
import com.ssafy.polaris.series.repository.SeriesRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class UserBookServiceImpl implements UserBookService {
	private final UserBookMapper userBookMapper;
	private final SeriesMapper seriesMapper;
	private final UserBookRepository userBookRepository;
	private final BookRepository bookRepository;
	private final SeriesRepository seriesRepository;
	private final EssayRepository essayRepository;
	private final StringRedisTemplate redisTemplate;
	private final EntityManager em;

	@Override
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
	public void createUserBook(Long userId, BookListRequestDto bookListRequestDto) {
		List<BookRequestDto> books = bookListRequestDto.getBooks();
		for (BookRequestDto b : books) {
			bookRepository.save(userBookMapper.toBookEntity(b));
			userBookRepository.save(userBookMapper.toUserBookEntity(userId, b));
			if (b.getSeriesId() != null) {
				seriesRepository.save(seriesMapper.toSeriesEntity(b.getSeriesId(), b.getSeriesName()));
			}
		}
	}

	@Override
	public List<UserBookResponseDto> getLibrary(Long userId) {
		return userBookRepository.findAllByUserId(userId);
	}

	@Override
	public UserBookResponseDto getUserBook(Long userId, String isbn) {
		return userBookRepository.getUserBook(userId, isbn);
	}

	@Override
	public int updateUserBook(Long userId, UserBookUpdateRequestDto data) {
		UserBook userBook = userBookRepository.getUserBookByIdAndIsbn(userId, data.getIsbn());
		if (userBook == null) {
			return 0;
		}
		userBook.updateUserBook(data);
		return 1;
	}

	@Override
	public int deleteUserBook(Long userId, UserBookListDeleteRequestDto data) {
		// TODO: 자신의 책인지 확인하는 로직 필요
		for (UserBookDeleteRequestDto dto : data.getBooks()) {
			UserBook userBook = userBookRepository.getReferenceById(dto.getId());
			if (!em.contains(userBook)) {
				return 0;
			}
			essayRepository.deleteEssayByUserBookId(userBook.getId());
			userBookRepository.deleteById(userBook.getId());
		}
		return 1;
	}

	@Override
	public SearchUserBookListResponseDto searchByConditionUserBook(Long regcodeId, String queryType, String keyword) {
		em.clear();
		String jpql = "select distinct new com.ssafy.polaris.book.dto.SearchUserBookResponseDto(ub.id, ub.user.id, " +
			"ub.user.nickname, ub.user.profileUrl, ub.user.regcode, b.isbn, b.title, " +
			"b.author, b.cover, ub.userBookTradeType) " +
			"from UserBook ub " +
			"left join Book b on ub.book.isbn = b.isbn " +
			"left join User u on u.id = ub.user.id ";

		// WHERE 절을 동적으로 추가하기 위한 StringBuilder
		StringBuilder whereClause = new StringBuilder();

		// 파라미터 Map을 이용하여 동적으로 쿼리 파라미터 설정
		Map<String, Object> parameters = new HashMap<>();

		if (regcodeId != null) {
			whereClause.append("u.regcode.id = :regcodeId");
			parameters.put("regcodeId", regcodeId);
		}

		if (queryType != null && keyword != null) {
			if (!whereClause.isEmpty()) {
				whereClause.append(" and ");
			}
			whereClause.append("b.").append(queryType).append(" like concat('%', :keyword, '%')");
			parameters.put("keyword", keyword);
		} else if (keyword != null && regcodeId == null && queryType == null) {
			if (!whereClause.isEmpty()) {
				whereClause.append(" and ");
			}
			whereClause.append("(u.regcode.si like concat('%', :regcodeKeyword, '%') ");
			whereClause.append("or u.regcode.gungu like concat('%', :regcodeKeyword, '%') ");
			whereClause.append("or u.regcode.dong like concat('%', :regcodeKeyword, '%') ");
			whereClause.append("or b.title like concat('%', :bookKeyword, '%') ");
			whereClause.append("or b.author like concat('%', :bookKeyword, '%'))");
			parameters.put("regcodeKeyword", keyword);
			parameters.put("bookKeyword", keyword);
		}

		if (!whereClause.isEmpty()) {
			jpql += " where " + whereClause.toString();
		}
		TypedQuery<SearchUserBookResponseDto> query = em.createQuery(jpql, SearchUserBookResponseDto.class);

		for (Map.Entry<String, Object> entry : parameters.entrySet()) {
			query.setParameter(entry.getKey(), entry.getValue());
		}
		List<SearchUserBookResponseDto> data = query.getResultList();
		return new SearchUserBookListResponseDto(data);
	}

	@Override
	@Cacheable(value = "weeklyBookCache", key = "bookForAWeek")
	@Scheduled(cron = "0 0/1 * * * *")
	public void saveWeeklyBooks() {
		List<WeeklyBooksDto> weeklyBooks = userBookRepository.getWeeklyBooks();

		// Sorted Set에 저장되는 순서를 기록하기 위한 변수
		long order = 0;

		for (WeeklyBooksDto book : weeklyBooks) {
			String hashKey = "weeklyBook:" + book.getIsbn();
			Map<String, String> bookMap = new HashMap<>();
			bookMap.put("isbn", book.getIsbn());
			bookMap.put("cover", book.getCover());
			bookMap.put("title", book.getTitle());
			bookMap.put("author", book.getAuthor());
			bookMap.put("publisher", book.getPublisher());
			bookMap.put("pubDate", book.getPubDate().toString());
			bookMap.put("bookDescription", book.getBookDescription());
			bookMap.put("priceStandard", book.getPriceStandard() + "");

			redisTemplate.opsForHash().putAll(hashKey, bookMap);

			// Sorted Set에 isbn을 order를 score로 추가
			redisTemplate.opsForZSet().add("weeklyBooksSortedSet", book.getIsbn(), order);

			redisTemplate.expire(hashKey, 7, TimeUnit.DAYS);

			// 다음 순서를 위해 증가
			order++;
		}
	}

	@Override
	public List<WeeklyBooksDto> getCachedWeeklyBooks() {
		List<WeeklyBooksDto> cachedBooks = new ArrayList<>();

		// Sorted Set에서 정렬된 isbn 목록을 가져옴
		Set<String> sortedIsbns = redisTemplate.opsForZSet().range("weeklyBooksSortedSet", 0, -1);

		if (sortedIsbns != null) {
			for (String isbn : sortedIsbns) {
				String hashKey = "weeklyBook:" + isbn;
				Map<Object, Object> bookMap = redisTemplate.opsForHash().entries(hashKey);

				WeeklyBooksDto book = new WeeklyBooksDto();
				book.setIsbn((String)bookMap.get("isbn"));
				book.setCover((String)bookMap.get("cover"));
				book.setTitle((String)bookMap.get("title"));
				book.setAuthor((String)bookMap.get("author"));
				book.setPublisher((String)bookMap.get("publisher"));
				book.setPubDate(LocalDateTime.parse((String)bookMap.get("pubDate")));
				book.setBookDescription((String)bookMap.get("bookDescription"));
				book.setPriceStandard(Integer.valueOf((String)bookMap.get("priceStandard")));

				cachedBooks.add(book);
			}
		}
		return cachedBooks;
	}
}
