package vn.elca.training.model.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QGroupz is a Querydsl query type for Groupz
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QGroupz extends EntityPathBase<Groupz> {

    private static final long serialVersionUID = -1934963296L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QGroupz groupz = new QGroupz("groupz");

    public final QKey _super = new QKey(this);

    public final QEmployee employee;

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final StringPath name = createString("name");

    public final SetPath<Project, QProject> projects = this.<Project, QProject>createSet("projects", Project.class, QProject.class, PathInits.DIRECT2);

    //inherited
    public final NumberPath<Long> version = _super.version;

    public QGroupz(String variable) {
        this(Groupz.class, forVariable(variable), INITS);
    }

    public QGroupz(Path<? extends Groupz> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QGroupz(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QGroupz(PathMetadata metadata, PathInits inits) {
        this(Groupz.class, metadata, inits);
    }

    public QGroupz(Class<? extends Groupz> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.employee = inits.isInitialized("employee") ? new QEmployee(forProperty("employee"), inits.get("employee")) : null;
    }

}

