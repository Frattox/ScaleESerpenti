# Progetto Scale e Serpenti
## Descrizione
Questo è il mio progetto preparato per l'esame di Ingegneria del Software all'Università della Calabria.
## Struttura
La struttura del progetto si basa sul pattern Model-View-Control (MVC), quindi si trovano i seguenti package:  
- __model__: contiene le classi fondamentali per il corretto funzionamento interno del sistema
- __view__: interfacce grafiche
- __control__: controller capaci di gestire la comunicazione tra model e view
## Istruzioni per l'esecuzione 
Per effettuare una simulazione della applicazione:  
- bisogna scaricare l'SDK di JavaFX su https://gluonhq.com/products/javafx/
- entrare nella cartella dove è presente il file ScaleESerpenti3.jar attraverso il CMD
- eseguire questo comando:
```
java --module-path "/path/to/javafx-sdk/lib" --add-modules javafx.controls,javafx.fxml -jar .\ScaleESerpenti3.jar
```
