package io.github.tomatsch87;

class Backtracking implements FreightSolver {

  @Override
  /**
   * Calculates the optimal freight distribution with a procedure from its implementing class
   * @param selected Array that indicates selected freight with 1, everythin else is 0
   * @param weights
   * @param values
   * @param capaciy Maximum sum of all weights allowed for this freight less the selected weights
   * @param objIndex
   * @return The sum of all values from the selected distribution of weights
   */
  public int backpack(int[] selected, int[] weights, int[] values, int capacity, int objIndex) {
    int maxValue = 0;
    //Calls private overloaded helper method
    return backpack(selected, weights, values, capacity, objIndex, maxValue);
  }

  /**
   * Overloaded helper method for int backpack()
   * @param maxValue Stores the best sum of weights allowed for the given capacity while backtracking
   */
  private int backpack(int[] selected,int[] weights, int[] values, int capacity, int objIndex, int maxValue) {
    //Local variable to store backtracking result
    int solve = 0;
    //Break recursion if capacity reaches 0
    if(capacity == 0){
      return sumSelected(selected, values);
    }
    //Break recursion if capacity below 0 and return invalid state
    if(capacity < 0){
      return -1;
    }
    //Break recursion if the last state is reached
    if(objIndex == selected.length){
      return sumSelected(selected, values);
    }
    //Initialize the two new states following the selected state
    int[][] states = new int[2][selected.length];

    states[0] = selected.clone();
    states[1] = selected.clone();
    //Create two new states at objIndex
    states[0][objIndex] = 1;
    states[1][objIndex] = 0;
    /*Backtracking: For each state calculate the solve recursively by
      trying all following states throughout the whole solution space
      and save the solve if its value is greater than maxValue*/
    for(int[] state:states){
      solve = backpack(state, weights, values, state[objIndex]==0?capacity:capacity-weights[objIndex],
        objIndex+1, maxValue);
        
      if(solve > maxValue){
        maxValue = solve;
      }
    }
    return maxValue;
  }

  @Override
  /**
   * Calculates the optimal freight distribution with a procedure from its implementing class
   * @param selected Array that indicates selected freight with 1, everythin else is 0
   * @param weights
   * @param values
   * @param capacity Maximum sum of all weights allowed for this freight less the selected weights
   * @param objIndex
   * @param bestSolve Saves the overall best state in form of a selected state in this parameter
   * @return The best solution in form of a selected state
   */
  public int[] backpack(int[] selected,int[] weights, int[] values, int capacity, int objIndex, int[] bestSolve) {
    //Local variable to store backtracking result
    int[] solve = selected.clone();
    //Break recursion if capacity reaches 0
    if(capacity == 0){
      return selected;
    }
    //Break recursion if capacity below 0 and return invalid state
    if(capacity < 0){
      for(int k = 0; k < selected.length; ++k){
        selected[k] = 0;
      }
      return selected;
    }
    //Break recursion if the last state is reached
    if(objIndex == selected.length){
      return selected;
    }
    //Initialize the two new states following the selected state
    int[][] states = new int[2][selected.length];

    states[0] = selected.clone();
    states[1] = selected.clone();
    //Create two new states at objIndex
    states[0][objIndex] = 1;
    states[1][objIndex] = 0;
    /*Backtracking: For each state calculate the solve recursively by
      trying all following states throughout the whole solution space
      and save the solve if its sum of values is greater than the sum of bestSolve*/
    for(int[] state:states){
      solve = backpack(state, weights, values, state[objIndex]==0?capacity:capacity-weights[objIndex],
        objIndex+1, bestSolve);
        
      if(sumSelected(solve, values) > sumSelected(bestSolve, values)){
        bestSolve = solve.clone();
      }
    }
    return bestSolve;
  }
  
  public static void main(String[] args) {
    //Backtracking object for tests
    Backtracking bt = new Backtracking();

    /*Basic console tests for all methods in class Backtracking.java
      using the default methods and examples provided through FreightSolver.java*/

    //Creating selected states:
    int[] a = new int[exampleA[0].length];
    int[] b = new int[exampleB[0].length];
    int[] c = new int[exampleC[0].length];

    //Running method backpack through runBackpack from FreightSolver.java
    System.out.println("The correct solution for example A is:\nThe sum of all values equals >15< Units");
    System.out.println("The used capacity equals >22< Units"+"\n");
    System.out.println("Solution from our backtracking implementation: ");
    bt.runBackpack(a, exampleA[0], exampleA[1], 30, 0);
    System.out.println("\n");
    System.out.println("The correct solution for example B is:\nThe sum of all values equals >159< Units");
    System.out.println("The used capacity equals >29< Units"+"\n");
    System.out.println("Solution from our backtracking implementation: ");
    bt.runBackpack(b, exampleB[0], exampleB[1], 30, 0);
    System.out.println("\n");
    System.out.println("The correct solution for example C is:\nThe sum of all values equals >230< Units");
    System.out.println("The used capacity equals >49< Units"+"\n");
    System.out.println("Solution from our backtracking implementation: ");
    bt.runBackpack(c, exampleC[0], exampleC[1], 50, 0);
    System.out.println("\n");

    /*Perform a runtime test with backtracking for a worst-case distribution of weights and values:
      Only the last weight and value pair is set to 1. In this case backtracking always needs to search
      the whole solutions space and cant break the recursion early*/
    bt.runtimeTest();
  }
}
