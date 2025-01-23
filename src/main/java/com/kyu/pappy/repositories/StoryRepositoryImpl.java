package com.kyu.pappy.repositories;

import com.kyu.pappy.dtos.CommentDto;
import com.kyu.pappy.dtos.StoryDto;
import com.kyu.pappy.entities.Comment;
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

        Story storyEntity = queryFactory.selectFrom(story)
                .leftJoin(story.comments, comment).fetchJoin()
                .where(story.id.eq(id)) // 최상위 댓글만 가져오기
                .fetchOne();

        if (storyEntity == null) {
            throw new IllegalArgumentException("Story not found with id: " + id);
        }

        return storyEntity;
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
