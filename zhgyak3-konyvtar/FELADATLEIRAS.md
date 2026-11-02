# Prog5 gyakorló zárthelyi – Könyvtári nyilvántartás

Javasolt időkeret: 120 perc + 20 perc bónusz. Saját gyakorlófeladat, nem hivatalos ZH.
Alapfeladat: 100 pont. Bónusz: +10 pont.

## Bevezetés

Egészítse ki a mellékelt `zh-konyvtar` projektet az alábbiak alapján!
A projekt Java 25, Spring Boot 4.1.1 és Vaadin 25.3.2 verziókat használ.
A függőségek, a modell, a repository-interfész és a memóriabeli repository készen vannak.
A `DbBookRepository` szándékosan nem működik; ezt nem kell implementálni.
A feladat megoldásához szükséges összes felületi szöveget tartalmazza a
`src/main/resources/vaadin-i18n/translations.properties`; ezeket használja a saját feliratokhoz
és validációs üzenetekhez. A login komponens lokalizálásához is rendelkezésre állnak a kulcsok.
A beépített keretrendszer által generált technikai hibaoldalakat nem kell lefordítania.

A dependency injection során interfészekre hivatkozzon, ahol rendelkezésre állnak.
A szolgáltatásokat a Spring kezelje. Nem elegendő csak elrejteni a tiltott műveletek gombját.
A felület pontos megjelenése szabadon választható; az oszlopsorrend és a működés kötött.
A befejezett megoldásnak `clean package` paranccsal fordulnia és elindulnia kell.
A memóriabeli adatok újraindításkor visszaállnak.

## Indulóállapot

- Kész `Book` modell: id, title, author, publicationYear, category, loanable.
- Kész `BookCategory` enum és öt kezdő rekord.
- Két repository bean: `mock` és `db`.
- `BookService` és `SecurityService` interfészek, service-implementáció nélkül.
- Félkész SecurityConfig: a felhasználók létrehozása adott, a bekötés hiányos.
- Üres nézetek és AppLayout-alapú MainLayout.
- A `/` címen ideiglenes StarterView mutatja, hogy az alap elindult.
  A lista root aliasának elkészítése előtt távolítsa el a StarterView `@Route` annotációját,
  vagy törölje ezt az ideiglenes osztályt, különben útvonalütközés lesz.
- Az induló security még a Spring alapértelmezett működését használja.

## 1. Konfiguráció – 5 pont

Az alkalmazás alapértelmezetten a **8095**, `dev` Spring profillal a **8096** portot használja.
Ellenőrizze mindkét indítási módot!

## 2. Bejelentkezés és felhasználók – 10 pont

Készítse el a `/login` nézetet Vaadin LoginForm használatával!
Az oldal bejelentkezés nélkül is elérhető legyen. Hibás belépéskor jelenjen meg a komponens hibajelzése.
A belépőoldal ne használja a bejelentkezett felhasználóknak szánt MainLayout fejlécet.
A LoginForm címét, mezőfeliratait, belépésgombját és hibaüzenetét a megadott fordításokból állítsa be;
a jelszó-visszaállító lehetőséget rejtse el.
Egészítse ki a security-konfigurációt, hogy az alábbi felhasználók beléphessenek!
Minden jelszó: `password`.

| Felhasználó | Szerepkörök |
|---|---|
| admin | ADMIN, MANAGE, DELETE, FICTION, SCIENCE, HISTORY |
| librarian | MANAGE, FICTION, SCIENCE, HISTORY |
| reader | FICTION |
| researcher | SCIENCE, HISTORY |

A szerepkörkiosztást ne változtassa meg!
A lista, az űrlap és a közös layout bejelentkezést igényeljen.

## 3. Service és repository – 10 pont

Implementálja a BookService interfészt!
A `listAll`, `findById`, `save`, `deleteById` metódusok a megfelelő repository-metódust hívják.
A `listCategories` a BookCategory enum értékeit adja vissza dinamikusan.
Konstruktoros injektálással, a BookRepository interfészen és a bean nevén keresztül válassza a `mock` implementációt!
A service importjai között nem szerepelhet az InMemoryBookRepository.
A SecurityService legyen injektálható Spring bean is.

## 4. Közös fejléc és oldalcímek – 10 pont

Implementálja az AppLayout-alapú MainLayout osztályt!
A fejléc tartalmazza a bejelentkezett felhasználó nevét, a listára mutató navigációt,
az új könyv felvitelére mutató navigációt és a kijelentkezés gombját.
Az új könyv menüpontot csak MANAGE szerepkörrel jelenítse meg!
A login, lista, új könyv és szerkesztés böngészőfülcíme is legyen megfelelően fordított.
Az űrlap címe tükrözze, hogy új rekord vagy szerkesztés van folyamatban.

## 5. Könyvek listázása – 15 pont

Készítse el a BookListView nézetet `/books` útvonalon, MainLayout-ba foglalva!
A `/` cím is ide vezessen. Használjon Grid<Book> komponenst, kézzel megadott oszlopokkal!

Az oszlopok sorrendje:

| Azonosító | Cím | Szerző | Kiadási év | Kategória | Kölcsönözhető | Műveletek |
|---|---|---|---|---|---|---|
| 1 | A Pál utcai fiúk | Molnár Ferenc | 1907 | Szépirodalom | Igen | Szerkesztés / Törlés |

