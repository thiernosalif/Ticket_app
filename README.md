# Ticket_app
ticket management system
lors du lancement de l'application, des donnees seront pre-chargees dans la base de donnees pour faciliter l'utilisation.
Le systeme d'authentification utilise est un systeme basique (il faudra utiliser un username attibut de l'entite User).

le fichier data.sql :

INSERT INTO app_user (username, email) VALUES ( 'FANJA', 'fanja@gmail.com');
INSERT INTO app_user ( username, email) VALUES ( 'TEST1', 'test1@gmail.com');
INSERT INTO app_user ( username, email)  VALUES ( 'test2', 'test2@gmail.com');
INSERT INTO app_user ( username, email)  VALUES ( 'TEST3', 'test3@gmail.com');

INSERT INTO ticket (ID, TITRE, DESCRIPTION, STATUT_TICKET, iduser)
VALUES (2, 'Ticket1', 'The ticket number 1', 'EN_COURS', NULL);

INSERT INTO ticket (ID, TITRE, DESCRIPTION, STATUT_TICKET, iduser)
VALUES (3, 'Ticket3', 'The ticket number 3', 'ANNULE', NULL);

INSERT INTO ticket (ID, TITRE, DESCRIPTION, STATUT_TICKET, iduser)
VALUES (4, 'Ticket4', 'The ticket number 4', 'TERMINE', NULL);

INSERT INTO ticket ("ID", "TITRE", "DESCRIPTION", "STATUT_TICKET", iduser)
VALUES (5, 'Ticket5', 'The ticket number 5', 'TERMINE', NULL);




