package binarytree.binaryindexedtree;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CountQueryTest {

   @Test
   public void binaryIndexedTree() {
      BinaryIndexedTree bit = new BinaryIndexedTree(10);
      bit.change(4, 1);
      bit.change(3, 1);
      bit.change(8, 1);

      assertEquals(3, bit.get(9));
      assertEquals(2, bit.get(4));

      bit.change(5, 1);
      assertEquals(4, bit.get(8));
   }
}
