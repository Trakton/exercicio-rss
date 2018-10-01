# Tarefa #1 - RSS 

Esta tarefa envolve os conceitos de UI widgets, tarefas assíncronas, RecyclerView, Custom Adapters, Intents, Permissions. 
Faça um clone ou fork deste projeto, siga os passos na ordem sugerida e marque mais abaixo, na sua resposta, quais os passos completados. 
Para entregar o exercício, responda o [formulário de entrega](https://docs.google.com/forms/d/e/1FAIpQLSc0L1cCzVb9uro-7RX69B2oyery0xNuC0FOpgArVVyr6gUF1A/viewform) até 16/09/2018, às 23h59.

  1. Faça o porting do código disponível em `MainActivityAntigo.java` para Kotlin, colocando o código correspondente na classe `MainActivity.kt`.
  2. Faça o carregamento do arquivo XML usando uma tarefa assíncrona, seja por meio de `AsyncTask`, seja por meio de `Anko` e `doAsync`. 
  3. Se ainda estiver dando erro, adicione a permissão para acessar internet.  
  4. Altere a aplicação de forma que passe a processar o arquivo XML usando a função `parse` da classe `ParserRSS`. Uma vez processado o XML por meio do parser, é retornado um objeto do tipo `List<ItemRSS>`. 
  5. Use este objeto para popular um `RecyclerView` por meio de um `Adapter` --- o widget deve manter o mesmo id do TextView (`conteudoRSS`).
  6. Crie um adapter personalizado para mostrar título e data para cada item do feed, usando o layout em `res/layout/itemlista.xml` como base. Este layout não deve ser alterado.
  7. Faça com que, ao clicar em um título, o usuário seja direcionado para o navegador. Opcionalmente, pode abrir em uma nova activity com `WebView`.
  8. Modifique a aplicação para que passe a carregar o endereço do feed a partir de uma string disponível em `res/values/strings.xml` com a chave `rssfeed`.

---

# Orientações

  - Comente o código que você desenvolver, explicando o que cada parte faz.
  - Entregue o exercício *mesmo que não tenha completado todos os itens* listados. Marque abaixo apenas o que completou.

----

# Status

| Passo | Completou? |
| ------ | ------ |
| 1 | **sim** |
| 2 | **sim** |
| 3 | **sim** |
| 4 | **sim** |
| 5 | **sim** |
| 6 | **sim** |
| 7 | **sim** |
| 8 | **sim** |


#PARTE 2

# Exercício #3 - RSS parte 2

A ideia deste exercício é aplicar os conceitos de `Service`, `BroadcastReceiver`, `SQLite`, `RecyclerView`, entre outros. 

Caso você não tenha feito a [parte 1 do exercício](https://github.com/if710/exercicio-rss) use o projeto disponível [neste link](https://github.com/if1001/exercicio2-rss) como referência. Reforçando que esta referência está escrita em Java. Portanto, caso já tenha feito a parte 1, sugiro que você continue a partir da sua resolução. 
A aplicação RSS disponível no [repositório de referência](https://github.com/if1001/exercicio2-rss) é uma versão atualizada da que foi passada como exercício anterior, mas *ainda incompleta*. Observe os passos listados abaixo. 
Esta versão está usando a classe `SQLiteRSSHelper` para gerenciar o banco de dados `SQLite` como forma de persistir os dados. 
Isto é, após o download e parsing do RSS, a lista de itens do feed está sendo armazenada no banco, ao invés de exibida diretamente na tela. 
Existem dois objetos `AsyncTask`. Um é responsável por carregar o XML da internet e salvar no banco. Se tudo correr bem, outro AsyncTask executa para carregar as notícias do banco de dados e exibir na tela. 

Siga os passos na ordem sugerida e marque mais abaixo, na sua resposta, quais os passos completados. 
Para entregar o exercício, responda o [formulário de entrega](https://docs.google.com/forms/d/e/1FAIpQLSeP0D2VaDDtG16w4OCc_ttU43QGCwcMq9b1GM8RdWyxUypSyg/viewform) até 30/09/2018, às 23h59.

  9. Modifique a aplicação para que passe a carregar o endereço do feed a partir de uma `SharedPreferences` com a chave `rssfeed`. Inclua a possibilidade de alterar a `SharedPreference` (`rssfeed`) incluindo um botão na `ActionBar` da aplicação. Ao clicar no botão, uma `Activity` deve ser exibida com base em uma `PreferenceScreen` gerada automaticamente por meio de um `Fragment` que estende a classe `PreferenceFragment`, como visto em sala. Defina um arquivo em `res/xml/preferencias.xml` para estruturar a tela.
  10. A classe `SQLiteRSSHelper` já tem toda a configuração do banco. No entanto, ainda é necessário implementar os métodos de manipulação do banco de dados (da linha 73 em diante), que estão em aberto ainda. A implementação do método `getItems` deve retornar apenas os itens não lidos;
  11. Modifique a aplicação de forma que ao clicar em um item RSS, o link seja aberto no navegador e a notícia seja marcada como lida no banco;
  12. Altere a aplicação de forma a usar um `Service` para fazer o download e persistência dos itens do feed no banco. Ou seja, a ideia aqui é mover o código que atualmente está no `AsyncTask` ou `doAsync` (dependendo da solução adotada por você no exercício anterior) que carrega o feed a partir da internet para um `Service`. Dica: use `IntentService`;
  13. Ao finalizar a tarefa, o `Service` deve enviar um `broadcast` avisando que terminou;
  14. Use um `BroadcastReceiver` registrado dinamicamente, para quando o usuário estiver com o app em primeiro plano, a atualização da lista de itens ser feita de forma automática;
  15. Se o usuário não estiver com o app em primeiro plano, um outro `BroadcastReceiver` registrado estaticamente deve exibir uma notificação, apenas se houver alguma notícia nova;

---

# Orientações

  - Comente o código que você desenvolver, explicando o que cada parte faz.
  - Entregue o exercício *mesmo que não tenha completado todos os itens* listados. Marque abaixo apenas o que completou.

----

# Status

| Passo | Completou? |
| ------ | ------ |
| 9 | **sim** |
| 10 | **sim** |
| 11 | **sim** |
| 12 | **sim** |
| 13 | **sim** |
| 14 | **sim** |
| 15 | **sim** |

