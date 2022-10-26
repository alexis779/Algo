package org.tech.vineyard.arithmetics.prime.squareroot;

import org.tech.vineyard.arithmetics.ModularArithmetics;

import java.util.Optional;
import java.util.stream.IntStream;

/**
 * https://en.wikipedia.org/wiki/Tonelli%E2%80%93Shanks_algorithm#The_algorithm
 */
public class TonelliShanksModularSquareRoot implements ModularSquareRoot {

   /**
    * Odd prime number.
    */
   int p;

   final ModularArithmetics modularArithmetics;

   public TonelliShanksModularSquareRoot(int p) {
      this.p = p;
      modularArithmetics = new ModularArithmetics(p);
      initVariables();
   }

   /**
    * Values used to start algorithm.
    */
   int S, Q, c;

   private void initVariables() {
      S = 0;
      Q = p-1;
      while (Q % 2 == 0) {
         Q /= 2;
         S++;
      }
      int z = quadraticNonResidue();
      c = modularExponent(z, Q);
   }

   /**
    * @return a quadratic non residue.
    */
   private int quadraticNonResidue() {
      return IntStream.range(2, p)
            .filter(this::isQuadraticNonResidue)
            .findFirst()
            .getAsInt();
   }

   /**
    * Euler's criterion:
    *    n^{\frac{p-1,2}} = \pm 1
    *
    * @param z
    * @return true if z is a quadratic non residue.
    */
   private boolean isQuadraticNonResidue(int z) {
      return modularExponent(z, (p-1)/2) == p-1;
   }

   @Override
   public Optional<Integer> get(int n) {
      if (n == 0) {
         return Optional.of(0);
      }

      if (isQuadraticNonResidue(n)) {
         return Optional.empty();
      }

      if (p % 4 == 3) {
         int r = modularExponent(n, (p+1)/4);
         return Optional.of(smallerRoot(r));
      }

      return tonelliShanks(n);
   }

   private Optional<Integer> tonelliShanks(int n) {
      int M = S;
      int t = modularExponent(n, Q);
      int R = modularExponent(n, (Q+1)/2);
      int c = this.c;

      while (t != 1) {
         int i = 0;
         int square = t;
         while (square != 1) {
            i++;
            square = square(square);
         }
         int b = modularExponent(c, modularExponent(2, M-i-1));
         M = i;
         c = square(b);
         t = multiply(t, c);
         R = multiply(R, b);
      }

      return Optional.of(smallerRoot(R));
   }

   /**
    * Roots are R & p - R
    *
    * @param R a root
    * @return the small root
    */
   private int smallerRoot(int R) {
      return Math.min(R, p - R);
   }

   private int modularExponent(int b, int e) {
      return (int) modularArithmetics.exponent(b, e);
   }

   private int square(int a) {
      return modularArithmetics.square(a);
   }

   private int multiply(int a, int b) {
      return (int) modularArithmetics.multiply(a, b);
   }
}
