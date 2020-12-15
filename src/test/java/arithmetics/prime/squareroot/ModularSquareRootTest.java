package arithmetics.prime.squareroot;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class ModularSquareRootTest {

   @Test
   public void squareRoot() {
      int p = 11;
      ModularSquareRoot modularSquareRoot = new TonelliShanksModularSquareRoot(p);
      assertEquals(0, modularSquareRoot.get(0).get());
      assertEquals(1, modularSquareRoot.get(1).get());
      assertEquals(2, modularSquareRoot.get(4).get());
      assertEquals(3, modularSquareRoot.get(9).get());
      assertEquals(4, modularSquareRoot.get(5).get());
      assertEquals(5, modularSquareRoot.get(3).get());
      assertFalse(modularSquareRoot.get(2).isPresent());
   }

   @Test
   public void wikipediaExample() {
      assertEquals(13, new TonelliShanksModularSquareRoot(41).get(5).get());
   }
}
