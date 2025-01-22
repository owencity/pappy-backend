package com.kyu.pappy.repositories;

import com.kyu.pappy.entities.QComment;
import com.kyu.pappy.entities.QStory;
import com.kyu.pappy.entities.Story;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class StoryRepositoryImpl implements StoryRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public Story findByIdWithComments(Long id) {
        QStory story = QStory.story;
        QComment comment = QComment.comment1;

        return queryFactory.selectFrom(story)
                .leftJoin(story.comments, comment).fetchJoin()
                .leftJoin(comment.replies).fetchJoin() // 댓글의 자식(대댓글)까지 가져오기
                .where(story.id.eq((id)))
                .fetchOne();
    }

    @Override
    public List<Story> findStoryPagination(int page , int size) {
        QStory story = QStory.story;

        return queryFactory.selectFrom(story)
                .offset((long) (page - 1) * size)
                .limit(size)
                .fetch();
    }
}
