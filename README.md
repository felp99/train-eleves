# Railway System Simulation

## Overview
This Java project simulates a basic railway system. It models the movement of trains on railway tracks, including stations and track sections. The system demonstrates object-oriented principles and thread synchronization in Java.

## System Components

### Classes

- `Element` (Abstract Class)
  - Generic representation of an element in the railway circuit (either `Station` or `Section`).
- `Station`
  - Represents a station in the railway network with a certain capacity (number of platforms).
- `Section`
  - Represents a section of the railway track.
- `Railway`
  - Represents the entire railway circuit, composed of stations and track sections.
- `Position`
  - Represents the position of a train within the railway circuit, consisting of an `Element` and a `Direction`.
- `Train`
  - Represents a train, characterized by its name and its position in the railway circuit. Implements the `Runnable` interface for concurrent movement simulation.
- `Direction` (Enum)
  - Represents the direction in which a train can travel (from left to right or from right to left).

### Exceptions

- `BadPositionForTrainException`
  - Custom exception thrown when a train is assigned an invalid position.

## Usage

1. **Setup Railway Network:**
   - Instantiate `Station` and `Section` objects.
   - Create a `Railway` instance by passing an array of `Element` (stations and sections).
  
2. **Initialize Trains:**
   - Instantiate `Train` objects with a name, associated `Railway`, and initial `Position`.
   - Ensure the initial position is a station (`Station` instance), otherwise `BadPositionForTrainException` will be thrown.

3. **Start Simulation:**
   - For each `Train` object, create a `Thread` and start it.
   - Trains will move based on their current position and direction, using the `moveLeftToRight` and `moveRightToLeft` methods in `Railway`.

4. **Monitor and Control:**
   - The movement of trains can be monitored and logged using the `toString` methods in the `Train` and `Position` classes.
   - The simulation runs for a predefined number of iterations or until manually stopped.

# TP11-15 – Trains et circuits (Informations générales)

LOBATO Felipe, SANCHEZ Jann

## Exercice 1 (Le comportement d'un train)

### 1.1. Dans le diagramme de classes précédent, quel sera le rôle de chaque classe dans la réalisation du déplacement d'un train ?

**La classe Position:** Cette classe représente la position d'un train sur le réseau ferroviaire. Elle contient une référence à un élément (qui pourrait être une section de voie ou une station) et une direction (qui pourrait être vers la gauche ou vers la droite). La classe Position est essentielle pour le déplacement du train, car elle détermine où se trouve le train et dans quelle direction il se déplace.

**La Classe Train:** La classe Train représente un train. Elle contient un nom et une Position, indiquant où le train se situe sur le réseau ferroviaire. Pour déplacer le train, on mettrait à jour sa Position dans le réseau.

**La Classe Direction:** C'est une classe énumération qui définit les directions possibles du déplacement du train (gauche vers droite ou droite vers gauche).

**La Classe Railway:** Sur cette classe on peut garder tous les éléments qui composent le railway, ça veut dire qu' il y a tous les éléments comme section et Station. Il est important de dire que l' objet railway ne peut pas être modifié une fois qu'il a été créé. L'objet Railway serait responsable de la gestion globale des déplacements du train à travers le réseau, en connaissant tous les éléments qui le composent.

**Classe Element :** C'est une classe abstraite qui sert de base pour tous les éléments composant le chemin de fer (les sections de voie et les stations, par exemple). Chaque élément a un nom et est associé à un Railway. Les méthodes de cette classe permettent d'associer l'élément au Railway et de représenter l'élément sous forme de chaîne de caractères (toString).

**Classe Section :** Héritant de la classe Element, la classe Section représente une section de la voie ferrée. Elle pourrait être utilisée pour définir les propriétés spécifiques des sections de voie, comme leur longueur ou leur état (par exemple, occupé ou libre).

**Classe Station :** Également un descendant de la classe Element, cette classe représente une gare ou une station dans le réseau ferroviaire. Elle a une taille (qui pourrait être interprétée comme le nombre de voies ou la capacité de la station) et serait responsable de la gestion des trains à l'arrivée et au départ.

### 1.2. Modifiez le diagramme de classes initial en ajoutant les méthodes et/ou attributs nécessaires à la réalisation du déplacement d'un train.

<img width="452" alt="image" src="https://github.com/felp99/train-eleves/assets/76445505/02338ca3-1dea-49d9-a87f-e7989341d6cf">

### 1.3. Donnez le code des méthodes identifiées. Pour valider le bon fonctionnement de vos méthodes, vous pouvez afficher l'état du train chaque fois qu'il change de position.

La fonction _moveToNextPosition_ (V1) est responsable du déplacement d'un train vers la position suivante. Chaque fois qu'il rencontre une gare, il s'arrête, change de direction et revient, en refaisant le chemin inverse.

<img width="452" alt="image" src="https://github.com/felp99/train-eleves/assets/76445505/66335b18-f09b-479f-899d-9ad3926e2bf7">

 <img width="452" alt="image" src="https://github.com/felp99/train-eleves/assets/76445505/9dd22a98-aac1-46d6-8014-9f00fea6abb3">

<img width="452" alt="image" src="https://github.com/felp99/train-eleves/assets/76445505/a2aeb76f-021c-428f-aa4f-0ac636a704e0">

Dans ce cas, la fonction _getElementIndexByPosition_ est chargée de renvoyer l'indice de la position par rapport à toutes les sections de la gare.

