package linear.programming;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Find a solution x of the optimization problem:
 *
 * Maximize
 * c^T * x
 *
 * with constraints
 * A * x <= b
 * x >= 0
 */
public class Simplex {

    private static final Logger LOG = LoggerFactory.getLogger(Simplex.class);

    /**
     * Objective function of the original problem.
     */
    private final double[] c;
    /**
     * Constraint LHS
     */
    private final double[][] A;
    /**
     * Constraint RHS
     */
    private final double[] b;
    /**
     * Type of constraints
     */
    private final Constraint[] constraints;
    /**
     * Maximize or Minimize objective.
     */
    private final Objective objective;
    /**
     * number of original variables
     */
    int n;
    /**
     * number of constraints
     */
    int m;
    /**
     * tableau
     */
    private double[][] t;
    /**
     * List of original variables.
     * If value is not 0, map to the row associated to the constraint.
     */
    private int[] originalVariables;
    /**
     * List of slack variables.
     * If value is not 0, map to the row associated to the constraint.
     */
    private int[] slackVariables;
    /**
     * List of artificial variables.
     * If value is not 0, map to the row associated to the constraint.
     */
    private int[] artificialVariables;
    /**
     * List of basic variables.
     * If value is not 0, map to the row associated to the constraint.
     */
    private int[] basicVariables;
    /**
     * row in the tableau corresponding to the objective function
     */
    private int objectiveRow;
    /**
     * Index of the last column in the tableau
     */
    private int lastColumn;
    /**
     * Optimization problem solution
     */
    private double[] basicFeasibleSolution;

    public Simplex(double[] c, double[][] A, double[] b, Constraint[] constraints, Objective objective) {
        this.c = c;
        this.A = A;
        this.b = b;
        this.constraints = constraints;
        this.objective = objective;

        runFullTableauMethod();
    }

    public Simplex(Simplex simplex) {
        this.c = simplex.c;
        this.A = simplex.A;
        this.b = simplex.b;
        this.constraints = simplex.constraints;
        this.objective = simplex.objective;

        this.t = simplex.t;
        this.n = simplex.n;
        this.m = simplex.m;
        this.lastColumn = simplex.lastColumn;

        this.originalVariables = simplex.originalVariables;
        this.slackVariables = simplex.slackVariables;
        this.artificialVariables = simplex.artificialVariables;
        this.basicVariables = simplex.basicVariables;

        objectiveRow = 1;
    }

    /**
     * After running the method, this returns the minimum value.
     *
     * @return the object function value
     */
    public double objectiveValue() {
        switch(objective) {
            case MAX:
                return -t[objectiveRow][lastColumn];
            case MIN:
                return t[objectiveRow][lastColumn];
            default:
                throw new RuntimeException("Can not support objective");
        }
    }

    private void setSolution() {
        basicFeasibleSolution = new double[n];
        for (int j = 2; j < 2 + n; j++) {
            basicFeasibleSolution[j - 2] = constraintValue(j);
        }
    }

    /**
     * @return the basic variable tuple after running full-tableau method
     */
    public double[] solution() {
        return basicFeasibleSolution;
    }

    /**
     * Sanity check of dimensions of c, A, b and constraints
     */
    private void validate() {
        if (c.length != A[0].length) {
            throw new RuntimeException("Invalid number of variables");
        }
        this.n = c.length;

        if (! (A.length == b.length && A.length == constraints.length)) {
            throw new RuntimeException("Invalid number of constraints");
        }
        this.m = b.length;
    }

