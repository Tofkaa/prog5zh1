# Próba-ZH 4 – Egyetemi eszköznyilvántartás

1. Olvasd el a [feladatleírást](FELADATLEIRAS.md). Javasolt idő: 140 perc, bónusz: +20 perc.
2. IntelliJben a `zh-eszkozok/pom.xml` fájlt nyisd meg Maven projektként.
3. A Project SDK és a Maven futtató JDK-ja **Java 25** legyen. A gép alapértelmezett Java 17-e ehhez nem elég.
4. Indítsd az `EquipmentApplication` main metódusát. Kiinduláskor a port **8080**.
5. A `zh-eszkozok` mappában dolgozz. A kész repositoryhoz és modellhez nem kell hozzányúlni.

Parancssorból, a `zh-eszkozok` mappában:

```powershell
# Ha a terminálban még Java 17 az alapértelmezett:
$env:JAVA_HOME = 'C:\Users\Tocka\.jdks\temurin-25.0.5'
.\mvnw.cmd clean package
.\mvnw.cmd spring-boot:run
```

Dev profil (a portbeállítás elkészítése után):

```powershell
.\mvnw.cmd spring-boot:run "-Dspring-boot.run.profiles=dev"
```

A kiindulóállapot szándékosan hiányos, nem tartalmaz kész megoldást. A megadott felhasználók a security-feladat után használhatók. Kezdetben a Spring alapértelmezett belépőoldala is megjelenhet (generált felhasználó: `user`, jelszó a konzolban); ez nem az elkészítendő Vaadin LoginForm.

A `/` ideiglenes kezdőlapját a `StarterView` adja. A lista root aliasának elkészítése előtt töröld a `StarterView` osztályt vagy a `@Route` annotációját, különben útvonalütközés lesz.

A magyar és angol szövegek készen vannak. **A nyelvválasztó működését neked kell megírnod**, az órai `ComboBox<Locale>` + munkamenet + újratöltés megoldás szerint. Nem kell fordításokat keresned vagy adatbázist telepítened.

Az indulóállapot fordíthatósága nem jelenti a feladatok teljesítését. A részletes önellenőrzés a feladatlap végén található.
