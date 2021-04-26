package vn.elca.training.model.entity;

import javax.persistence.*;

@MappedSuperclass
public abstract class Key {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    protected Long id;

    @Version
    @Column(name = "Version", nullable = false)
    protected Long version;

    public Key(Long id, Long version) {
        this.id = id;
        this.version = version;
    }

    public Key(Long version) {
        this.version = version;
    }

    public Key() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}
