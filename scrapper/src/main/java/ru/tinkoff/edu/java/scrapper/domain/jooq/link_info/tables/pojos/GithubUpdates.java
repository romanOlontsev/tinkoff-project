/*
 * This file is generated by jOOQ.
 */
package ru.tinkoff.edu.java.scrapper.domain.jooq.link_info.tables.pojos;


import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.processing.Generated;
import java.beans.ConstructorProperties;
import java.io.Serializable;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "https://www.jooq.org",
        "jOOQ version:3.18.3"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class GithubUpdates implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private Integer forksCount;
    private Integer watchers;

    public GithubUpdates() {}

    public GithubUpdates(GithubUpdates value) {
        this.id = value.id;
        this.forksCount = value.forksCount;
        this.watchers = value.watchers;
    }

    @ConstructorProperties({ "id", "forksCount", "watchers" })
    public GithubUpdates(
        @NotNull Long id,
        @Nullable Integer forksCount,
        @Nullable Integer watchers
    ) {
        this.id = id;
        this.forksCount = forksCount;
        this.watchers = watchers;
    }

    /**
     * Getter for <code>LINK_INFO.GITHUB_UPDATES.ID</code>.
     */
    @jakarta.validation.constraints.NotNull
    @NotNull
    public Long getId() {
        return this.id;
    }

    /**
     * Setter for <code>LINK_INFO.GITHUB_UPDATES.ID</code>.
     */
    public void setId(@NotNull Long id) {
        this.id = id;
    }

    /**
     * Getter for <code>LINK_INFO.GITHUB_UPDATES.FORKS_COUNT</code>.
     */
    @Nullable
    public Integer getForksCount() {
        return this.forksCount;
    }

    /**
     * Setter for <code>LINK_INFO.GITHUB_UPDATES.FORKS_COUNT</code>.
     */
    public void setForksCount(@Nullable Integer forksCount) {
        this.forksCount = forksCount;
    }

    /**
     * Getter for <code>LINK_INFO.GITHUB_UPDATES.WATCHERS</code>.
     */
    @Nullable
    public Integer getWatchers() {
        return this.watchers;
    }

    /**
     * Setter for <code>LINK_INFO.GITHUB_UPDATES.WATCHERS</code>.
     */
    public void setWatchers(@Nullable Integer watchers) {
        this.watchers = watchers;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final GithubUpdates other = (GithubUpdates) obj;
        if (this.id == null) {
            if (other.id != null)
                return false;
        }
        else if (!this.id.equals(other.id))
            return false;
        if (this.forksCount == null) {
            if (other.forksCount != null)
                return false;
        }
        else if (!this.forksCount.equals(other.forksCount))
            return false;
        if (this.watchers == null) {
            if (other.watchers != null)
                return false;
        }
        else if (!this.watchers.equals(other.watchers))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());
        result = prime * result + ((this.forksCount == null) ? 0 : this.forksCount.hashCode());
        result = prime * result + ((this.watchers == null) ? 0 : this.watchers.hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("GithubUpdates (");

        sb.append(id);
        sb.append(", ").append(forksCount);
        sb.append(", ").append(watchers);

        sb.append(")");
        return sb.toString();
    }
}
