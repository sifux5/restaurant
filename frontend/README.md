# Restorani Reserveerimissüsteem

**Autor:** Alex Kupper  
**Arendusaeg:** 15-20 tundi

## Eeldused enne käivitamist

- [Java 21](https://www.oracle.com/java/technologies/downloads/#java21) peab olema installitud
- [Node.js](https://nodejs.org/) (v18 või uuem) peab olema installitud
- Kontrolli terminalis:
```bash
  java -version   # peaks näitama 21.x.x
  node -version   # peaks näitama v18 või uuem
```

## Käivitamine (2 sammu)

### Samm 1 — Käivita backend
Ava terminal ja käivita:
```bash
cd restaurant
./mvnw spring-boot:run
```
✅ Backend on valmis kui näed: `Started RestaurantApplication in X seconds`

### Samm 2 — Käivita frontend
Ava **uus** terminal ja käivita:
```bash
cd restaurant/frontend
npm install
npm start
```
✅ Brauser avab automaatselt `http://localhost:3000`

---

## Funktsionaalsus

- Saaliplaani visuaal kolme tsooniga: Sisesaal, Terrass, Privaatruum
- Laudade filtreerimine kuupäeva, kellaaja, inimeste arvu ja tsooni järgi
- Eelistused: vaikne nurk, akna all, laste mängunurga lähedal
- Soovitusalgoritm mis arvestab vaba ruumi ja eelistuste sobivust
- Parim soovitus tõstetakse kuldse äärena esile
- Broneeringu loomine modali kaudu koos juhusliku päevaroaga (TheMealDB API)
- Dünaamiline laudade liitmine — kui seltskond on suurem kui ükski laud mahutab, soovitab süsteem kõrvuti asuvaid laudu
- Juhuslikud broneeringud genereeritakse käivitumisel

---

## Keerulisemad kohad

**Java versioon:** Algselt kasutasin Java 25, millega Lombok ei töötanud. Peale CGI kinnitust et Java 21 on sobiv, vahetasin versiooni ja probleem lahenes.

**Lombok annotation processor:** `@Data` annotatsioon ei genereerinud gettereid/settereid Maven kompileerimisel. Lahendasin lisades `pom.xml`-i eraldi `maven-compiler-plugin` konfiguratsiooni koos Lombok annotation processor path'iga.

**CORS:** Frontend (port 3000) ei saanud backendiga (port 8080) suhelda. Lahendasin lisades `application.properties`-se rea `spring.mvc.cors.allowed-origins=http://localhost:3000`.

---

## Abi

Kasutasin arenduse käigus Claude AI abi — Spring Boot ja React põhitõdede õppimisel ning vigade lahendamisel.

## Lahendamata probleemid

Kõik planeeritud funktsionaalsused said teostatud.