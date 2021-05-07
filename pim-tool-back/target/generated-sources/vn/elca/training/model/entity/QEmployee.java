package vn.elca.training.model.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QEmployee is a Querydsl query type for Employee
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QEmployee extends EntityPathBase<Employee> {

    private static final long serialVersionUID = 908197171L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QEmployee employee = new QEmployee("employee");

    public final QAbstractEntity _super = new QAbstractEntity(this);

    public final DatePath<java.time.LocalDate> birthDate = createDate("birthDate", java.time.LocalDate.class);

    public final StringPath firstName = createString("firstName");

    public final QGroup group;

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final StringPath lastName = createString("lastName");

    public final SetPath<Project, QProject> projects = this.<Project, QProject>createSet("projects", Project.class, QProject.class, PathInits.DIRECT2);

    //inherited
    public final NumberPath<Long> version = _super.version;

    public final StringPath visa = createString("visa");

    public QEmployee(String variable) {
        this(Employee.class, forVariable(variable), INITS);
    }

    public QEmployee(Path<? extends Employee> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QEmployee(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QEmployee(PathMetadata metadata, PathInits inits) {
        this(Employee.class, metadata, inits);
    }

    public QEmployee(Class<? extends Employee> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.group = inits.isInitialized("group") ? new QGroup(forProperty("group"), inits.get("group")) : null;
    }

}

