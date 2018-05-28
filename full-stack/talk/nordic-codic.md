# Full Stack GraphQL mit Apollo (Nils Hartmann)

GraphQL ist eine Sprache zur Abfrage von Daten, die häufig als "Alternative zu REST" bezeichnet wird. Gegenüber REST verspricht GraphQL eine höhere Flexibilität, weil der Client per Query bestimmen kann, welche Daten er vom Server laden oder schreiben will. Hinzu kommt, dass GraphQL über ein Typsystem verfügt, mit dem die API beschrieben wird.

In diesem Talk werde ich Euch eine kurze Einführung in die Konzepte von GraphQL geben und dann an Hand des Apollo Frameworks zeigen, wie man einen GraphQL Server implementiert und wie man darauf exemplarisch aus einem React/TypeScript Frontend zugreifen kann.

*Länge:* 45 Minuten zzgl Q&A

# Vorbereitung

1. IDEA starten:

```
idea /Users/nils/develop/javascript/apollo-graphql-schema-stitching/full-stack/frontend/
```

# Ablauf

* Intro Folien
* Beispiel zeigen, Model erklären
* GraphiQL zeigen um API zu explorieren
* Warum geht sowas wie GraphiQL => Schema und Typ-Beschreibungen sind PFLICHT
* Queries mit GraphiQL
* Mutation mit GraphiQL
* Nach GraphiQL nochmal Anwendung zeigen, um zu zeigen, dass neues Rating da ist
* Was geht außerdem? Tooling in der Entwicklung!
  * IDEA zum Entwickeln von Abfragen => BeerRatingApp.tsx
  * Eventuell TypeScript, ebenfalls BeerRatingApp
* *