    /**
     * Assign a column to each variable, depending on the constraint type.
     */
    private void initVariables() {
        int size = 2 + n + Arrays.stream(constraints)
              .mapToInt(Constraint::getVariableCount)
              .sum() + 1;

        lastColumn = size-1;
        t = new double[2 + m][size];
        originalVariables = new int[size];
        slackVariables = new int[size];
        artificialVariables = new int[size];
        basicVariables = new int[size];

        for (int j = 0; j != size; j++) {
            originalVariables[j] = -1;
            slackVariables[j] = -1;
            artificialVariables[j] = -1;
            basicVariables[j] = -1;
        }

        int currentVariable = 2;
        for (int j = 0; j < n; j++) {
            originalVariables[currentVariable++] = j;
        }
        for (int i = 0; i < m; i++) {
            switch(constraints[i]) {
                case LOWER:
                    /**
                     * Sum a_{ij} x_j <= b_i
                     * becomes
                     * Sum a_{ij} x_j + x^{s}_i = b_i
                     **/
                    basicVariables[currentVariable] = i;
                    slackVariables[currentVariable++] = i;
                    break;
                case EQUAL:
                    /**
                     * Sum a_{ij} x_j = b_i
                     * becomes
                     * Sum a_{ij} x_j + x^{a}_i = b_i
                     **/
                    basicVariables[currentVariable] = i;
                    artificialVariables[currentVariable++] = i;
                    break;
                case GREATER:
                    /**
                     * Sum a_{ij} x_j >= b_i
                     * becomes
                     * Sum a_{ij} x_j - x^{s}_i + x^{a}_i = b_i
                     **/
                    slackVariables[currentVariable++] = i;
                    basicVariables[currentVariable] = i;
                    artificialVariables[currentVariable++] = i;
                    break;
            }
        }
    }

    private void runFullTableauMethod() {
        validate();
        initTableau();
        phase1();
        phase2();
        setSolution();
    }

    private void initTableau() {
        initVariables();
        initPhase1Goal(t[0]);
        initPhase2Goal(t[1]);
        initConstraints();
        initIdentity();
        initConstants();
        printTableau();
    }

    /**
     * Init objective function of the auxiliary problem as the sum of the artificial variables to minimize.
     *
     * min Sum x^{a}_i
     *
     * which is equivalent to
     *
     * max Sum -x^{a}_i
     *
     * @param row
     */
    private void initPhase1Goal(double[] row) {
        row[0] = 1;
        row[1] = 0;
        for (int j = 2; j < lastColumn; j++) {
            if (artificialVariables[j] != -1) {
                row[j] = -1;
            } else {
                row[j] = 0;
            }
        }
    }

    /**
     * Init objective function of the original problem
     *
     * @param row
     */
    private void initPhase2Goal(double[] row) {
        row[0] = 0;
        row[1] = 1;
        for (int j = 2; j < lastColumn; j++) {
            if (originalVariables[j] != -1) {
                switch(objective) {
                    case MAX:
                        row[j] = c[originalVariables[j]];
                        break;
                    case MIN:
                        row[j] = -c[originalVariables[j]];
                        break;
                }
            } else {
                row[j] = 0;
            }
        }
    }

