# Programozás 5 – gyakorlófeladatok

Az öt gyakorlóprojekt közös Git-repositoryja. Mindegyik projekt önálló Maven-alkalmazás, külön almappában.

| Gyakorlófeladat | Maven-projekt | JDK |
| --- | --- | --- |
| zhgyak1 | `zhgyak1/zh-vaadin/zh-vaadin` | Java 21 |
| zhgyak2 | `zhgyak2/zh-vaadin` | Java 25 |
| zhgyakMAGAMTOL | `zhgyakMAGAMTOL/zh-vaadin` | Java 25 |
| zhgyak3-konyvtar | `zhgyak3-konyvtar/zh-konyvtar` | Java 25 |
| zhgyak4-eszkozok | `zhgyak4-eszkozok/zh-eszkozok` | Java 25 |

IntelliJben az adott projekt `pom.xml` fájlját nyisd meg Maven-projektként.
Parancssorból lépj a fenti Maven-projekt mappájába, majd indítsd:

```powershell
.\mvnw.cmd spring-boot:run
```

A repository a forráskódokat, a Maven-konfigurációt és wrappert, valamint a meglévő feladatleírásokat tartalmazza. A buildkimenetek, generált Vaadin-fájlok, naplók és helyi IDE-beállítások kimaradnak. A `prog5` többi mappája és a gyökérben lévő ZIP/PDF fájlok nem részei ennek a repositorynak.

## Közös GitHub-remote

A `prog5` mappából, egy üres GitHub-repository létrehozása után:

```powershell
git remote add origin <GitHub-repo-URL>
git push -u origin main
```

A további Git-műveleteket is a közös repositoryn végezd; az öt projekt egyetlen előzményt és remote-ot használ.
