package io.github.tomatsch87;

public interface FreightSolver {

  int[][] exampleA = new int[][] {
		{ 10, 5, 7, 11 },
		{  7, 6, 2, 1 }
	};

  int[][] exampleB = new int[][] {
		{ 10, 5, 7, 11, 13, 1, 7, 11, 13, 19, 19, 9, 8, 2, 7, 31 },
		{  7, 6, 1, 1, 1, 4, 11, 20, 3, 7, 8, 9, 16, 19, 100, 3 }
	};

  int[][] exampleC = new int[][] {
		{ 21, 52, 92, 1, 97, 26, 8, 42, 82, 46, 19, 76, 30, 79, 17, 16, 41, 24, 25, 66 },
		{ 86, 40, 34, 27, 2, 4, 17, 74, 96, 11, 100, 69, 99, 64, 10, 23, 90, 70, 45, 87 }
	};
  
  /**
   * Calculates the optimal freight distribution with a procedure from its implementing class
   * @param selected Array that indicates selected freight with 1, everything else is 0
   * @param weights
   * @param values
   * @param capacity Maximum sum of all weights allowed for this freight less the selected weights
   * @param objIndex
   * @return The sum of all values from the selected distribution of weights
   */
  int backpack(int[] selected, int[] weights, int[] values, int capacity, int objIndex);

  /**
   * Calculates the optimal freight distribution with a procedure from its implementing class
   * @param selected Array that indicates selected freight with 1, everything else is 0
   * @param weights
   * @param values
   * @param capacity Maximum sum of all weights allowed for this freight less the selected weights
   * @param objIndex
   * @param bestSolve Saves the overall best state in form of a selected state in this parameter
   * @return The best solution in form of a selected state
   */
  int[] backpack(int[] selected, int[] weights, int[] values, int capacity, int objIndex, int[] bestSolve);

  /**
   * Calculates the sum of the selected values
   * @param selected Array that indicates selected freight with 1, everything else is 0
   * @param values
   * @return The sum of the selected values
   */
  default int sumSelected(int[] selected, int[] values){
    int result = 0;
    
    //Sum all values with selected position equal to 1
    for(int i = 0; i < selected.length; ++i){
      if(selected[i] == 1){
        result += values[i]; 
      }
    }
    return result;
  }
  
  /**
   * Formats the freight solve from method backpack() in the console terminal 
   * @param selected Array that indicates selected freight with 1, everything else is 0
   * @param weights
   * @param values
   * @param capacity Maximum sum of all weights allowed for this freight less the selected weights
   * @param objIndex
   */
  default void runBackpack(int[] selected, int[] weights, int[] values, int capacity, int objIndex){
    int[] bestSolve = new int[selected.length];
    //Save the state with the best sum of all values from the selected distribution of weights
    int[] solve = backpack(selected, weights, values, capacity, objIndex, bestSolve);

    System.out.println("The sum of all values equals >" + sumSelected(solve, values) + "< Units");

    for(int i = 0; i < selected.length; ++i){

      System.out.format("%3d: selected[%2d] =  %3d, weights[%2d] =  %3d, values[%2d] =  %3d"+"\n",
        i, i, solve[i], i, weights[i], i, values[i]);

    }
    System.out.println("The used capacity equals >" + sumSelected(solve, weights) + "< Units");
  }

  /**
   * Generate a worst-case sample of weights and values to be used for backtracking runtime tests
   * @param n Size of the distribution array to be created
   * @return Worst-case sample of weights and values for backtracking runtime
   */
  private static int[][] createSample(int n){
    int[][] result = new int[2][n]; 
    //Set last weight of the distribution and its value to 1
    //With this sample backtracking always needs to search the whole solution space
    result[0][n-1] = 1;
    result[1][n-1] = 1;

    return result;
  }

  /**
   * Performs a runtime test with a variaty of lengths for random weight and value samples
   * Formats the results for the runtime test on the terminal
   */
  default void runtimeTest(){
    int length[] = {20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30};

    for(int n:length){
      int sample[][] = createSample(n);
      int selected[] = new int[n];

      //Chronometry
      long start = System.nanoTime();
      backpack(selected, sample[0], sample[1], 1, 0);
      long end = System.nanoTime();
      long microseconds = (end-start)/1000;

      //Out on terminal
      System.out.format("Solved backpack for NOBJS = %5d in : ", sample[0].length);
      //Converting from seconds and milliseconds: 1 microsecond = 1*10^6 secondes, 1 microsecond = 1*10^3 milliseconds
      System.out.format("%4d Seconds, %02d Milliseconds\n", microseconds/1000000, ((microseconds/1000)%1000)/10);
    }
  }
}