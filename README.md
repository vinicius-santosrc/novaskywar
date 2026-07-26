![image](https://i.ytimg.com/vi/GXyygEf0DlM/maxresdefault.jpg)

# 🚀 NovaSkyWar 2.3.7

O **NovaSkyWar** é um plugin Open Source do minigame **SkyWar** para **Minecraft 1.8.x**, desenvolvido em **Java** utilizando **Maven**.

O projeto encontra-se em fase de testes e possui diversas funcionalidades que aprimoram a experiência de jogo em relação ao SkyWar tradicional, além de uma arquitetura organizada para facilitar futuras expansões.

---

# ✨ Funcionalidades

## 🛡️ Sistema de Kits
Permite que os jogadores escolham kits com habilidades exclusivas antes do início da partida.

> O sistema de criação e edição de kits já foi planejado e será disponibilizado em versões futuras.

---

## 💰 Sistema de Créditos
Os jogadores recebem créditos (money) de acordo com suas ações durante a partida.

---

## 🌍 Sistema de Reset de Mundo
Ao término da partida, caso exista um backup do mundo da arena dentro da pasta:

```
/backups
```

o mapa será restaurado automaticamente para seu estado original.

---

## 📋 Sistema de Placas

É possível criar placas para entrada rápida nas arenas diretamente pelo lobby.

### Formato da placa

```
Linha 1: [SkyWar]
Linha 2: <nome-da-arena>
Linha 3: <sala>
```

---

## 🏝️ Sistema de Criação de Arenas

As arenas podem ser criadas rapidamente através de comandos.

### Criar uma arena

```
/skywar create <arena> <maxplayers>
```

### Configurar propriedades

```
/skywar set <arena> feast pos1
/skywar set <arena> feast pos2
/skywar set <arena> island <numeroIlha>
/skywar set <arena> spectator
/skywar set <arena> waitingLobby
```

Após finalizar a configuração:

1. Faça uma cópia do mundo da arena.
2. Coloque essa cópia na pasta:

```
/backups
```

Assim, sempre que a partida terminar, o mundo será restaurado automaticamente.

Caso deseje configurar propriedades adicionais, basta editar o arquivo:

```
plugins/SkyWar/config.yml
```

---

## 🎁 Sistema de Feast

- Reconhecimento automático da área do Feast.
- Bloqueio do uso de Ender Pearls durante o Feast para evitar vantagens indevidas.
- Anúncio para todos os jogadores quando o primeiro participante alcançar o Feast.

---

## ⚔️ Sistema de Kills

- Reconhecimento automático do **First Blood**.
- Mensagens personalizadas para eliminações.

---

## 🏠 Waiting Lobby

Enquanto a partida não inicia:

- Quebrar blocos é desativado.
- Jogadores não recebem dano.
- Ao cair no void, o jogador é teleportado novamente para o Waiting Lobby.

---

## 👑 Final da Partida

Quando resta apenas um jogador vivo:

- O vencedor é teleportado para o centro da arena.
- Fogos de artifício são lançados a partir das ilhas.
- Mensagens de vitória são exibidas.
- Todos recebem um resumo dos créditos obtidos durante a partida.

---

## 👤 Estado dos Jogadores

O plugin altera automaticamente o nome do jogador:

- 🟢 Verde para jogadores vivos.
- 🔴 Vermelho para jogadores eliminados.

A alteração é aplicada tanto no TAB quanto acima do personagem.

---

## 💬 Sistema de Mensagens

Todas as mensagens importantes do plugin podem ser personalizadas através do arquivo de configuração.

Exemplos:

- Início da partida
- Fim da partida
- PvP habilitado
- Feast
- Eliminações
- Vitória

---

## 👻 Modo Espectador

Jogadores eliminados entram automaticamente no modo espectador, podendo acompanhar a partida até seu término sem interferir na jogabilidade.

---

# 🤝 Como contribuir

Quer ajudar no desenvolvimento do NovaSkyWar?

Você pode contribuir de diversas maneiras:

- ⭐ Deixe uma estrela no repositório.
- 🍴 Faça um Fork e envie melhorias através de Pull Requests.
- 🐞 Reporte bugs abrindo uma Issue.
- 💡 Sugira novas funcionalidades.

Toda contribuição é bem-vinda!

---

## 📌 Status

> 🚧 Projeto em desenvolvimento e testes.
>
> Novas funcionalidades e melhorias estão sendo implementadas continuamente.