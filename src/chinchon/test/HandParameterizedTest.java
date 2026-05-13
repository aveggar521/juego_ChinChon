package chinchon.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import chinchon.model.Card;
import chinchon.model.Hand;
import chinchon.model.Rank;
import chinchon.model.Suit;

/**
 * Clase de pruebas unitarias parametrizadas para validar la lógica de la mano. Verifica el cálculo de puntuación y las condiciones de cierre del juego.
 */
public class HandParameterizedTest {

  /**
   * Verifica que la suma de puntos sea correcta para distintas combinaciones de cartas.
   * 
   * @param valor1          Valor numérico de la primera carta.
   * @param valor2          Valor numérico de la segunda carta.
   * @param puntosEsperados Resultado esperado de la suma.
   */
  @ParameterizedTest
  @CsvSource({ "1, 2, 3", "7, 10, 17", "11, 12, 23", "5, 5, 10" })
  void calcularPuntos_conDosCartas_devuelveSumaEsperada(int valor1, int valor2, int puntosEsperados) {
    Hand hand = new Hand();
    hand.addCard(new Card(Suit.COINS, buscarRankPorValor(valor1)));
    hand.addCard(new Card(Suit.CUPS, buscarRankPorValor(valor2)));

    assertEquals(puntosEsperados, hand.calculatePoints());
  }

  /**
   * Comprueba si el método permite cerrar la mano con puntuaciones válidas (menores o iguales a 5).
   * 
   * @param valorCarta Valor de la carta individual a probar.
   */
  @ParameterizedTest
  @ValueSource(ints = { 0, 1, 5 })
  void puedeCerrar_conPuntosPermitidos_devuelveTrue(int valorCarta) {
    Hand hand = new Hand();
    if (valorCarta > 0) {
      hand.addCard(new Card(Suit.SWORDS, buscarRankPorValor(valorCarta)));
    }

    assertTrue(hand.canClose());
  }

  /**
   * Método auxiliar para convertir un valor entero en una constante del Enum Rank.
   * 
   * @param valor El valor numérico de la carta (1-12).
   * @return El objeto Rank correspondiente.
   */
  private Rank buscarRankPorValor(int valor) {
    for (Rank r : Rank.values()) {
      if (r.getValue() == valor) {
        return r;
      }
    }
    return Rank.ONE;
  }
}
