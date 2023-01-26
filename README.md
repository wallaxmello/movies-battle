# movies-battle
Quiz de Filmes

Endpoints:


&nbsp;
Obs.: Token dura 10 minutos!

&nbsp;
Não precisam do token:


&nbsp;
/users : Cria um novo jogador.


&nbsp;
/login : Efetua o login com os dados do jogador.
         Obs.: No login, é gerado um bearer token, para ser usado, nos outro endpoints.
         Seleciona na aba Authorization do postman, o type: Bearer Toke. Cola o token, no campo Token!


&nbsp;
Precisam do token:

&nbsp;
/movies : Busca os filmes, para votação.


&nbsp;
/quizzes : Manda o Id do filme e o user, para votação.


&nbsp;
/ratings : Consulta o ranking dos jogadores.


&nbsp;
/stop : O jogador que quiser parar, é excluído da partida.


&nbsp;
# Para mais informações, consultar o Swagger!
