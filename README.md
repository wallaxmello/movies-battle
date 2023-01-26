# movies-battle
Quiz de Filmes

Endpoints:

Não precisam do token:

/users : Cria um novo jogador.
/login : Efetua o login com os dados do jogador.
         Obs.: No login, é gerado um bearer token, para ser usado, nós outro endpoints.
         Seleciona na aba Authorization do postman, o type: Bearer Toke. Cola o token, no campo Token!

Precisam do token:

/movies : Busca os filmes, para votação.
/quizzes : Manda o Id do filme e o user, para votação.
/ratings : Consulta o ranking dos jogadores.
/stop : O jogador que quiser parar, é excluído da partida.
