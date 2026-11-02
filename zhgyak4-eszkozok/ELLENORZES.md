# Az indulócsomag ellenőrzése

- Java: a gépen telepített Temurin 25.0.5.
- `mvnw.cmd -o clean package`: **BUILD SUCCESS**. A projektben nincsenek kész megoldást ellenőrző tesztek.
- Az elkészült alkalmazás elindult egy ideiglenes ellenőrző porton (18097), majd le lett állítva.
- Böngészőnek megfelelő HTML-kérés: `/` → a Spring alapértelmezett `/login` oldala, **HTTP 200**. A saját Vaadin LoginForm bekötése a feladat része.
- A három fordításfájl egyenként 40 kulcsot tartalmaz; a kulcsok egyeznek, nincsenek duplikált kulcsok vagy üres értékek.
- Az alapfeladatok pontszáma összesen 100, a bónusz +10.

Ez az ellenőrzés a kiindulóállapot fordíthatóságára és indulására vonatkozik. A CRUD, jogosultságok és nyelvválasztás szándékosan nincs megoldva.

A ZIP kizárólag az útmutatókat, forrásokat, Maven-konfigurációt és wrappert tartalmazza; buildkimenetet, generált frontendfájlokat és IDE-beállításokat nem.
