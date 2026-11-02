# Prog5 próba-ZH 4 – Egyetemi eszköznyilvántartás

Saját gyakorlófeladat, nem hivatalos ZH. Időkeret: **140 perc**, bónuszra további 20 perc.
**Alapfeladat: 100 pont. Bónusz: +10 pont.**

Egészítse ki a mellékelt `zh-eszkozok` Vaadin-projektet! Az alkalmazás egyetemi laptopokat, projektorokat és táblagépeket tart nyilván. Java 25, Spring Boot 4.1.1 és Vaadin 25.3.2, a korábbi gyakorlóprojekt függőségeivel.

## Mi van készen?

- `Equipment`: id, name, room, purchaseYear, type, available; konstruktorok, getterek, setterek, másoló konstruktor.
- `EquipmentType`: LAPTOP, PROJECTOR, TABLET.
- `EquipmentRepository`, valamint két Spring bean: `mock` és `db`. A `mock` kész; a `db` szándékosan kivételt dob, nem kell megírni.
- Öt mintaadat, automatikus azonosítóval. Az olvasás másolatot ad vissza; a tárolt adatot csak a `save` módosítja. Új rekordnál a kiosztott azonosító a `save` visszatérési értékében szerepel.
- `EquipmentService` interfész implementáció nélkül. A `SecurityService` szerepkörlekérő segédmetódusa kész, de még nincs belőle Spring bean.
- A felhasználók adatai a `SecurityConfig` osztályban készen vannak, de a metódus nincs beanként regisztrálva; a Vaadin security filter chain és a method security hiányzik.
- Üres nézetek és layout, TODO megjegyzésekkel. Nincs kész CRUD vagy nyelvválasztó.
- Három teljes, azonos kulcsú UTF-8 fordításfájl: `translations.properties` (magyar alap), `translations_hu.properties`, `translations_en.properties`.
- Ideiglenes `StarterView` a `/` címen. A lista root aliasának elkészítése előtt törölje ezt vagy a `@Route` annotációját!

Ahol van interfész, azon keresztül injektáljon, konstruktorral. A szolgáltatásokat a Spring kezelje. Az újraindítás visszaállítja a mintaadatokat. Az összes saját feliratot, validációs üzenetet és oldalcímet fordításból vegye; a keretrendszer technikai hibaoldalait nem kell lefordítani. Az elrendezés szabad, az oszlopok sorrendje és a működés kötött.

## 1. Konfiguráció – 5 pont

Állítsa be, hogy az alkalmazás alapértelmezetten **8097**, `dev` Spring profillal **8098** porton induljon! Ellenőrizze mindkét indítást. A `dev` itt Spring profil, nem Maven profil.

## 2. Bejelentkezés – 10 pont

Készítse el a `/login` nézetet Vaadin `LoginForm` komponenssel! Anonim felhasználó is megnyithassa; ne használja a bejelentkezetteknek szánt MainLayout-ot. A LoginForm a Spring Security bejelentkezési végpontjára küldje az adatokat. Hibás belépés után jelezzen hibát, és ne jelenítse meg a jelszó-visszaállítást.

Állítsa össze a Vaadin security-konfigurációt, és regisztrálja a kapott felhasználókat! Minden jelszó: `password`.

| Felhasználó | Szerepkörök |
|---|---|
| admin | ADMIN, MANAGE, DELETE, LAPTOP, PROJECTOR, TABLET |
| technician | MANAGE, LAPTOP, PROJECTOR, TABLET |
| student | LAPTOP, TABLET |
| lecturer | PROJECTOR |

A kiosztást ne módosítsa! A lista és a közös layout bejelentkezést igényeljen. A login címét, mezőit, gombját és hibaszövegét is a fordítások adják, `LoginI18n` használatával.

## 3. Service és repository – 10 pont

Implementálja az `EquipmentService` interfészt injektálható Spring szolgáltatásként! A `listAll`, `findById`, `save`, `deleteById` delegáljon a megfelelő repository-metódusra. A `listTypes` dinamikusan az enum értékeit adja vissza.

A repositoryt az interfészen keresztül, konstruktorral és a `mock` bean kiválasztásával injektálja. Az implementáció ne importálja az `InMemoryEquipmentRepository` osztályt! Tegye a `SecurityService`-t is injektálhatóvá, `securityService` bean néven.

## 4. Közös fejléc – 5 pont

Az AppLayout-alapú MainLayout fejlécében jelenjen meg:

- a bejelentkezett felhasználó neve;
- navigáció az eszközlistára;
- új eszköz felvitele, csak MANAGE szerepkörrel;
- működő kijelentkezés;
- a 9. feladatban elkészítendő magyar/angol nyelvválasztó.

