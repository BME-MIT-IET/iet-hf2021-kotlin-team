# Projekt célja és felhasznált eszközök

A házi feladattal a célunk egy meglévő Kotlinban írt Android projekt minőségének növelése tesztek, statikus analízis eszköz, CI és deployment hozzáadásával, hogy az a jövőben jobban karbantartható, átláthatóbb és hibamentesebb lehessen.

A projekt egyben lehetőséget ad nekünk különböző eszközök megismerésére, amik ezeket biztosítják. 
UI tesztekhez az Android hivatalos Espresso könyvtárát használjuk, BDD tesztekhez pedig a Cucumber keretrendszert integráltuk a projektbe. Ezen kívül deployment kezeléshez Vagrantot, statikus kódanalízishez pedig SonarQube-ot alkalmazunk. A CI megvalósításához GitHub Actions van integrálva.
