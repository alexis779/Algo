package linear.programming;

/**
 * Find a solution x of optimization problem:
 * 
 * Minimize
 *   c.T * x
 *   
 * with constraints
 *   A * x = b
 */
public class Simplex {
	/**
	 * 
	 */
	private double[] c; // objective function of the original problem
	private double[][] A;
	private double[] b;
	/**
	 * number of original variables
	 */
	int n;
	/**
	 * number of artificial variables
	 */
	int m;
	/**
	 * tableau
	 */
	private double[][] t;
	/**
	 * list of variables that constitute the basis
	 */
	private boolean[] basicVariables;
	/**
	 * row in the tableau corresponding to the objective function
	 */
	private int objectiveRow;
	/**
	 * column in the tableau corresponding to the last original variable
	 */
	private int lastVariable;
	
	public Simplex(double[] c, double[][] A, double[] b) {
		this.c = c;
		this.A = A;
		this.b = b;
		standardize();
	}

	/**
	 * @return the basic variable tuple after running full-tableau method
	 */
	public double[] solution() {
		runFullTableauMethod();
		
		double[] basicFeasibleSolution = new double[n];
		for (int j = 2; j < 2+n; j++) {
			basicFeasibleSolution[j-2] = constraintValue(j);
		}
		return basicFeasibleSolution;
	}
	
	/**
	 * After running the method, this returns the minimum value.
	 * @return the object function value
	 */
	public double objectiveFunction() {
		return -t[objectiveRow][2+n+m];
	}

	private void runFullTableauMethod() {
		initTableau();
		phase1();
		phase2();
	}
	
	private void initTableau() {
		t = new double[2+m][n+m+3];
		initPhase1Goal(t[0]);
		initPhase2Goal(t[1]);
		initConstraints();
		initIdentity();
		initConstants();
		printTableau();
		priceOut();
		initBasicVariables();
	}
	
	/**
	 * Init objective function of the auxiliary problem as the sum of the artificial variables
	 * @param row
	 */
	private void initPhase1Goal(double[] row) {
		row[0] = 1;
		row[1] = 0;
		// coefficients of the original variables
		for (int j = 0; j < n; j++) {
			row[2+j] = 0;
		}
		// coefficients of the artificial variables
		for (int j = 0; j < m; j++) {
			row[2+n+j] = 1;
		}
		row[2+n+m] = 0;
	}
	
	/**
	 * Init objective function of the original problem
	 * @param row
	 */
	private void initPhase2Goal(double[] row) {
		row[0] = 0;
		row[1] = 1;
		for (int j = 0; j < n; j++) {
			row[2+j] = c[j];
		}
		for (int j = 0; j < m; j++) {
			row[2+n+j] = 0;
		}
		row[2+n+m] = 0;
	}
	
