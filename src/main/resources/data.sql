INSERT INTO tb_categorias (nome)
VALUES ('Cotidiano'),('LGP3'),('SISD'),('ESTA'),('PWEB'),('MOPN'),('PIE1');


INSERT INTO tb_usuarios (nome, email)
VALUES ('Gustavo', 'gustavo@email.com'),('Taylor Swift','taylor@email.com'),('Odete Roitman','odete@tca.com');


INSERT INTO tb_projetos (nome, descricao)
VALUES ('IFSP', 'Projeto feito para realização das tarefas relacionadas ao IFSP');

INSERT INTO tb_tarefas (titulo, descricao, id_responsavel, id_projeto, id_categoria, criacao, prazo, prioridade, status)
VALUES ('Fazer atividade avaliativa','Concluir atividade avaliativa de LGP3', 2,1,2,'2025-10-12','2025-10-15','ALTA','FAZENDO'),
       ('Estudar para prova de SISTEMAS DISTRIBUIDOS','Revisar os conteúdos vistos em aula e fazer exercícios', 1,1,3,'2025-10-10','2025-10-20','MEDIA','FEITO'),
       ('Finalizar projeto de PWEB','Completar todas as funcionalidades do projeto de PWEB', 1,1,5,'2025-10-05','2025-10-25','ALTA','A_FAZER'),
       ('Ler capítulo 4 do livro de MOPN','Compreender os conceitos apresentados no capítulo 4', 3,1,6,'2025-10-08','2025-10-18','BAIXA','EXCLUIDA'),
       ('Preparar apresentação para PIE1','Criar slides e ensaiar a apresentação', 1,1,7,'2025-10-11','2025-10-31','ALTA','A_FAZER');

