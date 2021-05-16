# UI tesztek készítése
Az munkát az Androidban hivatalosan támogatott Espresso UI tesztelő keretrendszer segítségével valósítottuk meg. Első lépésként az alkalmazás átvizsgálása után megbeszéltük és felosztottuk a teszteseket. A UI tesztek elkészítésénél törekedtünk az alkalmazás főbb funcionalitásainak lefedésére. Ez magába foglalja a hozzáadást, a módosítást, a törlést, a rendezést és összetett UI elemek megjelenítését (pl. webview, viewpager).

Mivel volt már előzetes tapasztalatunk az Android platformmal, valamint az Espresso tesztek készítésével, így volt kiindulási alapunk. A házi feladat során ezeket az ismereteket mélyíthettük el.

Tanulságnak megfogalmazható, hogy az ilyesfajta tesztek készítésekor különösen fontos szerepet kap az újrafelhasználható kód írása. Mi ezeket egy CommonTest nevű osztályba szerveztük ki. Egy két bonyolultabb tesztlogika megírása után sokkal könnyebb volt összelegózni a maradékot.