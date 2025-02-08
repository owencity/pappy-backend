package com.kyu.pappy.entities;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QChatroom is a Querydsl query type for Chatroom
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QChatroom extends EntityPathBase<Chatroom> {

    private static final long serialVersionUID = 1672366918L;

    public static final QChatroom chatroom = new QChatroom("chatroom");

    public final SetPath<ChatroomMapping, QChatroomMapping> chatroomMappingSet = this.<ChatroomMapping, QChatroomMapping>createSet("chatroomMappingSet", ChatroomMapping.class, QChatroomMapping.class, PathInits.DIRECT2);

    public final DateTimePath<java.time.LocalDateTime> createAt = createDateTime("createAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath title = createString("title");

    public QChatroom(String variable) {
        super(Chatroom.class, forVariable(variable));
    }

    public QChatroom(Path<? extends Chatroom> path) {
        super(path.getType(), path.getMetadata());
    }

    public QChatroom(PathMetadata metadata) {
        super(Chatroom.class, metadata);
    }

}

