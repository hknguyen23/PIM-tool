package vn.elca.training.model.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QKey is a Querydsl query type for Key
 */
@Generated("com.querydsl.codegen.SupertypeSerializer")
public class QKey extends EntityPathBase<Key> {

    private static final long serialVersionUID = -329922374L;

    public static final QKey key = new QKey("key1");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> version = createNumber("version", Long.class);

    public QKey(String variable) {
        super(Key.class, forVariable(variable));
    }

    public QKey(Path<? extends Key> path) {
        super(path.getType(), path.getMetadata());
    }

    public QKey(PathMetadata metadata) {
        super(Key.class, metadata);
    }

}

