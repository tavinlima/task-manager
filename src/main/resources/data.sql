INSERT INTO tb_categorias (nome)
VALUES ('Cotidiano'),('LGP3'),('SISD'),('ESTA'),('PWEB'),('MOPN'),('PIE1');


INSERT INTO tb_usuarios (nome, email)
VALUES ('Gustavo', 'gustavo@email.com'),('Taylor Swift','taylor@email.com'),('Odete Roitman','odete@tca.com');


INSERT INTO tb_projetos (nome, descricao)
VALUES ('IFSP', 'Projeto feito para realização das tarefas relacionadas ao IFSP');

INSERT INTO tb_tarefas (titulo, descricao, id_responsavel, id_projeto, id_categoria, criacao, prazo, prioridade, status)
VALUES ('Fazer atividade avaliativa','Concluir atividade avaliativa de LGP3', 2,1,2,'2025-10-12','2025-10-15','ALTA','FAZENDO')

