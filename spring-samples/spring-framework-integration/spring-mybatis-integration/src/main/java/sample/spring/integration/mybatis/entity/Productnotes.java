package sample.spring.integration.mybatis.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 产品描述表
 * </p>
 *
 * @author someone
 * @since 2022-03-20
 */
public class Productnotes implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer noteId;

    private String prodId;

    private LocalDateTime noteDate;

    private String noteText;

    public Integer getNoteId() {
        return noteId;
    }

    public void setNoteId(Integer noteId) {
        this.noteId = noteId;
    }
    public String getProdId() {
        return prodId;
    }

    public void setProdId(String prodId) {
        this.prodId = prodId;
    }
    public LocalDateTime getNoteDate() {
        return noteDate;
    }

    public void setNoteDate(LocalDateTime noteDate) {
        this.noteDate = noteDate;
    }
    public String getNoteText() {
        return noteText;
    }

    public void setNoteText(String noteText) {
        this.noteText = noteText;
    }

    @Override
    public String toString() {
        return "Productnotes{" +
            "noteId=" + noteId +
            ", prodId=" + prodId +
            ", noteDate=" + noteDate +
            ", noteText=" + noteText +
        "}";
    }
}
