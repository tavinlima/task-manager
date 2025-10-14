package com.personal.task_manager.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="tb_categorias")
@Getter
@Setter
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCategoria;

    @Column(name = "nome", nullable = false, length = 60)
    private String nome;

    @Override
    public String toString() {
        return "Categoria{" +
                "id=" + idCategoria +
                ", nome='" + nome + '\'' +
                '}';
    }
}
