# SomAparcament - SomHackathon 2018 - Mataró

> Millorar la mobilitat dels espais urbans, mitjançant la gestió, monitorització i anàlisi de l’ús de les zones d’estacionament limitat.

### Problema
Actualment es considera **millorable l'ús de les zones de Càrrega i Descàrrega** a les ciutats, concretament en aquest cas a **Mataró**.

Al no haver-hi control dels estacionaments a les zones de Càrrega i Descàrrega no s'aprofiten en la seva totalitat. Ja sigui perquè les zones estan sobre-ocupades o sota-ocupades. Això influeix en què **es genera un innecessari tràfic i contaminació** dels vehicles a la mateixa ciutat. També afecta directament a la **satisfacció del conductor** o usuari del vehicle. Per altre banda, actualment s'utilitza el **rellotge de cartó**, manual, i **no aporta informació** sobre l'estat de les places, degut a que no hi ha comunicació telemàtica. Les solucions que ja existeixen no són *Open Source*, o, directament són privades.

### Resposta
  - **Dades útils**: Recollida i oferiment de dades dels vehicles.
  - **Anàlisis i visualització**: Involucració de l'usuari.
  - **Open Source**
  - **Disponibilitat**: Consulta en temps real de les places disponibles.
  - **Col·laboració**: Participació de la ciutadania.
  - **Modernització**: Apropament del sistema al món digital.

### Solució
Com a solució s'ha realitzat una plataforma que permet la **obtenció** i **explotació** de les dades de les zones de *CiD*. Aquesta solució es compon d'un **dashboard web** per al *frontend*, una **aplicació Android** per a la introducció de dades i una **API** com a *backend*.

Aquesta sol·lució és col·laborativa pel benefici comú. I es diferencia de les sol·lucions ja existents en que és:
 - *Open Source*: És replicable, escalable i adaptable.
 - *Open Data*: Es consumeixen dades obertes, es generen i es col·labora.

### Tech
Aquest projecte utilitza *Open Source* per al seu funcionament:

* [Flask] - Framework Python com a backend REST.
* [SQLite] - Persistència de les dades.
* [LeafletJS] - Representació de mapa a la web.
* [HTML5] - Web.
* [JS] - Web.
* [Android] - Aplicació Android nativa.
* [Open Street Maps] - Representació de mapa a l'aplicació.

### API REST
Hi ha una [documentació de l'API REST](https://github.com/SomHackathon18/somhackathon18-somrotacio/tree/master/backend) disponible.

Llicència
----
MIT

**Free Software, Hell Yeah!**
