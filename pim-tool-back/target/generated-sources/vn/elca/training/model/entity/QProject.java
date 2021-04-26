package vn.elca.training.model.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProject is a Querydsl query type for Project
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QProject extends EntityPathBase<Project> {

    private static final long serialVersionUID = -457060364L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProject project = new QProject("project");

    public final QKey _super = new QKey(this);

    public final StringPath customer = createString("customer");

    public final SetPath<Employee, QEmployee> employees = this.<Employee, QEmployee>createSet("employees", Employee.class, QEmployee.class, PathInits.DIRECT2);

    public final DatePath<java.time.LocalDate> endDate = createDate("endDate", java.time.LocalDate.class);

    public final DatePath<java.time.LocalDate> finishingDate = createDate("finishingDate", java.time.LocalDate.class);

    public final QGroupz groupz;

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final BooleanPath isActivated = createBoolean("isActivated");

    public final DatePath<java.time.LocalDate> lastModifiedDate = createDate("lastModifiedDate", java.time.LocalDate.class);

    public final StringPath projectName = createString("projectName");

    public final NumberPath<Long> projectNumber = createNumber("projectNumber", Long.class);

    public final DatePath<java.time.LocalDate> startDate = createDate("startDate", java.time.LocalDate.class);

    public final StringPath status = createString("status");

    public final SetPath<Task, QTask> tasks = this.<Task, QTask>createSet("tasks", Task.class, QTask.class, PathInits.DIRECT2);

    //inherited
    public final NumberPath<Long> version = _super.version;

    public QProject(String variable) {
        this(Project.class, forVariable(variable), INITS);
    }

    public QProject(Path<? extends Project> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProject(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProject(PathMetadata metadata, PathInits inits) {
        this(Project.class, metadata, inits);
    }

    public QProject(Class<? extends Project> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.groupz = inits.isInitialized("groupz") ? new QGroupz(forProperty("groupz"), inits.get("groupz")) : null;
    }

}

