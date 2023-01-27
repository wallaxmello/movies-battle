# movies-battle
Quiz de Filmes

Endpoints:


&nbsp;
Obs.: 


&nbsp;
1) No login, é gerado um bearer token, para ser usado, nos outro endpoints.
2) Utilizar o token no swagger(Opção Authorize) ou postman(Aba -> Authorization -> Type: Bearer Token)
3) Token dura 10 minutos!
4) /movies : Pelo Swagger, tem que passar o size com valor 2.
5) Pode usar os dados dos users já existente ou criar outros! username: user1; user2; user3. password: 123.

&nbsp;
Não precisam do token:


&nbsp;
/users : Cria um novo jogador.


&nbsp;
/login : Efetua o login com os dados do jogador.


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

&nbsp;
http://localhost:8080/swagger-ui.html

ou

&nbsp;
http://localhost:8080/v3/api-docs
