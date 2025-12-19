Principe de base de l'attaque CSRF: 

CLIENT mondomain.com
    -> log
    -> cookie inscrit dans le navigateur client et dispo sur le domain mondomain.com 
    -> client éxécute une autre action ( par ex accès à une ressource protégée ) avec le lien suivant /jira en GET 
    -> client éxécute une autre action ( par ex accès à une ressource protégée ) avec le lien suivant /post/image en POST
    -> Pour être que sur l'utilisateur a le droit de récupérer cette info, ses identifiants peuvent être passés automatiquement en paramètre



PIRATE envoie un mail au client avec un lien vers un site frauduleux
- Le site frauduleux ressemble en tous points au site hébergé sur mondomain.com 
- La victime pense utiliser un formulaire qui se trouve sur mondomain.com, ou alors elle pense cliquer sur un lien en particulier 
- Mais le problème, c'est que l'action qui est faite par l'utilisateur va être redirigée vers mondomain.com depuis le domaine frauduleux. 
- On a donc une requête déclenchée par l'utilisateur parce qu'un pirate l'a trompé, qui provient d'un domaine douteux MAIS qui peut 
utiliser automatiquement les credentials de la victime. 


Aujourd'hui ça n'est plus vraiment possible, la plupart des navigateurs détectent de genre de pattern. 
Bloquent les requêtes avec un allow-origin * + transmission automatique de credentials. 
Les sites webs, mettent quasiment tous en place un token anti csrf. 

Token anti CSRF


- Pour se protéger, lorsqu'on consulte une ressource, le site web crée un token unique, un token CSRF. 
- Si vous voulez accéder à une ressource protégée, vous devez passer au site web ce token. 
- Le token doit être quasiment à usage unique, chaque fois que l'on consomme un TOKEN pour accéder à une ressource protégée, alors le token est consommé et on en génère un autre que l'on renvoit au client. 

Le seul moyen de pirater un token CSRF c'est d'avoir accès au code html / json renvoyé au client. 
Donc de lui demander d'ouvrir sa console, d'analyser le html et de lui copier coller le token, pour pouvoir MAJ
son propre code frauduleux. 




SERVEUR GENERE CSRF TOKEN 
SERVEUR RENVOIE AU CLIENT LE TOKEN GENERE
Le client, s'il veut pouvoir executer une uri/action protégée doit envoyer le csrf TOKEN que le serveur vient de lui envoyer
SERVEUR RECOIT UNE REQUETE, les informations qu'il lui faut ainsi que le CSRF TOKEN. 
SERVEUR COMPARE LE TOKEN ENVOYE AU DERNIER TOKEN GENERE STOCKE EN MEMOIRE 
    -> Si c'est ok alors la ressource / l'action est déclenchée Et on retourne à l'étape 1
    -> Si pas ok, alors la ressource / l'action est inaccessible Et on retourne à l'étape 1

UN CSRF TOKEN EST NORMALEMENT A USAGE UNIQUE