## 5. Eszközlista – 15 pont

Készítse el az `EquipmentListView` nézetet `/equipment` útvonalon, MainLayout-tal! A `/` is erre a nézetre vezessen. Használjon `Grid<Equipment>` komponenst, automatikus oszlopgenerálás nélkül!

| Oszlopsorrend | Tartalom |
|---|---|
| 1. Azonosító | Csak ADMIN szerepkörrel látható |
| 2. Megnevezés | name |
| 3. Terem | room |
| 4. Beszerzési év | purchaseYear |
| 5. Típus | Fordított enumérték |
| 6. Elérhető | Fordított Igen/Nem, illetve Yes/No |
| 7. Műveletek | Szerepkör szerint Szerkesztés és/vagy Törlés |

Műveleti jogosultság nélkül az utolsó oszlop lehet üres vagy elhagyható. Üres listánál a `list.empty` szöveg jelenjen meg. A bónusz nélkül minden bejelentkezett felhasználó mind az öt rekordot látja.

## 6. Közös új/szerkesztő űrlap – 20 pont

Egyetlen `EquipmentFormView` osztály szolgálja ki a következő címeket MainLayout-tal:

- `/equipment/new`: új eszköz, kezdetben null azonosítóval;
- `/equipment/:id/edit`: meglévő eszköz szerkesztése.

A paramétert navigációkor dolgozza fel, majd töltse be a megfelelő modellt!

| Mező | Vaadin komponens | Validáció |
|---|---|---|
| Megnevezés | TextField | Kötelező, csak szóköz nem fogadható el |
| Terem | TextField | Kötelező, csak szóköz nem fogadható el |
| Beszerzési év | IntegerField | Kötelező, 2000–2026, mindkét határ elfogadható |
| Típus | ComboBox<EquipmentType> | Kötelező, elemek a service-ből, fordított címkék |
| Elérhető | Checkbox | A true és a false is érvényes |

Használjon `Binder<Equipment>`-et! Az id nem szerkeszthető mező. A Mentés csak érvényes űrlapot tároljon, majd navigáljon a listára. Szerkesztéskor ugyanaz az id maradjon, a rekordok száma ne nőjön.

A Mégsem mentés nélkül vigyen a listára, és a beírt változtatások ne módosítsák a repository tartalmát! Nem létező id esetén fordított `equipment.notfound` értesítés után térjen vissza a listára. Nem számszerű és Long tartományon kívüli id se okozzon kezeletlen kivételt; az útvonal elutasítása vagy az előbbi visszairányítás is elfogadható.

## 7. Törlés megerősítéssel – 10 pont

A sor Törlés gombja `ConfirmDialog`-ot nyisson! A fordított kérdés tartalmazza az eszköz megnevezését (`delete.message`, paraméter: a név). A megerősítő és a megszakító gomb is legyen fordított. Csak a megerősítés töröljön, utána frissüljön a Grid. A Mégsem és az ablak bezárása ne módosítson adatot.

## 8. Jogosultságkezelés – 10 pont

Engedélyezze a method security-t, és valósítsa meg a következő szabályokat:

| Művelet | Követelmény |
|---|---|
| Lista, egy eszköz és típusok lekérése a service-ben | Bejelentkezett felhasználó |
| Új/szerkesztő nézet közvetlen megnyitása | MANAGE |
| Mentés a service-ben | MANAGE |
| Törlés a service-ben | DELETE |

A szerkesztési gombok és az új menüpont csak MANAGE, a törlési gombok csak DELETE esetén látszódjanak. A gombok elrejtése önmagában nem elég: a közvetlen URL és a service-metódus is legyen védett! A MANAGE nem ad törlési jogot, az ADMIN nem helyettesít automatikusan más szerepkört.

## 9. Magyar/angol nyelvválasztás az órai minta szerint – 15 pont

Készítsen a MainLayout fejlécébe `ComboBox<Locale>` nyelvválasztót!

