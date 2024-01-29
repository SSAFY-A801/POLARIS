package com.ssafy.polaris.book.repository;

import com.ssafy.polaris.book.domain.UserBook;
import com.ssafy.polaris.book.dto.UserBookResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserBookRepository extends JpaRepository<UserBook, String> {

    @Query("select ub.id, b.isbn, b.title, b.cover, b.author, ub.isOpened, ub.isOwned, " +
            "b.series.id, b.series.name from UserBook ub inner join Book b on ub.book = b.isbn " +
            "where ub.user =: userId")
    List<UserBookResponse> findAllByUserId(@Param("userId") Long userId);
}
