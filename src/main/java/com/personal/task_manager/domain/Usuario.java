package com.personal.task_manager.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="tb_usuarios")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;

    @Column(name="nome", nullable=false, length=60)
    private String nome;

    @Column(name="email", nullable=false, length=256)
    private String email;

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + idUsuario +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}