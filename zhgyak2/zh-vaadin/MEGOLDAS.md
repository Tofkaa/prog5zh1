# Prog5 – a korábbi ZH mintamegoldása

A megoldás az induló projekt Java 25 / Spring Boot 4.1.1 / Vaadin 25.3.2 verzióit tartja meg.
A DBPetRepository szándékosan maradt megvalósítatlan: a feladat a mock nevű repository használatát kéri.
Az adatok memóriában élnek, újraindításkor visszaállnak.

## Indítás

Az IDE-ben a projekt SDK-ja és a Maven futtató JDK-ja is Java 25 legyen.
A ZhVaadinApplication main metódusával, vagy a projekt könyvtárából:

```powershell
.\mvnw.cmd clean package
.\mvnw.cmd spring-boot:run
```

Alapértelmezett cím: http://localhost:8083/login
Dev profil:

```powershell
.\mvnw.cmd spring-boot:run "-Dspring-boot.run.profiles=dev"
```

Dev cím: http://localhost:8084/login
A dev itt Spring profil; nem azonos a Vaadin frontend fejlesztői módjával.

## Melyik feladat hol található?

A Java-fájlok a src/main/java/hu/mik/pte/prog5/zh1vaadin alatt vannak.

| PDF | Megoldás | Mit érdemes megérteni? |
|---|---|---|
| 1 | application.properties, application-dev.properties | A profil felülírja az alapértéket. |
| 2 | config/SecurityConfig, ui/view/LoginView | Beanek, felhasználók, Vaadin login action, error query paraméter. |
| 3 | service/PetServiceImpl | Konstruktoros DI interfészre, @Qualifier("mock"). |
| 4 | ui/MainLayout | AppLayout, navbar, RouterLink, AuthenticationContext. |
| 5 | ui/view/ListView | Kézi Grid-oszlopok, sorrend, ADMIN-függő azonosítóoszlop. |
| 6 | ui/view/RegisterView | Enum ComboBox, Binder, kötelezőség, mentés. |
| 7 | A három view getPageTitle metódusa | HasDynamicTitle és getTranslation. |
| 8.1 | config/SecurityConfig | @EnableMethodSecurity bekapcsolja a PreAuthorize/PostFilter feldolgozását. |
| 8.2 | MainLayout, RegisterView, PetServiceImpl | Menü, útvonal és service külön védelme. |
| 8.3 | PetServiceImpl.listAll | @PostFilter, filterObject, enum.name() és szerepkörök összevetése. |

A felhasználói szövegek a src/main/resources/vaadin-i18n/translations.properties fájlból jönnek,
a login saját feliratai és a kötelezőségi hibaüzenetek is.
A CurrentUserSecurityService a kapott SecurityService interfészt Spring beanként elérhetővé teszi;
azért securityService a bean neve, hogy a SpEL-kifejezésben @securityService alakban hivatkozhassunk rá.

## Jogosultságok kézi ellenőrzése

Minden kapott felhasználó jelszava: password.

| Felhasználó | Látható állatok | Azonosítóoszlop | Regisztráció |
|---|---|---|---|
| admin | mind az öt faj | van | engedélyezett |
| dog | kutyák | nincs | tiltott |
| cat | kutyák | nincs | engedélyezett |
| uncommon | papagájok, teknősök, vadászgörények | nincs | tiltott |

A cat felhasználó a kiinduló projektben REGISTER és DOG szerepkört kapott. Ezt megtartottam.
Az ADMIN önmagában nem kerüli meg a fajszűrést: a kapott admin minden fajszerepkört is megkapott.
A REGISTER szerepkör nem fajszűrés: a cat felhasználó bármely fajt regisztrálhatja,
de utána a listában csak a kutyákat láthatja.

1. Kijelentkezve nyisd meg a /list és /register címet: bejelentkezés szükséges.
2. Hibás jelszóval jelentkezz be: a login jelezzen hibát.
3. Ellenőrizd a táblázatot mind a négy felhasználóval.
4. dog felhasználóval közvetlenül írd be a /register címet is: a rejtett menüpont nem az egyetlen védelem.
5. cat felhasználóval próbálj üres, csak szóközös, illetve faj nélküli űrlapot menteni.
6. Adj meg érvényes nevet és kutya fajt: mentés után az új állat megjelenik a listán.
7. Ellenőrizd a böngészőfül címét a három oldalon, majd a kijelentkezést.
8. Dev profillal ellenőrizd a 8084-es portot.

## Javasolt olvasási sorrend

SecurityConfig → CurrentUserSecurityService → PetServiceImpl → LoginView → MainLayout → ListView → RegisterView.
A PetServiceSecurityTests valódi Spring proxyn keresztül ellenőrzi a service jogosultságait;
ez a teszt nem része a ZH-n megírandó alkalmazáskódnak.

A login szándékosan külön áll a MainLayout-tól, mert a felhasználónév és a védett menü a belépés utáni oldalakhoz tartozik.
A Binder.readBean csak beolvassa a modell értékeit. A writeBeanIfValid érvényes űrlap esetén írja vissza őket,
és csak ezután történik service-hívás és navigáció.

## Elvégzett ellenőrzések

- Java 25 alatt a clean package sikeres; 7 teszt, 0 hiba.
- Alapértelmezett szerverindulás: 8083. Dev profil: 8084.
- Böngésző: hibás belépés magyar hibaüzenettel, sikeres admin belépés, öt faj és azonosítóoszlop.
- Böngésző: kötelező mezők hibaüzenetei, magyar fajlista, új kutya mentése és megjelenése a listán.
- Böngésző: kijelentkezés, dog belépés, csak kutyák, azonosítóoszlop és regisztrációs menüpont nélkül.
- Böngésző: dog számára a közvetlen /register útvonal is tiltott.

Fejlesztéshez az IDE-s main indítást vagy a spring-boot:run parancsot használd.
A normál package Java-csomagot készít; önálló, beépített frontenddel futó JAR-hoz
a production Maven profil szolgál. A production csomagolást ebben a körben nem ellenőriztem.
