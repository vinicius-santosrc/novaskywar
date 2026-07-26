![image](https://i.ytimg.com/vi/GXyygEf0DlM/maxresdefault.jpg)


# 🚀 NovaSkyWar 2.3.7
NovaSkyWar é um plugin do mini-game "SkyWar" desenvolvido para Minecraft 1.8x utilizando Java Maven. Ele é OpenSource e está atualmente em versão de testes aberta e possui diversas funcionalidades para melhorar a jogabilidade comparado ao "SkyWar padrão".

## Funcionalidades
* Sistema de Kits: Permite aos jogadores escolher diferentes kits pré definidos com habilidades únicas no início da partida. (criação/edição de kits já foi pensada e em breve será desenvolvida).
* Gerenciamento de money(créditos) de acordo com ações no jogo.
* Funcionalidades do Plugin:
    - Sistema de Reset World:
        - Ao finalizar uma partida, caso o mundo da arena esteja dentro da pasta `/backups`, o mundo será resetado de acordo com o backup na pasta.
    - Sistema de Placas:
        - É possível criar placas SkyWar de diferentes arenas para fácil acesso no Lobby.
        - Placa:
            - `Linha 1: [SkyWar]`
            - `Linha 2: <nome-da-arena>`
            - `Linha 3: <sala>`
    - Criação de arenas:
        - É possível criar arenas de maneira fácil.
        - Comandos:
            - `/skywar create <arena> <maxplayers>`
            - `/skywar set <arena> <feast> <pos1/pos2>`
            - `/skywar set <arena> <island> <numeroIlha>`
            - `/skywar set <arena> <spectator>`
            - `/skywar set <arena> <waitingLobby>`
            - Após isso, você deve criar uma cópia do mundo de onde está sua arena e jogar dentro da pasta `/backups` para que, após a partida, aconteça o reset do mapa.
        - Após todos comandos, sua arena está pronta para ser jogada. Os comandos set podem ser utilizados após a criação da partida/set de todas propriedades.
        - Caso queira editar mais propriedades, entre no arquivo `config.yml` localizado na pasta `/SkyWar` dentro de `/plugins` no seu servidor
    - Reconhecimento Feast:
        - Bloqueio de Enderpearls no feast para evitar vantagem inicial.
        - Sistema de reconhecimento do primeiro jogador ao alcança-lo, avisando a todos na partida.
    - Reconhecimento Kills:
        - Reconhecimento do "First Blood" realizando alerta a todos da partida.
    - Listeners WaitingLobby:
        - Desabilita quebrar blocos, qualquer tipo de dano e ao cair no void, será retornado ao waitingLobby.
    - Final da partida:
        - Com o ultimo jogador vivo, ele será teleportado ao centro da partida, onde será parabenizado com mensagens e fogos de artifícios de todas as ilhas.
        - Todos os jogadores receberão uma mensagem de resumo de créditos obtidos naquela partida.
    - Estado do player:
        - Seu nome será alterado no TAB e no GAME para caso vivo: verde, e caso eliminado: vermelho, para melhor reconhecimento dos outros jogadores.
    - Sistema de Mensagens Personalizadas: 
        - Personalize mensagens de início, fim e eventos importantes durante a partida.
    - Modo Espectador:
        - Jogadores que são eliminados entram no modo espectador, permitindo que assistam o restante da partida sem interferir.

<!-- ## Vídeo de Demonstração
Confira o vídeo de teste da versão funcional do plugin:
<a href="https://www.youtube.com/watch?v=6zuB15Lgyrc&t=2s&ab_channel=Vin%C3%ADciusSantos">Assistir</a> -->

## Como Contribuir
Caso queira ajudar no desenvolvimento do NovaSkyWar, aqui estão algumas maneiras de contribuir:

* Dê uma estrela ⭐: Mostre que você gostou do projeto.
* Faça um Fork 🍴: Crie uma cópia do repositório e envie suas melhorias através de Pull Requests.
* Reporte bugs 🐞: Encontrou algum problema? Abra uma Issue no GitHub para que possamos resolver.
