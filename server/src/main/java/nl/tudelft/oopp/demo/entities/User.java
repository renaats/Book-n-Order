package nl.tudelft.oopp.demo.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User {
    @Id
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "score")
    private int score;

    public User() {
    }

    /**
     * Create a new Quote instance.
     *
     * @param id Unique identifier as to be used in the database.
     * @param name Username of the user.
     * @param score Score of the user.
     */
    public User(long id, String name, int score) {
        this.id = id;
        this.name = name;
        this.score = score;
    }

    public String getUser() {
        return name;
    }

    public int getScore() {
        return score;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        User user = (User) o;

        return id == user.id;
    }
}
