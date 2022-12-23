package com.affiancesolutions.kingtiger.ktbtracker.server.model;

import com.affiancesolutions.kingtiger.ktbtracker.server.model.dao.JournalPostsDAO;
import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@EntityListeners(JournalPostsDAO.class)
@Table(name = "JOURNAL_POSTS")
@NamedQueries({
        @NamedQuery(name = "JournalPost.findAll",
                query = "SELECT t FROM JournalPost AS t"
                        + " ORDER BY t.metadata.created DESC"),
        @NamedQuery(name = "JournalPost.findAllByCandidate",
                query = "SELECT t FROM JournalPost AS t"
                        + " WHERE t.candidate = :candidate"
                        + " ORDER BY t.metadata.created DESC"),
        @NamedQuery(name = "JournalPost.findRangeByCandidate",
                query = "SELECT t FROM JournalPost AS t"
                        + " WHERE t.candidate = :candidate"
                        + " AND t.metadata.created BETWEEN :from_date AND :to_date"
                        + " ORDER BY t.metadata.created DESC"),
        @NamedQuery(name = "JournalPost.countDaysJournaledByCandidate",
                query = "SELECT COUNT(DISTINCT CAST(t.metadata.created AS DATE))"
                        + " FROM JournalPost AS t"
                        + " WHERE t.candidate = :candidate"
                        + " AND CAST(t.metadata.created AS DATE) BETWEEN :from_date AND :to_date"),
        @NamedQuery(name = "JournalPost.findDaysJournaledByCandidate",
                query = "SELECT DISTINCT CAST(t.metadata.created AS DATE) FROM JournalPost AS t"
                        + " WHERE t.candidate = :candidate"
                        + " AND CAST(t.metadata.created AS DATE) BETWEEN :from_date AND :to_date")
})
public class JournalPost implements Serializable {

    /**
     * Versioning UID for serialization.
     */
    private static final long serialVersionUID = -955709902108275640L;

    /**
     * The auto-generated ID for this entity.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private int id;

    /**
     * Object Relational Model (ORM) relationship to the candidate (Candidate) being tracked.
     */
    @ManyToOne
    @JoinColumn(name = "CANDIDATE_ID", nullable = false)
    private Candidate candidate;

    /**
     * Title assigned to the journal post.
     */
    @Column(name = "TITLE", nullable = false)
    private String title;

    @Column(name = "ALIAS", nullable = false)
    private String alias;

    @Column(name = "SLUG", nullable = false, columnDefinition = "VARCHAR(100)")
    private String slug;

    @Column(name = "SUMMARY")
    private String summary;

    @Column(name = "PUBLISHED", nullable = false)
    private boolean published;

    @Lob
    @Column(name = "CONTENT")
    private String content;

    @Embedded
    private Metadata metadata;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Candidate getCandidate() {
        return candidate;
    }

    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    @Override
    public String toString() {
        return String.format("JournalPost(id=%d, candidate=%d, created=%s, title='%s')",
                getId(), getCandidate().getId(), getMetadata().getCreated(), getTitle());
    }
}
