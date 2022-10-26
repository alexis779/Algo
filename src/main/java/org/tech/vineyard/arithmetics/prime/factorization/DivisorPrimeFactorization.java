package org.tech.vineyard.arithmetics.prime.factorization;

import java.util.ArrayList;
import java.util.List;

public class DivisorPrimeFactorization implements PrimeFactorization {

   @Override
   public List<PrimeFactor> factors(int a) {
      List<PrimeFactor> primeFactors = new ArrayList<>();

      for (int divisor = 2; divisor * divisor <= a; divisor++) {
         if (a % divisor == 0) {
            int exponent = 0;
            while (a % divisor == 0) {
               a /= divisor;
               exponent++;
            }
            primeFactors.add(new PrimeFactor(divisor, exponent));
         }
      }

      if (a != 1) {
         primeFactors.add(new PrimeFactor(a, 1));
      }

      return primeFactors;
   }
}
