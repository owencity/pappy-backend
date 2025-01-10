package com.kyu.pappy.repositories;

import com.kyu.pappy.entities.QComment;
import com.kyu.pappy.entities.QStory;
import com.kyu.pappy.entities.Story;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class StoryRepositoryImpl implements StoryRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public Story findByIdWithComments(Long id) {
        QStory story = QStory.story;
        QComment comment = QComment.comment1;

        return queryFactory.selectFrom(story)
                .leftJoin(story.comments, comment).fetchJoin()
                .where(story.id.eq((id)))
                .fetchOne();
    }
}
