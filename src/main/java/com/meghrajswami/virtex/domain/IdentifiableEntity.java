package com.meghrajswami.virtex.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * Base identifiable entity.
 */
@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class IdentifiableEntity implements Model {
    /**
     * The unique id of the entity.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Simple name of the class
     */
    @Getter(onMethod = @__(@JsonIgnore))
    private String classType = getClass().getSimpleName();

    /**
     * This method override the equals method.
     *
     * @param target the target object
     * @return true if both entities have the same Id
     */
    @Override
    public boolean equals(Object target) {
        if (target instanceof IdentifiableEntity) {
            IdentifiableEntity entity = (IdentifiableEntity) target;
            if (entity.getId() == null) {
                return ((IdentifiableEntity) target).getId() == null;
            }
            return entity.getId().equals(this.id);
        }
        return false;
    }

    /**
     * This method override the hashCode method.
     *
     * @return the hash code of the entity
     */
    @Override
    public int hashCode() {
        if (id == null) {
            return 0;
        }
        return id.hashCode();
    }
}
