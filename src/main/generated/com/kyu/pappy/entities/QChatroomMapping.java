package com.kyu.pappy.entities;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QChatroomMapping is a Querydsl query type for ChatroomMapping
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QChatroomMapping extends EntityPathBase<ChatroomMapping> {

    private static final long serialVersionUID = -1318171992L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QChatroomMapping chatroomMapping = new QChatroomMapping("chatroomMapping");

    public final QChatroom chatroom;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DateTimePath<java.time.LocalDateTime> lastCheckedAt = createDateTime("lastCheckedAt", java.time.LocalDateTime.class);

    public final QUser user;

    public QChatroomMapping(String variable) {
        this(ChatroomMapping.class, forVariable(variable), INITS);
    }

    public QChatroomMapping(Path<? extends ChatroomMapping> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QChatroomMapping(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QChatroomMapping(PathMetadata metadata, PathInits inits) {
        this(ChatroomMapping.class, metadata, inits);
    }

    public QChatroomMapping(Class<? extends ChatroomMapping> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.chatroom = inits.isInitialized("chatroom") ? new QChatroom(forProperty("chatroom")) : null;
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user")) : null;
    }

}

