## Periode B : Projet Go
### Groupe 202:
* KACI Said
* CHEVILY Pierre
* MAATOUGUI Nassim
* BOUGAMZA Sofiane
# Rapport

## Ce qui marche : 
* SPRINT 1 : les commandes showboard, quit, boardsize
* SPRINT 2 : play
* SPRINT 3 : faire une IA qui joue aléatoirement 
- Une commande privée à choisir, comme :
- player black random
- player white human

## Pas fait ou qui ne marche pas :

La capture des pierres ne fonctionne pas entièrement, la faute à la fonction getNbLiberties, qui ne va pas assez loin dans la récursivité : une pierre peut demander à ses plus proches voisins combien de libertés ils ont, mais ne va pas jusqu'aux voisins de ses voisins. La capture fonctionne donc pour les chaînes de longueur 2, mais peut présenter des dysfonctionnements pour les longueurs supérieures.  
Capture des pierres ennemies presque impossible quand il y a IA contre IA du fait que l'IA ne joue pas si, aux coordonnées où elle compte jouer, la pièce aurait potentiellement 0 liberté même si c'est pour capturer une autre pierre ensuite.

## Choix de conception :
* Nous avons choisi qu'un joueur restait le même au cours d'une partie, et que, par conséquent, on ne pouvait pas le redéfinir.
* En ce qui concerne l'IA, nous la faisons jouer aléatoirement sur une case libre du goban. Une partie IA vs IA s'arrêtera dès lors qu'une IA ne peut plus jouer.
* La longueur du plateau est de 19 par défaut, comme la version la plus commune du jeu de go.
* Comme dans la version classique du jeu de go, les joueurs noirs sont les premiers à jouer.


## Tests réalisés :
### Tests levant des exceptions (GobanTestSprintsKo)
- boardsize
- placePiece
- isSuicide
- definePlayers
- AIvsAI (simule une partie entre deux IA)

### Tests ne levant pas d'exceptions (GobanTestSprintsOk)
- boardsize
- clear_board
- placePiece
- playCaptureWithoutChains (utiliser la commande play et capturer une pierre qui n'appartient pas à une chaîne)
- playCaptureWithChainsBS4 (utiliser la commande play et capturer des pierres qui appartiennent à une même chaîne, le goban est de taille 4)
- playCaptureWithChainsBS5 (utiliser la commande play et capturer des pierres qui appartiennent à une même chaîne, le goban est de taille 5)
- getNbLibertiesWithoutChains (utiliser la commande getNbLiberties pour une pierre n'appartenant pas à une chaîne)
- getNbLibertiesChainBS5 (utiliser la commande getNbLiberties pour une pierre appartenant à une chaîne, le goban est de taille 5)
- getNbLiberties (test donné par M.Ziane)
- player (définition des joueurs)
- playerWhiteAI
- playerBlackAI

## Bilan du projet :

### Difficultés :
Capture pour IA vs IA presque impossible à cause d'isSuicide qui ne prend pas en compte une possible capture.

### Réussites :
Nous avons réussi à implémenter toutes les fonctions demandées. De plus, nous avons implémenté la méthode isSuicide, pour que l'IA ne joue pas vraiment au hasard et ait un minimum d'intelligence dans ses coups. Le code est documenté et lisible.

### Ce qui peut être améliorable :

Il faut améliorer la capture des pions en changeant la fonction getNbLiberties.
Il aurait pû être intéressant de coder une IA plus intelligente, ce qui n'était pas demandé, mais qui aurait ajouté plus de compétition pour les parties IA vs humain.

### Méthodes utilisées
Après le sprint 1, nous nous sommes tout de suite aperçus qu'il était extrêmement redondant de réécrire les mêmes méthodes à chaque fois qu'on lançait le Main.  
Nous avons choisi de réaliser une multitude de tests, que vous pourrez retrouver dans le dossier /tests. Cela nous a permis de vérifier au fur et à mesure nos erreurs, les comparer aux résultats attendus, utiliser le débogueur, etc. 

## Diagramme d'architecture
! [Diagramme d'architecture du projet](Diagramme_architecture.png)

# Commentaires lors des recettes
## Sprint 1
2 boardsize  
?1  
Doit afficher  
?2 invalid command

Penser à afficher le score de chaque camp  
boardsize 0  
=6  
?6  
Ne pas afficher le =6 et mettre un message d'erreur (invalid number/integer)  
Penser à définir un packetage go et un packetage ihm



## Sprint 2
ajouter la prise et la maj du score