    private void initConstraints() {
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                t[2 + i][2 + j] = A[i][j];
            }
        }
    }

    private void initIdentity() {
        for (int j = 2; j < lastColumn; j++) {
            if (basicVariables[j] != -1) {
                t[2 + basicVariables[j]][j] = 1;
            }
        }
    }

    private void initConstants() {
        for (int i = 0; i < m; i++) {
            t[2 + i][lastColumn] = b[i];
        }
    }

    /**
     * Null out the coefficients of the artificial variables.
     */
    private void priceOut() {
        LOG.info("Price out artificial variables");
        printTableau();
        for (int j = 2; j < lastColumn; j++) {
            if (artificialVariables[j] != -1) {
                sumAndReplace(t[0], t[2 + artificialVariables[j]]);
            }
        }
    }

    /**
     * Run simplex on the auxiliary problem
     *
     * Min Sum x^{a}_i
     *
     * then drive artificial variables out of the basis if any
     */
    private void phase1() {
        LOG.info("Phase I");
        objectiveRow = 0;

        priceOut();
        runSimplex();

        if (objectiveValue() != 0) {
            throw new RuntimeException("Can not find a feasible basic solution");
        }

        LOG.info("Drive out artificial variables");
        int c;
        while ((c = basicArtificialVariable()) != -1) {
            LOG.info("Leaving variable {}", c);
            artificialPivot(c);
        }

        printTableau();
    }

    private void runSimplex() {
        LOG.info("Run Simplex");
        printTableau();
        int c;
        while ((c = enteringVariable()) != -1) {
            pivot(c);
        }
    }

    private void runDualSimplex() {
        LOG.info("Run Dual Simplex");
        printTableau();
        int row;
        while ((row = enteringDualVariable()) != -1) {
            dualPivot(row);
        }
    }

    /**
     * Select a column j with a positive reduced cost:
     * c[j] > 0
     * <p>
     * Make sure
     * - there is an entering basic variable
     * - the entering variable does not violate non-negativity constraint in case a basic variable is degenerate and turns negative
     *
     * @return the first valid entering variable
     */
    private int enteringVariable() {
        for (int j = 2; j < lastColumn; j++) {
            if ((objectiveRow == 0 || artificialVariables[j] == -1) && basicVariables[j] == -1 && t[objectiveRow][j] > 0 && isValidMove(j)) {
                return j;
            }
        }
        return -1;
    }

    /**
     * Select a row i with an infeasible constraint
     * x_i = b_i < 0
     *
     * @return
     */
    private int enteringDualVariable() {
        for (int i = 0; i < m; i++) {
            if (t[2+i][lastColumn] < 0) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Run simplex on original problem.
     */
    private void phase2() {
        LOG.info("Phase II");
        objectiveRow = 1;
        runSimplex();
    }


    /**
     * Sum 2 vectors target & source and replace the target by the result
     *
     * @param target
     * @param source
     */
    private void sumAndReplace(double[] target, double[] source) {
        for (int j = 0; j < target.length; j++) {
            target[j] += source[j];
        }
    }

    private void substractAndReplace(double[] target, double[] source) {
        for (int j = 0; j < target.length; j++) {
            target[j] -= source[j];
        }
    }

    private boolean isValidMove(int c) {
        int r = pivotRow(c);
        if (r == -1) {
            return false;
        }

        for (int i = 0; i < m; i++) {
            if (t[2+i][lastColumn] == 0) { // degenerate basic variable
                if (-(t[2+i][c] / t[2+r][c]) * t[2+r][lastColumn] < 0) { // new value for the basic variable
                    // degenerate basic variable would turn negative
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Select the basic variable in the diagonal of the identity matrix corresponding to row r
     *
     * @param r row
     * @return the basic variable column which value is 1 for the row r
     */
    private int leavingVariable(int r) {
        for (int j = 2; j < lastColumn; j++) {
            if (basicVariables[j] != -1 && t[2+r][j] == 1) {
                return j;
            }
        }
        return -1;
    }

    private void pivot(int column) {
        int row = pivotRow(column);
        if (row == -1) {
            throw new RuntimeException("Can not find pivot for entering variable " + column);
        }

        int lv = leavingVariable(row);
        if (lv == -1) {
            throw new RuntimeException("Can not find leaving variable");
        }

        switchBasicVariable(column, row, lv);
    }

    private void dualPivot(int row) {
        int column = pivotColumn(row);
        if (column == -1) {
            LOG.info("System is infeasible");
            throw new RuntimeException(String.format("Can not find pivot column for row %d", row));
        }
        int lv = leavingVariable(row);
        switchBasicVariable(column, row, lv);
    }

    private void artificialPivot(int lv) {
        int row = basicVariables[lv];

        int ev = enteringOriginalVariable(row);
        if (ev == -1) {
            LOG.info("Can not find entering variable");
            return;
        }

        switchBasicVariable(ev, row, lv);
    }


    private void switchBasicVariable(int column, int row, int lv) {
        // value in the column will be 1
        divideAndReplace(t[2+row], t[2+row][column]);
        for (int i = 0; i < 2 + m; i++) {
            if (i != 2+row) {
                // value in the column will be 0
                diffAndReplace(t[i], t[2+row], t[i][column]);
            }
        }

        basicVariables[column] = row;
        basicVariables[lv] = -1;

        LOG.info("entering variable: {}, leaving variable {} at row {}", column, lv, 2+row);
        printTableau();
    }

    private void printTableau() {
        Arrays.stream(t)
              .forEach(this::printRow);
    }

    private void printRow(double[] row) {
        LOG.info(Arrays.stream(row)
              .mapToObj(d -> String.format("%.2f", d))
              .collect(Collectors.joining(" ")));

    }

    private void divideAndReplace(double[] target, double d) {
        for (int j = 0; j < target.length; j++) {
            target[j] /= d;
        }
    }

    private void diffAndReplace(double[] target, double[] source, double d) {
        for (int j = 0; j < target.length; j++) {
            target[j] -= d * source[j];
        }
    }

    /**
     * Select a row i in column c such as
     * - A[i][j] > 0
     * - b[i] != 0
     * - b[i] / A[i][j] is minimal
     *
     * @return the first valid leaving variable that will increase the objective function
     */
    private int pivotRow(int c) {
        double minRatio = Double.MAX_VALUE;
        int minRow = -1;
        for (int i = 0; i < m; i++) {
            if (t[2+i][c] > 0 && t[2+i][lastColumn] != 0) {
                double ratio = t[2+i][lastColumn] / t[2+i][c];
                if (ratio < minRatio) {
                    minRatio = ratio;
                    minRow = i;
                }
            }
        }
        return minRow;
    }

    private int pivotColumn(int row) {
        double minRatio = Double.MAX_VALUE;
        int minColumn = -1;
        for (int j = 2; j < lastColumn; j++) {
            if (artificialVariables[j] == -1 && basicVariables[j] == -1 && t[objectiveRow][j] < 0 && t[2+row][j] < 0) {
                double ratio = t[objectiveRow][j] / t[2+row][j];
                if (ratio < minRatio) {
                    minRatio = ratio;
                    minColumn = j;
                }
            }
        }
        return minColumn;
    }

    /**
     * @return one artificial variable that is still a basic variable
     */
    private int basicArtificialVariable() {
        for (int j = 2; j < lastColumn; j++) {
            if (artificialVariables[j] != -1 && basicVariables[j] != -1) {
                return artificialVariables[j];
            }
        }
        return -1;
    }

    /**
     * @param r basic variable row
     * @return a non basic variable from the original problem with non 0 value in row r
     */
    private int enteringOriginalVariable(int r) {
        for (int j = 2; j < lastColumn; j++) {
            if (originalVariables[j] != -1 && basicVariables[j] == -1 && t[2+r][j] != 0) {
                return originalVariables[j];
            }
        }
        return -1;
    }

    private double constraintValue(int j) {
        if (basicVariables[j] != -1) {
            return t[2+basicVariables[j]][lastColumn];
        } else {
            return 0;
        }
    }

    /**
     * For Integer solution, add a new constraint
     *
     * x_i + x^{s}_m = b_m
     *
     * @param i
     * @param b
     * @param constraint
     */
    public void addIntegerConstraint(int i, int b, Constraint constraint) {
        extendTableau(i, b, constraint);
        int row = m-1;
        substractAndReplace(t[2 + row], t[2 + basicVariables[2 + i]]);
        if (constraint == Constraint.GREATER) {
            divideAndReplace(t[2 + row], -1);
        }
        runDualSimplex();
        setSolution();
    }

    private void extendTableau(int j, int b, Constraint constraint) {
        LOG.info("Adding constraint x_{} {} {}", j, constraint, b);
        int size = t[0].length+1;
        double[][] t2 = new double[t.length+1][size];
        for (int i = 0; i < t.length; i++) {
            System.arraycopy(t[i], 0, t2[i], 0, size-1);
        }

        // add a new slack variable
        int[] originalVariables2 = new int[size];
        int[] slackVariables2 = new int[size];
        int[] artificialVariables2 = new int[size];
        int[] basicVariables2 = new int[size];
        System.arraycopy(originalVariables, 0, originalVariables2, 0, size-1);
        System.arraycopy(slackVariables, 0, slackVariables2, 0, size-1);
        System.arraycopy(artificialVariables, 0, artificialVariables2, 0, size-1);
        System.arraycopy(basicVariables, 0, basicVariables2, 0, size-1);

        for (int i = 0; i < t.length; i++) {
            t2[i][lastColumn] = 0;
            t2[i][lastColumn+1] = t[i][lastColumn];
        }

        lastColumn++;
        m++;

        t = t2;
        originalVariables = originalVariables2;
        slackVariables = slackVariables2;
        artificialVariables = artificialVariables2;
        basicVariables = basicVariables2;

        // add the new constraint
        t[2+m-1][2+j] = 1;
        t[2+m-1][lastColumn-1] = (constraint == Constraint.LOWER) ? 1 : -1;
        t[2+m-1][lastColumn] = b;

        basicVariables[lastColumn-1] = m-1;
        printTableau();
    }

    public Objective objective() {
        return objective;
    }
}
