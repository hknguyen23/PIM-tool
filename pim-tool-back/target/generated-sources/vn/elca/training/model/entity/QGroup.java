package vn.elca.training.model.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QGroup is a Querydsl query type for Group
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QGroup extends EntityPathBase<Group> {

    private static final long serialVersionUID = 768865818L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QGroup group = new QGroup("group1");

    public final QAbstractEntity _super = new QAbstractEntity(this);

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final QEmployee leader;

    public final StringPath name = createString("name");

    public final SetPath<Project, QProject> projects = this.<Project, QProject>createSet("projects", Project.class, QProject.class, PathInits.DIRECT2);

    //inherited
    public final NumberPath<Long> version = _super.version;

    public QGroup(String variable) {
        this(Group.class, forVariable(variable), INITS);
    }

    public QGroup(Path<? extends Group> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QGroup(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QGroup(PathMetadata metadata, PathInits inits) {
        this(Group.class, metadata, inits);
    }

    public QGroup(Class<? extends Group> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.leader = inits.isInitialized("leader") ? new QEmployee(forProperty("leader"), inits.get("leader")) : null;
    }

}

