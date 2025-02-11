package br.com.madr.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Objects;

@Entity
@Table(
        name = "user_db",
        schema = "madr",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"username"},
                        name = "uk_username_user_db"
                )
        }
)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "username", nullable = false, length = 50)
    @NotNull(message = "{user.username.notnull}")
    private String username;

    @Column(name = "email", nullable = false, length = 150)
    @NotNull(message = "{user.email.notnull}")
    private String email;

    @Column(name = "senha", nullable = false, length = 128)
    @NotNull(message = "{user.senha.notnull}")
    @Size(min = 6, max = 128, message = "{user.senha.size}")
    private String senha;

    public User() {
    }

    public User(Long id, String username, String email, String senha) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.senha = senha;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(username, user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(username);
    }
}
