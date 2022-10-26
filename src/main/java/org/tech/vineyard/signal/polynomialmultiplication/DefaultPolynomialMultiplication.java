package org.tech.vineyard.signal.polynomialmultiplication;

import org.tech.vineyard.linear.algebra.IntVector;

public class DefaultPolynomialMultiplication implements PolynomialMultiplication {

   @Override
   public IntVector multiply(IntVector a, IntVector b) {
      int size = a.size() + b.size() - 1;
      IntVector c = new IntVector(size);
      for (int j = 0; j <= size; j++) {
         c.set(j, coefficient(a, b, j));
      }
      return c;
   }

   private int coefficient(IntVector a, IntVector b, int j) {
      int coefficient = 0;
      for (int i = 0; i <= j; i++) {
         coefficient = add(coefficient, multiply(a.get(i), b.get(j-i)));
      }
      return coefficient;
   }

   private int add(int a, int b) {
      return a + b;
   }

   private int multiply(int a, int b) {
      return a * b;
   }
}
