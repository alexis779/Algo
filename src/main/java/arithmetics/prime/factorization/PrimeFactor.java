package arithmetics.prime.factorization;

public class PrimeFactor {
   final int prime;
   final int exponent;
   public PrimeFactor(int prime, int exponent) {
      this.prime = prime;
      this.exponent = exponent;
   }
   public int getPrime() { return prime; }
   public int getExponent() {
      return exponent;
   }

   @Override
   public boolean equals(Object o) {
      PrimeFactor factor = (PrimeFactor) o;
      return prime == factor.getPrime() && exponent == factor.getExponent();
   }
}