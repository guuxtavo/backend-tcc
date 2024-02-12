package com.ads.tcc.entities.celula;

import com.ads.tcc.entities.user.Funcionario;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "tb_celula")
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Celula implements Serializable {

    private static final Long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    private Long id;

    @NotNull(message = "O campo 'numero' não pode ser vazio")
    private Integer numero;

    @NotBlank(message = "O campo 'status' não pode ser vazio")
    private String status;

    @OneToMany(mappedBy = "celula")
    private List<Funcionario> funcionarios;

}


//    @ManyToMany
//    @JoinTable(name = "tb_funcionarios_por_celula",
//            joinColumns = @JoinColumn(name = "celula_id"),
//            inverseJoinColumns = @JoinColumn(name = "funcionario_id"))
//    private List<Funcionario> funcionarios;




