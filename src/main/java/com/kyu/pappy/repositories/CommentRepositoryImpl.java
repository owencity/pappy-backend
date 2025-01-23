package com.kyu.pappy.repositories;

import com.kyu.pappy.entities.Comment;
import com.kyu.pappy.entities.QComment;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    // 대댓글 불러오기
    @Override
    public List<Comment> findRepliesByCommentId(Long parentId) {
        QComment comment = QComment.comment1;

        return queryFactory.selectFrom(comment)
                .leftJoin(comment.replies).fetchJoin() // 대댓글과 대댓글의 자식까지 join
                .where(comment.parent.id.eq(parentId)) // 특정 댓글의 대댓글만 가져옴
                .fetch();
    }

}
