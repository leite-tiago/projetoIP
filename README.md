Este foi o meu primeiro projeto de Introdução à Programação (IP) na Faculdade de Ciências da Universidade de Lisboa (FCUL), foi feito com o meu colega Rodrigo Frutuoso.

Consiste na criação de uma versão do jogo Wordle no âmbito de IP (daí o nome Ipurdle), este projeto está dividido em duas partes (parte1 e parte2), o jogo é o mesmo mas na parte2 há uma componente gráfica (IpurdleGUI.java), o código está organizado por várias classes e foram usados conceitos mais avançados do que na parte1.

O objetivo do jogo é advinhar uma palavra secreta com um certo tamanho (wordSize, que por default é 5) com um número máximo de tentativas (maxGuesses, que por default é 6). Após cada tentativa é dada uma pista (clue). O jogo acaba se o jogador descobrir a palavra secreta ou se se acabarem as tentativas.

Na parte1, a pista dada consiste na palavra introduzida pelo jogador com cada letra numa cor que indica se a letra está na posição correta (VERDE), se está na posição errada (AMARELO) ou se não faz parte da palavra (VERMELHO).

Na parte2, a pista dada é representa através dos símbolos * (posição correta), 0 (posição errada) e _ (letra não pertence à palavra a descobrir) ou se fôr usada a interface gráfica é igual à parte1.
