package linear.programming;

public enum Constraint {
   LOWER(1, "<="), // <=
   GREATER(2, ">="), // >=
   EQUAL(1, "=="); // =

   /**
    * Number of extra variables for the type of constraint:
    * - <= requires slack
    * - = requires artificial
    * - >= requires slack & artificial
    */
   int variableCount;
   String symbol;
   Constraint(int variableCount, String symbol) {
      this.variableCount = variableCount;
      this.symbol = symbol;
   }
   public int getVariableCount() {
      return variableCount;
   }

   @Override
   public String toString() {
      return symbol;
   }
}
