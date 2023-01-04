# OpenEvent


## A faire:
- [X] : Finir la modélisation </br>

- [X] : Finir les classe </br>

- [X] : Finir les test </br>

- [X] : Interface

## Cahier des charges

L’application à réaliser gère les événements d’une association et de ses membres.
### Membre : 
un membre d’association est caractérisé par son nom, son prénom, son âge et son </br>
adresse. Il n’existe pas deux membres ayant le même nom et le même prénom. </br>
Dans l’association, un membre joue un rôle particulier, celui de président. </br>
Il y a toujours un et un seul président. </br>
### Événement :
Un événement est défini par un nom, une date et une heure, une durée, un lieu et </br>
un nombre maximum de personnes y participant. Deux événements ne peuvent pas avoir lieu </br>
en même temps dans le même lieu. </br>
### Inscription :
 un membre peut s’inscrire à un événement. La contrainte est que le membre ne </br>
doit pas être déjà inscrit à un événement qui se chevauche dans le temps avec le nouvel </br>
événement auquel il veut s’inscrire. On ne peut également s’inscrire à un événement que s’il </br>
n’a pas déjà eu lieu et que le nombre maximal de participants n’est pas atteint. Un membre </br>
peut ensuite se désinscrire d’un événement. </br>
### Suivi :
 on doit pouvoir visualiser l’ensemble des membres de l’association, l’ensemble des </br>
événements (tous ou ceux à venir), l’ensemble des inscriptions pour un événement donné </br>
(avec le nombre d’inscrits) et pour chaque membre, pouvoir lister les événements auxquels il </br>
est inscrit (tous ou ceux à venir). L’application doit permettre de créer et supprimer de </br>
nouveaux membres ou événements. </br>
### Mise en œuvre :
Pour faciliter la maintenance de l’application, on a décidé de faire une </br>
application Java. L’ensemble des données est stocké dans des fichiers </br>
