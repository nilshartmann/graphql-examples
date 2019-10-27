# Abstrakt

GraphQL: Teufelszeug oder Heilsbringer - Eine interaktive Einführung

GraphQL ist eine Sprache zur Abfrage von Daten, die häufig als Alternative zu REST bezeichnet wird und mit der Clients selbst auswählen können, welche Daten sie Use-Case-abhängig von einem Server lesen oder schreiben wollen. Dadurch verspricht GraphQL nicht nur Effizienz zur Laufzeit, sondern auch eine einfache Entwicklung und Pflege der damit entwickelten APIs. GraphQL wird innerhalb der GraphQL Foundation, in der u. a. Atlassian, GitHub und AWS Mitglied sind, standardisiert und ist mittlerweile für eine Vielzahl von Programmiersprachen sowohl im Backend als auch für das Frontend verfügbar.
GraphQL bringt viele neue Ideen mit, bricht aber gleichzeitig mit vielen erlernten und vertrauten Konzepten aus der REST-Architektur und gilt deshalb dem einen als Teufelszeug und anderen gerade deshalb als Heilsbringer.
In diesem interaktiven Vortrag sehen wir uns Grundlagen und Konzepte der Sprache und mögliche Einsatzszenarien an. Exemplarisch schauen wir an Hand einer Java-Anwendung, wie ihr GraphQL APIs auch für Eure eigenen Anwendungen bauen könnt (Konzepte sind auf andere Sprachen übertragbar). Gemeinsam können wir dann überprüfen, inwieweit gängige Aussagen über GraphQL ("REST-Alternative", "SQL für APIs", “kann kein Caching”, “ist nicht für HTTP/2 gemacht”, “Das Backend wird überflüssig”) zutreffend sind.



# Vorbereitung

## IDEA

- Öffnen Anwendung in IDEA
- Starten `backend` und `userservice`
- Testen ob Code Completion auf BeerPage.tsx geht
- NICHT Frontend starten

## ngrok starten (TODO)

## Hostname irgendwo hinschreiben

## VS Code

- `code /Users/nils/develop/javascript/apollo-graphql-examples/java/beeradvisor_graphql-java-dataloader/frontend-hooks`
- App starten
- Testen ob Code completion geht (AddRatingMutation.tsx und BeerPage.tsx)
- Öffnen ShopPage und ShopPageQuery nebeneinander

# Client DEMO

- `yarn codegen:watch` starten
- Problems View aufmachen
- ShopPage: street Feld aus address löschen => Fehler zeigen
