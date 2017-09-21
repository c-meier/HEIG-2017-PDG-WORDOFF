# Règles du jeu

## Caractéristiques

* Taille maximum des mots : 7 lettres
* Case W : envoi la lettre contenu dans la case à l’adversaire (max 2 cases)
* Case 2+L : lettre compte double
* Case 3+L : lettre compte triple
* Double word Score (2 cases jaunes)
    * 0, 1 ou 2 lettres à dispositions.
    * Lettre non utilisée = malus à hauteur de la valeur de la lettre
    * Toutes utilisées = mot compte double
* Swap (mélanger) : change l’ordre des lettres du joueur, pouvoir gratuit.
* Discard (jeter) :
    * Donner 2 lettres au hasard à l’adversaire et passer son tour
        * Si des lettres jaunes sont disponible = malus
    * Echanger toutes ses lettres et passe le tour pour 1 étoile
        * Si des lettres jaunes ?? à vérifier
* Peek (apercu) : permet de voir le jeu de l’adversaire pour 1 étoile
* Hint (indice) : permet de placer une lettre du meilleur mot possible sur la grille. Pour une étoile. 10 max par partie / mot ? à vérifier
* Chaque lettre est associée à une valeur
    * Pour le français (102 jetons)
        * 0 point -> Joker x2
        * 1 point -> E x15, A x9, I x8, N x6, O ×6, R ×6, S ×6, T ×6, U ×6, L ×5
        * 2 points : D ×3, M ×3, G ×2
        * 3 points : B ×2, C ×2, P ×2
        * 4 points : F ×2, H ×2, V ×2
        * 8 points : J ×1, Q ×1
        * 10 points : K ×1, W ×1, X ×1, Y ×1, Z ×1

## Mode

* Player Vs Player
    * Adversaire aléatoire
    * Adversaire de la liste d’amis
* Tournoi
    * De 1 à 20 joueurs par tournoi
    * Dure 5 jours (séparée en 5 journée de 24 heures)
    * Contre une IA (pour l'échange de lettres, les malus, etc)
    * La victoire du tournoi est décidée à la fin du 5ème jour
    * Possibilité de rejouer 1x par jour son tour pour 1 étoile.
    * Tournoi avec joueurs aléatoires
        * 1 seul tournoi à la fois
    * Tournoi entre amis
        * Plusieurs tournois possibles, nombre max ? à vérifier
    * Les points obtenus après avoir terminé la partie du jour X sont additionnés au points des jours précédent
    * Obtenir + de points que l'IA offre un bonus de 50 points supplémentaire sur le score du jour X

## Déroulement d’une partie

* Un sac commun par partie (102 lettres)
* Tirage des lettres aléatoires
* Mot de 7 lettres = +10 points
* Taille minimum d’un mot : 2 lettres
* Tour par tour

## Fin de la partie

* Jusqu’à épuisement des lettres
    * Après 2 tours passés consécutifs, la partie prend fin
    * Un joueur termine toutes ses lettres, la partie prend fin
    * Les lettres non utilisées sont un malus au score final suivant leur score de base
* Abandonner une partie
* Statistiques de la partie
    * Progression du score des 2 participants (graphique)
    * Score final des joueurs
    * Meilleur mot (score)
    * Malus de fin (lettres restantes)
    * Journal de la partie

## Autres fonctionnalités

* Liste d’amis
* Liste noire
* Chat
    * A chaque tour, le mot jouer est affiché dans le chat
* Profil
    * Photo du joueur
    * Changement de nom
    * Statistiques
        * Derniers score du jeu
        * Parties gagnées / jouées et en pourcent
        * Niveau de difficulté
        * Score moyen par mot
        * Meilleur score
        * Meilleur mot
        * Dernière activité
        * Résultats des confrontations pour profil adversaire
* Messagerie
    * Gestion des demandes d’amis
    * Messages reçus ?? je n’ai  pas trouvé à quoi ça sert
* Code bonus pour les amis
    * Rentrer le code d’un ami : 300 pièces + 10 étoiles
    * Un ami rentre votre code : 150 pièces + 10 étoiles
