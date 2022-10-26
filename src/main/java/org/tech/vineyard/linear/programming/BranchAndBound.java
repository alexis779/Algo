package org.tech.vineyard.linear.programming;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;


/**
 * where b_i = \floor{x^{opt}_i} is an optimal solution variable rounded value
 *
 * Apply dual simplex method since solution is
 * - dual feasible
 * - primal infeasible
 *
 * {@literal
 *  objective function coefficients are <= 0, solution is optimal
 *  x^{s}_i = b_i - x_i <= 0, solution is infeasible
 * }
 */
public class BranchAndBound {

   private static final Logger LOG = LoggerFactory.getLogger(BranchAndBound.class);

   private final Simplex root;
   private Objective objective;
   private double objectiveValue;
   private double[] solution;
   private int intObjectiveValue;
   private int[] intSolution;

   public BranchAndBound(Simplex root) {
      this.root = root;

      objective = root.objective();
      objectiveValue = objective == Objective.MAX ? Double.MIN_VALUE : Double.MAX_VALUE;

      traverse(root, 0);
      if (solution != null) {
         setSolution();
         LOG.info("Optimal solution {} {}", objectiveValue, Arrays.toString(intSolution));
      }
   }

   private void setSolution() {
      intObjectiveValue = (int) objectiveValue;
      intSolution = new int[solution.length];
      for (int i = 0; i < solution.length; i++) {
         intSolution[i] = (int) solution[i];
      }
   }

   public int objectiveValue() {
      return intObjectiveValue;
   }

   public int[] solution() {
      return intSolution;
   }

   private void traverse(Simplex current, int i) {
      double[] solution = current.solution();

      if (i == solution.length) {
         // solution is integral
         if ((objective == Objective.MAX && current.objectiveValue() > objectiveValue) ||
             (objective == Objective.MIN && current.objectiveValue() < objectiveValue)) {
            objectiveValue = current.objectiveValue();
            this.solution = current.solution();
         }
         // bound
         return;
      }

      double x = solution[i];
      if (isInteger(x)) {
         traverse(current, i+1);
         return;
      }

      int b = (int) Math.floor(x);
      // x_i <= b
      branch(current, i, b, Constraint.LOWER);

      // x_i >= b+1
      branch(current, i, b+1, Constraint.GREATER);
   }

   private void branch(Simplex current, int i, int b, Constraint constraint) {
      if (b == 0) {
         return;
      }

      Simplex child = new Simplex(current);
      try {
         child.addIntegerConstraint(i, b, constraint);
      } catch (RuntimeException re) {
         // bound, new constraint is infeasible
         return;
      }

      LOG.info("Solution {} {}", child.objectiveValue(), Arrays.toString(child.solution()));

      if ((objective == Objective.MAX && child.objectiveValue() <= objectiveValue) ||
            (objective == Objective.MIN && child.objectiveValue() >= objectiveValue)) {
         // bound, solution does not improve current objective
         return;
      }

      traverse(child, 0);
   }

   private boolean isInteger(double x) {
      return Math.floor(x) == x;
   }

   private boolean isIntegerSolution(double[] solution) {
      return Arrays.stream(solution)
            .allMatch(this::isInteger);
   }
}
