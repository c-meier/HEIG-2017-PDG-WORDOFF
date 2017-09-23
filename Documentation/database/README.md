# Base de donnée

## Model conceptuel

![model conceptuel](model_conceptuel.svg)

Crée avec `UMLet`.

## Nomenclature

### User

* Un utilisateur du jeu (compte invité créé automatiquement si nécessaire)
* Un unique utilisateur représentant une "intelligence artificielle"

### Game

* Une partie

### Tournament

* Un tournoi

### Challenge

* Un challenge envoyé à un utilisateur
* Les status possible: `discarded`, `waiting`, `done`

### Tile

* Une tuile représentant une lettre et sa valeur

### Case

* Une case où l'on peut poser une tuile

### Tileset

* Un ensemble de tuiles disponible pour une partie

### Language

* Le langage de la partie.
