

# Music Catalog & Spotify Integration

## Funcționalități

- **Creare cont**: Permite utilizatorilor să creeze un cont nou.
- **Login**: Autentificare pentru utilizatorii existenți.
- **Match-up între piese**: Un mini-joc în stilul "would you rather", care permite utilizatorilor să voteze între două piese muzicale.
- **Vizualizare piese**: Afișează toate piesele disponibile în baza de date.
- **Vizualizare albume**: Permite utilizatorilor să vadă albumele disponibile.
- **Vizualizare playlisturi**: Vizualizează playlisturile utilizatorului.
- **Creare playlist**: Permite utilizatorilor să creeze noi playlisturi.
- **Ștergere playlist**: Permite utilizatorilor să șteargă playlisturi existente.
- **Adăugare piesă în catalog**: Permite utilizatorilor să adauge piese noi în catalogul aplicației prin căutarea și selectarea acestora.
- **Adăugare piesă în playlist**: Adaugă o piesă selectată într-un playlist existent.
- **Vizualizare leaderboard-uri curente**: Afișează clasamentele actuale bazate pe jocul de match-up între piese.
- **Conectare Spotify**: Permite utilizatorilor să se conecteze la contul lor de Spotify și să integreze muzica preferată din Spotify în aplicație.
- **Vizualizare piese Spotify**: Afișează piesele din contul utilizatorului de Spotify, oferind posibilitatea de a explora și gestiona muzica din Spotify direct din aplicație.
- **Match-up între piese Spotify**: Permite utilizatorilor să joace jocul de match-up folosind piesele din contul lor de Spotify, adăugând o dimensiune nouă și captivantă experienței de utilizare.
- **Adăugare piesă inexistentă în DB**: Permite utilizatorilor să adauge piese noi în baza de date.
- **Vizualizare toate piesele din DB**: Afișează toate piesele disponibile în baza de date.

## DB Schema
![Album](https://github.com/Raduc2003/MusicCatalog/assets/72871085/55c55c5b-580b-4fe3-8e64-a0f4eb588605)

## Tehnologii Utilizate

- Java
- JDBC pentru conectarea la baza de date
- MySQL

## Cum să Rulezi Local

### Prerechizite

- Java JDK 
- JDBC MySql driver

### Pași pentru rulare

1. **Clonare repository**: Clonează repository-ul GitHub local.
   ```bash
   git clone https://github.com/Raduc2003/MusicCatalog.git
   cd MusicCatalog
   ```

2. **Setare baza de date**: Asigură-te că ai o instanță MySQL rulând și creează baza de date necesară folosind scripturile SQL furnizate.

3. **Configurare driver JDBC**: Asigură-te că ai driverul JDBC MySQL configurat în proiectul tău Java.

4. **Compilare și rulare aplicație**:
   ```bash
   javac -d bin src/Main/Main.java
   java -cp "bin:mysql-connector-java-8.0.23.jar" Main.Main
   ```

### Utilizare

1. **Creare cont**: Selectează opțiunea 1 din meniu pentru a crea un cont nou.
2. **Login**: Selectează opțiunea 2 pentru autentificare.
3. **Match-up între piese**: Selectează opțiunea 3 pentru a juca jocul de match-up între piese.
4. **Vizualizare piese**: Selectează opțiunea 4 pentru a vizualiza piesele disponibile.
5. **Vizualizare albume**: Selectează opțiunea 5 pentru a vizualiza albumele disponibile.
6. **Conectare Spotify**: Selectează opțiunea 6 pentru a te conecta la Spotify și a integra muzica ta preferată în aplicație.
7. **Vizualizare piese Spotify**: Selectează opțiunea 7 pentru a vizualiza piesele din contul tău de Spotify și a le explora în detaliu.
8. **Vizualizare playlisturi**: Selectează opțiunea 8 pentru a vizualiza playlisturile tale.
9. **Adăugare piesă în playlist**: Selectează opțiunea 9 pentru a adăuga o piesă într-un playlist existent.
10. **Adăugare piesă în catalog**: Selectează opțiunea 10 pentru a adăuga o piesă nouă în catalogul aplicației.
11. **Vizualizare leaderboard-uri**: Selectează opțiunea 11 pentru a vizualiza leaderboard-urile curente.
12. **Adăugare piesă inexistentă în DB**: Selectează opțiunea 12 pentru a adăuga o piesă nouă în baza de date.
13. **Vizualizare toate piesele din DB**: Selectează opțiunea 13 pentru a vizualiza toate piesele din baza de date.
14. **Creare playlist**: Selectează opțiunea 15 pentru a crea un nou playlist.
15. **Ștergere playlist**: Selectează opțiunea 16 pentru a șterge un playlist existent.
16. **Match-up între piese Spotify**: Selectează opțiunea 17 pentru a juca jocul de match-up folosind piesele din Spotify, aducând astfel o experiență muzicală mai personalizată și interactivă.
17. **Ieșire**: Selectează opțiunea 14 pentru a ieși din aplicație.

Aceste modificări ar trebui să reflecte clar noile funcționalități ale aplicației tale în README și să sublinieze integrarea captivantă cu Spotify.
