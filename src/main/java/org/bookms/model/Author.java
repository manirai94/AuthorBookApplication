package org.bookms.model;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(name = "Author")
@NamedQueries({@NamedQuery(name = "Author.findAllAuthors", query = "select e from Author e")})
public class Author implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    @NotBlank(message = "Please enter the name")
    private String name;


    @JsonIgnore
    @OneToMany(mappedBy = "author")
    private List<Book> books;
}
