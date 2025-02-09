package br.com.madr.vo;

import br.com.madr.domain.User;
import br.com.madr.utils.StringUtils;
import io.quarkus.runtime.annotations.RegisterForReflection;

import java.io.Serial;
import java.io.Serializable;

@RegisterForReflection
public class UserVO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;
    private String username;
    private String email;
    private String senha;

    public UserVO() {
    }

    public UserVO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.senha = user.getSenha();
    }

    public User toEntity(){
        User userData = new User();

        userData.setId(id != null ? id : null);
        userData.setUsername(username != null ? StringUtils.sanitizeName(username) : null);
        userData.setEmail(email != null ? email : null);
        userData.setSenha(senha != null ? senha : null);

        return userData;
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
}
