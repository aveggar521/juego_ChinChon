# Proyecto Chinchón - Java

Este proyecto consiste en la implementación del juego de cartas español Chinchón. Se ha desarrollado en Java siguiendo los principios de la programación orientada a objetos, utilizando 2 patrones de diseño (Singlenton y Factory en mi caso) para asegurar una estructura escalable y organizada.

## Descripción del Proyecto

El juego permite partidas entre un jugador humano y una inteligencia artificial (máquina) que toma decisiones de descarte automáticas. El motor del juego controla el flujo completo de la partida: desde el reparto de cartas y la gestión de turnos hasta el cálculo de puntuaciones y la eliminación de jugadores cuando superan el límite de puntos establecido.

Para el desarrollo se han aplicado los siguientes conceptos técnicos:

* **Patrón Singleton:** Implementado en la clase `Game` para garantizar que solo exista una instancia activa de la partida y centralizar el control del flujo.
* **Patrón Factory:** Utilizado en `MemberFactory` para instanciar de forma desacoplada tanto a jugadores humanos como a la IA.
* **Herencia y Polimorfismo:** Empleados en la jerarquía de `Member`, permitiendo que el juego gestione una lista genérica de jugadores independientemente de si son humanos o máquinas.
* **Encapsulamiento:** Toda la lógica de validación de combinaciones (tríos y escaleras) se encuentra protegida dentro de la clase `Hand`.

## Pruebas del Sistema

Para asegurar el correcto funcionamiento de la lógica de juego, se han realizado diferentes tipos de pruebas unitarias utilizando JUnit:

### Pruebas de Caja Negra
Se han diseñado casos de prueba basados exclusivamente en las especificaciones de las reglas del Chinchón, sin tener en cuenta la implementación interna:
* **Validación de Combinaciones:** Verificación de que el sistema identifica correctamente tríos de diferentes palos y escaleras del mismo palo.
* **Reglas de Cierre:** Comprobación de que un jugador solo puede cerrar la ronda si cumple los requisitos de puntuación (tener 5 puntos o menos en cartas no combinadas).
* **Límite de Puntuación:** Verificación de que los jugadores son eliminados correctamente al alcanzar el límite de puntos de la partida.

### Pruebas de Caja Blanca
Se han realizado pruebas estructurales para garantizar que todos los caminos lógicos del código se ejecutan correctamente:
* **Caminos de Combinación:** Pruebas específicas para el método `getCombinedMask` para asegurar que las cartas se marcan como combinadas correctamente en situaciones complejas, como escaleras de gran longitud.
* **Lógica de Reabastecimiento:** Verificación del método de reinicio del mazo cuando se agotan las cartas y se debe recuperar la pila de descartes.
* **Control de Flujo:** Pruebas sobre los bucles de turno para confirmar que el juego gestiona correctamente los saltos de turno de jugadores eliminados.

### Requisitos Previos
* Java JDK 21 o superior.
* Un IDE compatible con Java (Eclipse, IntelliJ o VS Code).

### Instalación y Ejecución
1. Clonar el repositorio o descargar el código fuente.
2. Importar el proyecto en el IDE como un proyecto Java existente.
3. Localizar la clase `Main` dentro del paquete `chinchon.app`.
4. Ejecutar el método `main`.

### Cómo Jugar
1. **Inicio:** El juego configura los jugadores y reparte 7 cartas a cada uno de forma automática.
2. **Robo:** En tu turno, deberás elegir entre robar una carta del mazo o la última carta de la pila de descartes.
3. **Descarte:** Tras robar, deberás seleccionar qué carta descartar (índice 1-8) para volver a quedarte con 7 cartas.
4. **Cierre:** Si tus cartas no combinadas suman 5 puntos o menos, el sistema te permitirá cerrar la ronda. Si logras combinar las 7 cartas (Chinchón), ganas la partida directamente.
5. **Puntuación:** Al cerrar una ronda, se suman los puntos de las cartas sueltas. Aquel jugador que supere el límite de 100 puntos queda fuera de la partida.