	private void initConstraints() {
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				t[2+i][2+j] = A[i][j];
			}
		}
	}
	private void initIdentity() {
		for (int i = 0; i < m; i++) {
			t[2+i][2+n+i] = 1;
		}
	}
	private void initConstants() {
		for (int i = 0; i < m; i++) {
			t[2+i][2+n+m] = b[i];
		}
	}
	/**
	 * Null out the coefficients of the artificial variables
	 */
	private void priceOut() {
		for (int i = 0; i < m; i++) {
			sumAndReplace(t[0], t[2+i]);
		}
	}
	/**
	 * Init the basis with the list of artificial variables.
	 */
	private void initBasicVariables() {
		basicVariables = new boolean[t[0].length];
		for (int i = 0; i < m; i++) {
			basicVariables[2+n+i] = true;
		}
	}
	/**
	 * Run simplex on the auxiliary problem
	 * Min x1 + ... + xp
	 * where xi, i = [1..p] are the artificial variables
	 * 
	 * then drive artificial variables out of the basis if any
	 */
	private void phase1() {
		System.out.println("Phase I");
		objectiveRow = 0;
		lastVariable = 2+n+m;
		runSimplex();
		
		System.out.println("Driving out artificial variables");
		// drive out basic artificial variables
		int c;
		while ((c = basicArtificialVariable()) != -1) {
			artificialPivot(c);
		}
	}
	
	private void runSimplex() {
		int c;
		while ((c = enteringVariable()) != -1) {
			pivot(c);
		}
	}
	/**
	 * Run simplex on original problem.
	 */
	private void phase2() {
		System.out.println("Phase II");
		objectiveRow = 1;
		lastVariable = 2+n; // discard artificial variables
		runSimplex();
	}
	

	/**
	 * Sum 2 vectors target & source and replace the target by the result
	 * @param target
	 * @param source
	 */
	private void sumAndReplace(double[] target, double[] source) {
		for (int j = 0; j < target.length; j++) {
			target[j] += source[j];
		}
	}
	
	/**
	 * Convert to the standard form
	 * - Minimize c.T * x
	 * - A*x = b
	 * - x >= 0
	 * - b >= 0 
	 */
	private void standardize() {
		validate();
	}
	
	/**
	 * Sanity check of dimensions of c, A and b 
	 */
	private void validate() {
		if (c.length != A[0].length) {
			log("Invalid number of variables");
		}
		this.n = c.length;
		
		if (A.length != b.length) {
			log("Invalid number of constraints");
		}
		this.m = b.length;
	}
	
	private void log(String message) {
		System.out.println(message);
	}

	/**
	 * Select a column j with a negative reduced cost:
	 * c[j] > 0
	 * 
	 * Make sure
	 * - there is an entering basic variable
	 * - the entering variable does not violate non-negativity constraint in case a basic variable is degenerate and turns negative
	 * 
	 * @return the first valid entering variable
	 */
	private int enteringVariable() {
		for (int j = 2 ; j < lastVariable; j++) {
			if (! basicVariables[j] && t[objectiveRow][j] < 0 && isValidMove(j) ) {
				return j;
			}
		}
		return -1;
	}
	
	private boolean isValidMove(int c) {
		int r = pivotRow(c);
		if (r == -1) {
			return false;
		}
		
		for (int i = 2; i < 2+m; i++) {
			if (t[i][2+n+m] == 0) { // degenerate basic variable
				if (- (t[i][c] / t[r][c]) * t[r][2+n+m] < 0) { // new value for the basic variable
					// degenerate basic variable would turn negative
					return false;
				}
			}
		}

		return true;
	}

	/**
	 * Select the basic variable in the diagonal of the identity matrix corresponding to row r
	 * @param r row
	 * @return the basic variable column which value is 1 for the row r
	 */
	private int leavingVariable(int r) {
		for (int j = 2 ; j < lastVariable; j++) {
			if (basicVariables[j] && t[r][j] == 1) {
				return j;
			}
		}
		return -1;
	}
	
	private void pivot(int ev) {
		int row = pivotRow(ev);
		if (row == -1) {
			log("Can not find pivot for entering variable " + ev);
			System.exit(1);
			return;
		}
		
		int lv = leavingVariable(row);
		if (lv == -1) {
			log("Can not find leaving variable");
			return;
		}
		
		switchBasicVariable(ev, row, lv);
	}
	
	private void artificialPivot(int lv) {
		int row = basicVariableRow(lv);
		if (row == -1) {
			log("Can not find pivot");
			return;
		}
		
		int ev = enteringOriginalVariable(row);
		if (ev == -1) {
			log("Can not find entering variable");
			return;
		}

		switchBasicVariable(ev, row, lv);
	}

	
	private void switchBasicVariable(int ev, int row, int lv) {
		// value in the column will be 1
		divideAndReplace(t[row], t[row][ev]);
		for (int i = 0; i < 2+m; i++) {
			if (i != row) {
				// value in the column will be 0
				diffAndReplace(t[i], t[row], t[i][ev]);
			}
		}
		basicVariables[ev] = true;
		basicVariables[lv] = false;
		
		System.out.format("entering variable: %d, leaving variable %d at row %d%n", ev, lv, row);
		//printBasicVariables();
		printTableau();
	}
	
	
	public void printRow(double[] row) {
		for (int j = 0; j < row.length; j++) {
			System.out.format("%8.3f", row[j]);
			//System.out.format("%4.0f", row[j]);
		}
		System.out.println();
	}
	private void printTableau() {
		for (int i = 0; i < t.length; i++) {
			printRow(t[i]);
		}
	}	
	private void printBasicVariables() {
		for (int j = 0; j < basicVariables.length; j++) {
			System.out.format("%8d", basicVariables[j] ? 1 : 0);
			//System.out.format("%4d", j-1);
		}
		System.out.println();
		for (int j = 0; j < basicVariables.length; j++) {
			System.out.format("%8d", basicVariables[j] ? 1 : 0);
			//System.out.format("%4d", basicVariables[j] ? 1 : 0);
		}
		System.out.println();
	}

	private void divideAndReplace(double[] target, double d) {
		for (int j = 0; j < target.length; j++) {
			target[j] /= d;
		}
	}
	
	private void diffAndReplace(double[] target, double[] source, double d) {
		for (int j = 0; j < target.length; j++) {
			target[j] -= d*source[j];
		}
	}
	
	/**
	 * Select a row i in column c such as
	 * - A[i][j] > 0
	 * - b[i] != 0
	 * - b[i] / A[i][j] is minimal
	 * @return the first valid leaving variable that will reduce the objective function
	 */
	private int pivotRow(int c) {
		double minRatio = Double.MAX_VALUE;
		int minRow = -1;
		for (int i = 2; i < 2+m; i++) {
			if (t[i][c] > 0 && t[i][2+n+m] != 0) {
				double ratio = t[i][2+n+m] / t[i][c];
				if (ratio < minRatio) {
					minRatio = ratio;
					minRow = i;
				}
			}
		}
		return minRow;
	}
	
	/**
	 * @return one artificial variable that is still a basic variable
	 */
	private int basicArtificialVariable() {
		for (int i = 2+n; i < 2+n+m; i++) {
			if (basicVariables[i]) {
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * @param c basic variable column
	 * @return the row that has 1 as value in the column c
	 */
	private int basicVariableRow(int c) {
		for (int i = 2; i < 2+m; i++) {
			if (t[i][c] == 1) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * @param r basic variable row
	 * @return a non basic variable from the original problem with a non 0 value in row r
	 */
	private int enteringOriginalVariable(int r) {
		for (int j = 2; j < 2+n; j++) {
			if (! basicVariables[j] && t[r][j] != 0) {
				return j;
			}
		}
		return -1;
	}
	
	private double constraintValue(int j) {
		if (basicVariables[j]) {
			return t[basicVariableRow(j)][2+n+m];
		} else {
			return 0;
		}
	}
}
