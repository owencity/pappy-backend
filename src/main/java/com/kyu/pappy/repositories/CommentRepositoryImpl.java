package com.kyu.pappy.repositories;

import com.kyu.pappy.entities.Comment;
import com.kyu.pappy.entities.QComment;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Comment> getCommentsWithReplies(Long storyId) {
        QComment comment = QComment.comment1;
        return  queryFactory
                .selectFrom(comment)
                .where(comment.commentStory.id.eq(storyId)
                        .and(comment.parent.isNull()))
                .fetch();
    }
}
