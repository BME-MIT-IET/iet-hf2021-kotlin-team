# CI beüzemelése

A CI beüzemeléséhez a GitHub Actions-t használtuk. Jelenlegi beállítása szerint minden pull request esetén ellenőrzi, hogy a buildelési folyamat sikeresen megtörtént-e.

A projekthez kapcsolódóan beállításra kerül a szükséges JDK verzió, ellenőrzésekkor a "gradlew test" és a "gradlew assemble" ellenőrzések futnak le. Az első ellenőrzés során a "gradlew" dependencia hiányával kapcsolatban jelzett hibát, ez javítva lett.

Tesztelési célzattal is létre lett hozva pull request, vizsgálva az ellenőrzés sikerességét. A későbbi merge-elések során minden alkalommal lefutott az ellenőrzés, előfordult, hogy talált hibákat, ilyen esetekben átnéztük és javítottuk azokat.



Szerintem hosszútávon egyértelműen megtérülő befektetés a Continous Integration beüzemelése, főleg akkor, ha többen dolgoznak egy projekten. Minden alkalommal egy visszajelzést kaphatunk arról, hogy az egyes módosításoknak volt-e negatív hatása a meglévő kódbázisra.
