package org.tech.vineyard.arithmetics.prime.factorization;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Divisors {

   public List<Integer> divisors(int a) {
      PrimeFactorization factorization = new DivisorPrimeFactorization();
      List<PrimeFactor> factors = factorization.factors(a);

      List<Integer> divisors = new ArrayList<>();
      addDivisors(divisors, factors, 0, 1);
      Collections.sort(divisors);
      return divisors;
   }

   private void addDivisors(List<Integer> divisors, List<PrimeFactor> factors, int i, int n) {
      if (i == factors.size()) {
         divisors.add(n);
         return;
      }

      addDivisors(divisors, factors, i+1, n);
      PrimeFactor factor = factors.get(i);
      for (int exponent = 0; exponent < factor.getExponent(); exponent++) {
         n *= factor.getPrime();
         addDivisors(divisors, factors, i+1, n);
      }
   }
}
