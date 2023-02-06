package test;

import com.sun.javafx.sg.prism.NGNode;
import com.sun.javafx.sg.prism.NGText;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "new_entity")
public class NewEntity {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "age", nullable = false)
    private String age;

    public Long getId() {
        NGNode node = new NGText();
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @PostRemove
    public void postRemove() {

    }

    @PostUpdate
    public void postUpdate() {

    }
}