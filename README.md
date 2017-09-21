# Jeu de lettres multijoueur WordOff

## Introduction

WordOff est un jeu de lettres réalisé en Java à l'aide de la librairie JavaFX basée sur le jeu pour smartphone [WordOn HD](https://www.wordonhd.com/). Il a été réalisé dans le cadre du cours Projet de Groupe de la troisième année d'informatique logicielle à la Haute-Ecole d'Ingénieurie et de Gestion du canton de Vaud (HEIG-VD).

### Ajout de nouvelles fonctionnalités

Afin d'ajouter de nouvelles fonctionnalités, les étapes suivantes doivent être suivies:
    1. Créez une nouvelle branche appelée feature-[nom de fichier]-[nom de la branche].
    2. Développez dans une nouvelle branche associée à la branche liée à la fonctionnalité (feature).
    3. Examen de code + tests unitaires avant de fusionner la branche de développement dans la branche feature associée.
    4. Une fois la fonctionnalitée terminée, supprimez l'ancienne ou les anciennes branches de développement.
        1. Fusionnez la branche **master** dans la branche **feature**.
        2. Fusionnez la branche **feature** dans la branche **master** après la résolution des conflits et une deuxième série de tests.
    5. Supprimez la branche **feature** une fois fusionnée avec **master**.

La branche **master** devrait représenter l'avancement général du projet. Chaque branche **feature** devrait représenter la progression globale d'une fonctionnalité.

### Relecture de code

Les relectures de code sont encouragées pour vérifier si les commits sont cohérent et pour trouver des bugs. Elles ne sont pas obligatoires et ne devraient pas être nécessaires si le développeur teste son code de manière exhaustive et n'oublie pas de le formater son code.

## Installation

* Compiler le projet nécessite [Apache Maven](https://maven.apache.org/download.cgi) ainsi que la version 1.8 du [Java JDK](http://www.oracle.com/technetwork/pt/java/javase/downloads/jdk8-downloads-2133151.html).
* Le fichier `pom.xml` contient les informations utilisées par Maven pour compiler le projet. La commande `mvn clean install` permet de compiler le projet avec le fichier `pom.xml` du répertoire courrant.
* Le programme exécutable est `N/A`.

## Lancement de l'exécutable

Pour lancer l'exécutable compilé, utiliser la commande `java -jar <nom de l'executable>.jar`

## Auteurs

- Edward Ransome (Chef de groupe)
- Antoine Friant
- Gabriel Luthier
- Christopher Meier
- Daniel Palumbo
- Michela Zucca
