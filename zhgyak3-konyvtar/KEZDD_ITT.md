# Könyvtári gyakorló-ZH

1. Olvasd el a [feladatleírást](FELADATLEIRAS.md).
2. IntelliJben a `zh-konyvtar/pom.xml` fájlt nyisd meg Maven projektként.
3. A Project SDK és a Maven futtató JDK-ja Java 25 legyen.
4. Kiindulásként indítsd a LibraryApplication main metódusát. Az ideiglenes kezdőlap a `/` címen van,
   alapból 8080 porton; a végleges portok beállítása a feladat része.
5. A megoldásodat a `zh-konyvtar` almappában írd meg. Kész megoldás nincs a csomagban.

Parancssorból a projekt almappájában:

```powershell
.\mvnw.cmd clean package
.\mvnw.cmd spring-boot:run
```

Dev profil:

```powershell
.\mvnw.cmd spring-boot:run "-Dspring-boot.run.profiles=dev"
```

Az indulóprojekt szándékosan hiányos: a megadott felhasználók csak a security-feladat elkészítése után működnek.
A repository kész, másolatokat ad vissza. Az új rekord azonosítóját a save visszatérési értéke tartalmazza.
Nem kell adatbázist telepítened és nem kell függőségeket vagy fordításokat keresgélned.
A Java fordítás ellenőrzése nem jelenti a később elkészítendő feladatok teljesítését.
