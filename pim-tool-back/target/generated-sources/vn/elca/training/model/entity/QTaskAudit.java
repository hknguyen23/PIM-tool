package vn.elca.training.model.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QTaskAudit is a Querydsl query type for TaskAudit
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QTaskAudit extends EntityPathBase<TaskAudit> {

    private static final long serialVersionUID = -100279439L;

    public static final QTaskAudit taskAudit = new QTaskAudit("taskAudit");

    public final EnumPath<TaskAudit.AuditType> auditType = createEnum("auditType", TaskAudit.AuditType.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath message = createString("message");

    public final NumberPath<Long> projectId = createNumber("projectId", Long.class);

    public final EnumPath<TaskAudit.Status> status = createEnum("status", TaskAudit.Status.class);

    public final DatePath<java.time.LocalDate> taskDeadline = createDate("taskDeadline", java.time.LocalDate.class);

    public final StringPath taskName = createString("taskName");

    public QTaskAudit(String variable) {
        super(TaskAudit.class, forVariable(variable));
    }

    public QTaskAudit(Path<? extends TaskAudit> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTaskAudit(PathMetadata metadata) {
        super(TaskAudit.class, metadata);
    }

}

