package io.github.tomatsch87;

class Greedy {
  // for testing:
  static int[][] exampleA = new int[][] {
      { 10, 5, 7, 11 },
      {  7, 6, 2, 1 }
  };

  static int[][] exampleB = new int[][] {
      { 10, 5, 7, 11, 13, 1, 7, 11, 13, 19, 19, 9, 8, 2, 7, 31 },
      {  7, 6, 1, 1, 1, 4, 11, 20, 3, 7, 8, 9, 16, 19, 100, 3 }
  };

  static int[][] exampleC = new int[][] {
      { 21, 52, 92, 1, 97, 26, 8, 42, 82, 46, 19, 76, 30, 79, 17, 16, 41, 24, 25, 66 },
      { 86, 40, 34, 27, 2, 4, 17, 74, 96, 11, 100, 69, 99, 64, 10, 23, 90, 70, 45, 87 }
  };

  /**
   * Formats the freight solve from method backpack() in the console terminal
   * @param selected Array that indicates selected freight with 1, everything else is 0
   * @param weights
   * @param values
   * @param capacity Maximum sum of all weights allowed for this freight less the selected weights
   * @param
   */
  void runBackpack(int[] selected, int[] weights, int[] values, int capacity){
    // init an array for your result from the array returning method "backpackArray"
    int[] solve = backpackArray(selected, weights, values, capacity);
    // print out the sum of the sleted value as String
    System.out.println("The sum of all values equals >" + sumSelected(solve, values) + "< Units");
    // iterate through your solve array
    for(int i = 0; i < solve.length; ++i){
    // print out in the required form
      System.out.format("%3d: selected[%2d] =  %3d, weights[%2d] =  %3d, values[%2d] =  %3d"+"\n",
          i, i, solve[i], i, weights[i], i, values[i]);

    }
    // print out the used capacity as a String
    System.out.println("The used capacity equals >" + sumSelected(solve, weights) + "< Units");
  }

  /**
   * Calculates the sum of the selected values
   * @param selected Array that indicates selected freight with 1, everything else is 0
   * @param values
   * @return The sum of the selected values
   */
  int sumSelected(int[] selected, int[] values){
    int result = 0;

    // Sum all values with selected position equal to 1
    for(int i = 0; i < selected.length; ++i){
      if(selected[i] == 1){
        result += values[i];
      }
    }
    return result;
  }

  /**
   * method for searching optimal Value (index of value) for Greedy algorithm
   * @param weights
   * @param values
   * @param capacity
   * @param selected Array that indicates selected freight with 1, everything else is 0
   * @return
   */
  int optimalValue(int[] weights, int[] values, int capacity, int[] selected){
    // fallback value for max, if no element is small enough to fit in the remaining capacity
    int max = -1;
    int maxIndex = -1;
    // iterate trough every element of weights
    for (int i = 0; i < weights.length; i++) {
      // check if this element has the optimal capacity in reference to its weight
      if (weights[i] <= capacity && values[i] > max && selected[i] == 0 && capacity != 0) {
        // save the element position as new max
        max = values[i];
        maxIndex = i;
      }
    }
    // return the optimal value index
    return maxIndex;
  }

  /**
   * Calculates the optimal freight distribution with a procedure from its implementing class
   * @param selected Array that indicates selected freight with 1, everythin else is 0
   * @param weights
   * @param values
   * @param capacity Maximum sum of all weights allowed for this freight less the selected weights
   * @return The sum of all values from the selected distribution of weights
   */
  public int backpack(int[] selected, int[] weights, int[] values, int capacity){
  //Local variable to store greedy result
    int solve = 0;
    while (optimalValue(weights, values, capacity, selected) != -1){
      int res = optimalValue(weights, values, capacity, selected);
      // set the index, where you found an optimalValue for to 1
      selected[res] = 1;
      // increase your solve variable by the new optimal value that you have found
      solve += values[res];
      // decrease your capacity by the weight of your founded value, for your upcoming search with the new less capacity
      capacity -= weights[res];
    }
    // return your solve value as result
    return solve;
  }

  /**
   * Calculates the optimal freight distribution with a procedure from its implementing class
   * @param selected Array that indicates selected freight with 1, everything else is 0
   * @param weights
   * @param values
   * @param capacity Maximum sum of all weights allowed for this freight less the selected weights
   * @return The sum of all values from the selected distribution of weights
   */
  public int[] backpackArray(int[] selected, int[] weights, int[] values, int capacity) {
    // as long as you can find an optimal value (return value not equal -1)
    while (optimalValue(weights, values, capacity, selected) != -1){
      // set local variable res to return value of optimalValue to wokr with it later
      int res = optimalValue(weights, values, capacity, selected);
      // set the index, where you found an optimalValue for to 1
      selected[res] = 1;
      // decrease your capacity by the weight of your founded value, for your upcoming search with the new less capacity
      capacity -= weights[res];
    }
    // return your array of selected positions as result
    return selected;
  }

  public static void main(String[] args) {
    // Greedy object for tests
    Greedy gr = new Greedy();

    // Basic console tests for all methods in class Greedy.java

    // Creating selected states:
    int[] a = new int[exampleA[0].length];
    int[] b = new int[exampleB[0].length];
    int[] c = new int[exampleC[0].length];

    // Running method backpack through runBackpack
    System.out.println("The correct solution for example A is:\nThe sum of all values equals >15< Units");
    System.out.println("The used capacity equals >22< Units"+"\n");
    System.out.println("Solution from our greedy implementation: ");
    gr.runBackpack(a, exampleA[0], exampleA[1], 30);
    System.out.println("\n");
    System.out.println("The correct solution for example B is:\nThe sum of all values equals >159< Units");
    System.out.println("The used capacity equals >29< Units"+"\n");
    System.out.println("Solution from our greedy implementation: ");
    gr.runBackpack(b, exampleB[0], exampleB[1], 30);
    System.out.println("\n");

    System.out.println("The correct solution for example C is:\nThe sum of all values equals >230< Units");
    System.out.println("The used capacity equals >49< Units"+"\n");
    System.out.println("Solution from our greedy implementation: ");
    gr.runBackpack(c, exampleC[0], exampleC[1], 50);
    System.out.println("Is this case the greedy algorithm doesnt find to optimal solution.\nThis is the intended behaviour!");
    System.out.println("\n");
  }
}
