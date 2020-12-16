package org.bookms.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;


@Entity
@Table(name = "Book")
@NamedQueries({
        @NamedQuery(name = "Book.getListOfBooks", query = "select b from Book b"),
        @NamedQuery(name = "Book.findBookByAuthorId", query = "select b from Book b  where b.author.id = :id")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @JsonIgnore
    @ManyToOne()
    @JoinColumn(name = "author_id")
    private Author author;
}