- Az azonosítóoszlop csak ADMIN szerepkörrel jelenjen meg.
- A kategóriák a fordított nevükkel jelenjenek meg.
- A boolean érték helyén fordított Igen/Nem szerepeljen.
- A műveletoszlopban soronként legyenek a jogosultság szerint megengedett gombok.
- Ha nincs megengedett művelet, a műveletoszlop lehet üres vagy elhagyható.
- Üres listához használja a `list.empty` üzenetet.

A bónusz nélkül minden bejelentkezett felhasználó minden rekordot láthat.

## 6. Új könyv és szerkesztés közös űrlappal – 25 pont

Egyetlen BookFormView osztály szolgálja ki mindkét útvonalat:

- `/books/new`: új, még azonosító nélküli könyv.
- `/books/:id/edit`: az azonosítóhoz tartozó meglévő könyv szerkesztése.

Mindkét útvonal használja a MainLayout-ot. A lista Szerkesztés gombja a megfelelő rekord szerkesztőcímére vigyen.
Az útvonalparamétert navigációkor dolgozza fel, és a megfelelő modellt töltse be az űrlapra.

Mezők:

| Mező | Komponens | Követelmény |
|---|---|---|
| Cím | TextField | Kötelező, csak szóköz nem fogadható el |
| Szerző | TextField | Kötelező, csak szóköz nem fogadható el |
| Kiadási év | IntegerField | Kötelező, 1450–2026, a határok is elfogadhatók |
| Kategória | ComboBox<BookCategory> | Kötelező; service-ből kapott enumlista, fordított címkék |
| Kölcsönözhető | Checkbox | Szabadon választható; a false is érvényes |

A modell és a mezők között Binderrel teremtsen kapcsolatot!
Az azonosító nem szerkeszthető mező.
A Mentés gomb csak érvényes űrlapot mentsen, majd navigáljon a listára.
Szerkesztéskor őrizze meg az azonosítót: ne keletkezzen új rekord!
A Mégsem gomb mentés nélkül vigyen vissza a listára. A begépelt módosítások ilyenkor ne maradjanak meg.
Nem létező számszerű azonosítónál jelenítse meg a `book.notfound` értesítést és térjen vissza a listára.
Nem számszerű azonosító esetén is kezelje a navigációt összeomlás nélkül;
az elutasított útvonal vagy ugyanilyen visszairányítás is elfogadható.

## 7. Törlés megerősítéssel – 10 pont

A lista Törlés gombja ConfirmDialog ablakot nyisson!
A fordított kérdés tartalmazza a kiválasztott könyv címét.
Legyen Törlés és Mégsem lehetőség.
Csak a megerősítés töröljön, utána frissítse a táblázatot.
Az elutasítás és a dialógus bezárása ne módosítsa az adatokat.

## 8. Jogosultságkezelés – 15 pont

Engedélyezze a method security-t!

| Művelet | Követelmény |
|---|---|
| Lista megnyitása, könyv és kategóriák lekérése | Bejelentkezett felhasználó |
| Új könyv és szerkesztő űrlap megnyitása | MANAGE |
| Mentés a service-ben | MANAGE |
| Törlés a service-ben | DELETE |

Az új könyv menüpontot és a szerkesztési gombokat MANAGE, a törlési gombokat DELETE alapján jelenítse meg.
A közvetlen űrlap-URL is legyen védett. A service-metódusok védelme a felülettől függetlenül működjön.
A MANAGE önmagában nem ad törlési jogot, az ADMIN önmagában nem helyettesíti a többi szerepkört.

## 9. Bónusz: kategória szerinti szűrés – +10 pont

Method security segítségével a BookService.listAll eredményében csak azok a könyvek maradjanak,
amelyek kategórianeve szerepel a felhasználó szerepkörei között.
Használja a SecurityService.getRolesAsString metódusát!
Ne a Gridben rejtse el a tiltott rekordokat. Az ADMIN nem kap külön kivételt.

Elvárt induló eredmény:

| Felhasználó | Látható könyvek |
|---|---|
| admin | mind az öt |
| librarian | mind az öt |
| reader | A Pál utcai fiúk; Egri csillagok |
| researcher | Az idő rövid története; Történelmi atlasz; Csillagászati kézikönyv |

A megadott MANAGE felhasználók minden kategóriaszerepkört megkaptak,
így a bónusz nem tesz elérhetetlenné számukra szerkeszthető könyveket.

## Leadás előtti saját ellenőrzés

1. Fordítás: clean package. Belépés hibás, majd helyes jelszóval.
2. Új könyv mentése; a rekordok száma eggyel nő.
3. Ugyanennek szerkesztése; a rekordok száma és azonosítója nem változik.
4. Szerkesztés után Mégsem; a korábbi tárolt adatok maradnak.
5. Üres, szóközös, 1449-es és 2027-es értékek elutasítása; 1450 és 2026 elfogadása.
6. Nem létező szerkesztési azonosító kezelése.
7. Törlés elutasítása, majd megerősítése; a lista helyesen frissül.
8. librarian: új és edit van, törlés nincs. reader: egyik módosítás sincs.
9. reader közvetlen /books/new és /books/1/edit útvonala is tiltott.
10. Bónusznál a négy felhasználó listájának ellenőrzése.
11. Kijelentkezés, majd védett útvonal újbóli megnyitása.
12. Alapértelmezett és dev profil portjának ellenőrzése.
