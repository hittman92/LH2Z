Voraussetzungen:
- Java 17 oder höher
- MySQL Server (lokal)
- Eclipse IDE


Initialisierung der Anwendung:

### 1. Datenbank einrichten
MySQL-Server lokal starten und folgende Befehle ausführen:

```sql
CREATE DATABASE lh2z;
CREATE USER 'lh2z'@'localhost' IDENTIFIED BY 'lh2z';
GRANT ALL PRIVILEGES ON lh2z.* TO 'lh2z'@'localhost';
FLUSH PRIVILEGES;

### 2. Das Projekt in Eclipse importieren
---> Import --> Maven --> Existing Maven Projects

### 3. Anwendung starten
--> Rechstklick auf Lh2zApplication.java --> Run As --> Java Application


Verwendung der Webanwendung:
--> URL: http://localhost:8080/
