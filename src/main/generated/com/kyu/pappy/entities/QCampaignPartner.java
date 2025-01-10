package com.kyu.pappy.entities;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCampaignPartner is a Querydsl query type for CampaignPartner
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCampaignPartner extends EntityPathBase<CampaignPartner> {

    private static final long serialVersionUID = 1533113125L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCampaignPartner campaignPartner = new QCampaignPartner("campaignPartner");

    public final QCampaign campaign;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DateTimePath<java.time.LocalDateTime> participationDate = createDateTime("participationDate", java.time.LocalDateTime.class);

    public final QUser user;

    public QCampaignPartner(String variable) {
        this(CampaignPartner.class, forVariable(variable), INITS);
    }

    public QCampaignPartner(Path<? extends CampaignPartner> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCampaignPartner(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCampaignPartner(PathMetadata metadata, PathInits inits) {
        this(CampaignPartner.class, metadata, inits);
    }

    public QCampaignPartner(Class<? extends CampaignPartner> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.campaign = inits.isInitialized("campaign") ? new QCampaign(forProperty("campaign")) : null;
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user")) : null;
    }

}