- Pontosan két választás legyen: `Locale.of("hu")` és `Locale.of("en")`.
- A mező felirata a `language.label` fordításból származzon. A nyelvek neve az aktuális felület nyelvén jelenjen meg: magyar/angol, illetve Hungarian/English.
- A kiválasztott érték az aktuális UI nyelvét tükrözze. Ha az induló locale országkódot is tartalmaz, például `hu_HU` vagy `en_US`, normalizálja a két támogatott érték egyikére. Más nyelvnél a magyar legyen az alap.
- Felhasználói váltáskor állítsa be a `VaadinSession` locale-ját, majd töltse újra az oldalt az órai megoldás szerint. A kiválasztó inicializálása ne indítson végtelen újratöltést. Az üres választást tiltsa vagy kezelje biztonságosan.
- A választás ugyanabban a Vaadin-munkamenetben navigáció és kézi oldalfrissítés után is maradjon meg. Új munkamenetben megengedett az alapértelmezett nyelv.
- Váltás után a fejléc, a Grid oszlopai, a típusnevek, a boolean értékek, a gombok, az űrlap címkéi, a validációs üzenetek, a törlési dialógus és a böngészőfülcímek is a választott nyelven jelenjenek meg.
- A lista, az új és a szerkesztő űrlap, valamint a login böngészőfülcíme fordításból származzon (`HasDynamicTitle`). Az űrlap címe új/szerkesztés szerint különbözzön.
- A login fordításait az aktuális locale alapján állítsa össze. A loginon külön nyelvválasztó **nem kötelező**. A login angol működése a böngésző angol nyelvi beállításával vagy tesztben angol UI locale-lal is ellenőrizhető; a kijelentkezés utáni nyelvmegőrzés nem követelmény, mert a munkamenet megszűnhet.

Az eszközök saját adatait (például Dell Latitude 5440, A101) nem kell lefordítani. Újratöltéskor a nem mentett űrlapadatok elveszhetnek; megőrzésük nem feladat.

Órai támpont: `ext/gyak6/crud-mvc-vaadin/src/main/java/hu/mik/pte/prog5/crud_mvc/views/layout/MainLayout.java`. A fordításokat a `getTranslation` segítségével használja. Nem kell saját fordításkezelőt írni.

## 10. Bónusz: típus szerinti service-szűrés – +10 pont

Method security segítségével szűrje a `listAll` visszatérési értékét: csak az az eszköz maradjon benne, amelynek típusneve szerepel a felhasználó szerepkörei között! Használja a `SecurityService.getRolesAsString()` metódust. Ne a Gridben szűrjön! Az ADMIN nem kap külön kivételt.

| Felhasználó | Elvárt induló lista |
|---|---|
| admin | Mind az öt eszköz |
| technician | Mind az öt eszköz |
| student | Dell Latitude 5440; Samsung Galaxy Tab S9; Lenovo ThinkPad T14 |
| lecturer | Epson EB-X49; BenQ MW560 |

A MANAGE felhasználók minden típusszerepkört megkaptak. Ez a bónusz csak a lista eredményének szűrése, további rekordszintű szabályok megírása nem feladat.

## Saját ellenőrzőlista

1. `clean package` sikeres Java 25-tel, az alkalmazás elindul.
2. Alapértelmezett port 8097, dev port 8098.
3. Hibás, majd helyes bejelentkezés; működő kijelentkezés.
4. admin: id, új, szerkesztés és törlés látszik. technician: id és törlés nincs, új és szerkesztés van.
5. student és lecturer: nincs módosító gomb, a közvetlen `/equipment/new` és `/equipment/1/edit` is tiltott.
6. Új eszköz mentése eggyel növeli a darabszámot. Szerkesztés megtartja az id-t és a darabszámot.
7. Szerkesztés, átírás, Mégsem: a korábbi adat marad meg.
8. Üres és szóközös megnevezés/terem, hiányzó típus, üres év, 1999 és 2027 elutasítva. 2000 és 2026 elfogadva. Az Elérhető=false menthető.
9. `/equipment/99999/edit`, `/equipment/abc/edit` és túl nagy szám kezelve.
10. Törlés elutasítása nem változtat, megerősítése töröl és frissít.
11. HU → EN: Equipment, Room, Purchase year, Projector, Tablet, Yes/No és angol műveleti gombok jelennek meg. A fülcím is angol.
12. Angolul nyissa meg az új és a szerkesztő űrlapot, váltson ki validációs hibát, nyisson törlési dialógust. Ezek szövege is angol.
13. Kézi F5 és navigáció után megmarad az angol; EN → HU után minden magyar. A váltás nem okoz újratöltési ciklust vagy null hibát.
14. Bónusznál ellenőrizze a fenti négy listaeredményt.

### Pontozás összesítése

| Feladat | Pont |
|---|---:|
| Konfiguráció | 5 |
| Bejelentkezés | 10 |
| Service | 10 |
| Fejléc | 5 |
| Lista | 15 |
| Űrlap | 20 |
| Törlés | 10 |
| Jogosultságok | 10 |
| Nyelvválasztás és fordítások | 15 |
| **Összesen** | **100** |
| Bónusz | +10 |