<img width="452" alt="image" src="https://github.com/felp99/train-eleves/assets/76445505/f5be5d59-7727-4cd5-85cd-6d2160f02347">

## Exercice 2 (Plusieurs trains sur la ligne)

### 2.1. Modifiez votre programme pour qu'il puisse y avoir plusieurs trains actifs (en déplacement) sur la ligne.

<img width="452" alt="image" src="https://github.com/felp99/train-eleves/assets/76445505/d57a3e58-1690-41fc-bed6-7701535df7f2">

### 2.2. Identifiez les variables qui permettent d'exprimer l'invariant de sûreté pour la ligne de trains.

<img width="410" alt="image" src="https://github.com/felp99/train-eleves/assets/76445505/b6d48322-694e-459b-94f0-e3eb799f6140">

Invariant de Sécurité :

- Le nombre de trains qui se déplacent de gauche à droite (countTrainsLR) et de droite à gauche (countTrainsRL) ne peut pas être négatif.
- Le array withTrain doit avoir la même longueur que le tableau elements.
- Pour chaque position sur la ligne (éléments), la valeur correspondante dans withTrain doit être true si et seulement s'il y a un train dans cette position.
- La somme des countTrainsLR et countTrainsRL ne doit jamais dépasser la longueur de la ligne de train.

### 2.3. À l'aide des variables identifiées, exprimez l'invariant de sûreté.

<img width="452" alt="image" src="https://github.com/felp99/train-eleves/assets/76445505/9bcbe4a0-4c89-4627-a3fd-4ab5fc6964a1">

<img width="452" alt="image" src="https://github.com/felp99/train-eleves/assets/76445505/c8287c4e-8047-47e9-b2f0-1ed0d093ff51">

### 2.4. Quelles sont les actions « critiques » que peut effectuer un train ?

- Mouvement du train de gauche à droite (moveLeftToRight) et de droite à gauche (moveRightToLeft):

Ces méthodes gèrent le déplacement des trains le long de la ligne ferroviaire.

Actions critiques : Modification de l'état des éléments de la ligne ferroviaire (occupation des sections par les trains). Prioriser que le train (thread) utilise une ressource d'abord quand il est disponible pour plusieurs, dans ce cas nous avons priorisé les trains qui partent de gauche à droite de la Gare A.

- Incrémentation et décrémentation du nombre de trains en mouvement (incrementCountLR, incrementCountRL, decrementCountLR, decrementCountRL):

Ces méthodes mettent à jour le nombre de trains en mouvement dans chaque direction.

Actions critiques : Modification du nombre de trains en mouvement, qui peut affecter l'accès concurrentiel à la ligne ferroviaire.

- Contrôle de l'état de la ligne (controlState):

Cette méthode gère l'état global de la ligne ferroviaire en fonction de la position actuelle d'un train.

Actions critiques : Vérification et mise à jour de l'occupation des sections par les trains, ainsi que la gestion des priorités et des mouvements.

### 2.5. Dans quelles classes ces actions doivent être ajoutées ?

Les actions critiques doivent être ajoutées dans la classe Railway. C'est dans cette classe que les opérations liées aux mouvements des trains et à la gestion de l'état global de la ligne ferroviaire sont définies.

Les méthodes telles que moveLeftToRight, moveRightToLeft, incrementCountLR, incrementCountRL, decrementCountLR, decrementCountRL, et controlState sont des exemples d'actions critiques qui appartiennent à la classe Railway.

### 2.6. Selon la méthode de construction d'une solution de synchronisation donnée plus haut, quelles autres méthodes faut-il ajouter et dans quelle classe ?

Dans ce cas de la classe train, la méthode run est l'entrée principale lorsque le train est exécuté comme un fil. Cette méthode appelle moveToNextPosition, où se trouvent la logique de mouvement et l'interaction avec la classe Railway. Nous avons veillé à ce que les opérations critiques au sein de moveToNextPosition soient synchronisées en cas d'interaction simultanée entre plusieurs trains.

Le mot-clé synchronized pour garantir l'accès atomique aux sections critiques du code. Cela aide à éviter les problèmes de concurrence lors de l'exécution simultanée de plusieurs threads (représentant les trains) qui interagissent avec la ligne ferroviaire.

## Observations

## Sources d'inspiration

Comme sources d'inspiration et d'aide pour créer les modèles et répondre aux exercices, nous avons utilisé ChatGPT - pour résoudre certains bugs et faire du brainstorming en général - et les mini-projets utilisés en classe, en particulier ceux qui portaient des ressources et des états similaires à l'idée de trains, de gares et de tronçons.

## Difficultés rencontrées

En général, au cours du développement du projet, nous avons rencontré deux difficultés majeures : la compréhension du problème, étant donné qu'à chaque exercice nous avons identifié de nouvelles spécificités et que leur traitement a rendu le code plus compliqué, et les problèmes techniques en général (le code est devenu plus difficile et la simplification n'a pas été possible).

Plus techniquement, en termes de ressources et de synchronisation, nous avions une certaine difficulté à traiter les points critiques (comme deux trains partant de la même gare, ou deux trains partant en même temps de deux gares, mais pointés l'un vers l'autre). Il a donc fallu utiliser des variables de priorité pour traiter ces cas particuliers qui bloquaient le fonctionnement des las Threads.

Malheureusement, ces deux obstacles ont été suffisants pour nous empêcher de compléter la partie 3 de manière cohérente, et nous avons donc choisi d'établir la possibilité d'exécuter les codes de la partie 1 et de la partie 2 dans la fonction principale, en fonction des commentaires.
