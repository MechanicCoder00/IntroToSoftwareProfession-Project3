// PROGRAM TITLE: Traveling Salesman
// FILENAME: Main.java
// EXTERNAL	FILES REQUIRED: City.java, Display.java
// EXTERNAL FILES CREATED:  n/a
// PROGRAMMERS: Ted King, Scott Tabaka, Nick Ray, Kora Baker
//				TED'S EMAIL: 	tmkbf9@mail.umsl.edu
//				SCOTT's EMAIL: 	satk5n@mail.umsl.edu
//				NICK'S EMAIL: 	nrrmbc@mail.umsl.edu
//				KORA'S EMAIL: 	krb8z5@mail.umsl.edu
//				COURSE: CS4500 - 001
//				DATE: 03/04/2020
// PROGRAM DESCRIPTION: This program emulates the classic computer science challenge, the Traveling Salesperson.
//				Problem. Using non-copyrighted code, it takes in two integers, K and N, from an interactive  
//				user, where K is the number of cities to be visited, and N is the length of one side of a square  
//				grid within which the K cities are located. The program randomly selects integer coordinates for 
//				each city (K) as if it lies within a square coordinate system from 0 to N on both axes. The  
//				distance from each city to all other cities is calculated and displayed in matrix format. Then,  
//				using brute force, the shortest route through the cities is calculated and displayed each time a 
//				shortest path is determined.
// RESOURCES USED:  Geeksforgeeks.org -- Brute force algorithm see lines 58-64 for more info
//                  StackOverflow.com
	
import java.util.Scanner;

// Class to obtain necessary user input for calculating shortest distance
public class Main {
	// Create scanner object for user input
	static Scanner input = new Scanner(System.in);

	// Function to find the total distance of a path (copied from display, but returns a double)
	static double totalDistanceOfPath(int k, double[][] arrayOfDistancesMatrix, int[] arrayOfShortestPath) {
		double total = 0;
		int currentPos = 0;

		// Loop through arrayOfDistancesMatrix to calculated a cumulative total distance
		for (int i = 0; i < k; i++) {
			total += arrayOfDistancesMatrix[currentPos][arrayOfShortestPath[i]];
			currentPos = arrayOfShortestPath[i];
		}
		
		return total;
	}

	// Function to convert returned shortestPath array from [0,X,..,0] to
	// [X,..,0]. Length is reduced by 1.
	static int[] fixedShortestPath(int k, int[] shortestPath) {
		int[] fixed = new int[k];
		
		// Loop through shortesPath array to accommodate changing the first element
		for (int i = 1; i < shortestPath.length; i++) {
			fixed[i - 1] = shortestPath[i];
		}
		
		return fixed;
	}

	
	 // Function to calculate the shortest path for TSP (This function is
	 // 	actually designed to find the minimum weight of a Hamiltonian Cycle)
	 //	Found at: https://www.geeksforgeeks.org/travelling-salesman-problem-implementation-
	 // 	using-backtracking. 
	 //		Author: user md1844
	 // 	Contributors: mohit kumar 29, Mithun Kumar, Rajput-Ji, SambhavKumarThakur
	 // 	Modified to accommodate int[]
	static int[] tsp(double[][] distances, boolean[] v, int currPos, int n, int count, int[] possibleShortestPath,
			int[] shortestPath) {

		// If last node is reached and it has a link to the starting node (i.e the source) then
		// 	keep the minimum value out of the distances of possibleShortestPath and shortestPath.
		// 	Finally return to check for more possible values
		if (count == n && distances[currPos][0] > 0) {
			possibleShortestPath[count - 1] = currPos;
			//
			if (totalDistanceOfPath(n + 1, distances,
					possibleShortestPath) < (totalDistanceOfPath(n + 1, distances, shortestPath))) {
				{
					// Loop through possibleShortestPath array to determine the smallest element (shortest path)
					for (int i = 0; (i < n + 1); i++) {
						shortestPath[i] = possibleShortestPath[i];
					}
					
					// Display current shortest path
					Display.displayShortestPath(n, distances, fixedShortestPath(n, shortestPath));
					return shortestPath;
				}
			}
		}

		// BACKTRACKING STEP
		// Loop to traverse the adjacency list of currPos node and increasing the count
		// 	by 1 as well as adding currPos to the possible shortest path array
		for (int i = 0; i < n; i++) {
			if (v[i] == false && distances[currPos][i] > 0) {

				// Mark as visited
				v[i] = true;
				possibleShortestPath[count - 1] = (currPos);
				shortestPath = tsp(distances, v, i, n, count + 1, possibleShortestPath, shortestPath);

				// Mark ith node as unvisited
				v[i] = false;
			}
		}
		return shortestPath;
	}

	// Function to obtain the number of cities, K, from the user
	public static int getK() {

		int k;

		System.out.println("We need the number of cities to be visited");

		// While K is less than 4 or greater than 9, continually ask user for input
		do {
			System.out.print("Enter a number for K between 4-9 (inclusive): ");
			k = input.nextInt();

			// If user types in an invalid number, output an error message before re-prompting
			if (k < 4 || k > 9) {
				System.out.println("\nERROR: K must be between 4-9 (inclusive)! \n");
			}
		} while (k < 4 || k > 9); // Continue looping until k is between 4 and 9 inclusive

		return k;
	}

	// Function to obtain the length of one side of a square grid, N, from the user
	public static int getN() {
		int n;

		System.out.println("Now we need the length of one side of our grid");

		// While n is less than 10 or greater than 30, continually ask user for input
		do {
			System.out.print("Enter a number for N between 10-30 (inclusive): ");
			n = input.nextInt();

			// If user types in an invalid number, output an error message before re-prompting
			if (n < 10 || n > 30) {
				System.out.println("\nERROR: N must be between 10-30 (inclusive)! \n");
			}
		} while (n < 10 || n > 30); // Continue looping until n is between 10 and 30 inclusive

		return n;
	}

    private static void waitForEnter() {    // function to wait for the "Enter" key to be pressed
		input.nextLine(); // Clears the scanner buffer
		System.out.print("Press Enter key to continue...");
		input.nextLine(); // Will wait for "Enter" to be pressed to exit
	}

	// Main function calls corresponding functions to 1) obtain necessary input from the user,
	// 	2) calculate distances and shortest paths, and 3) display city and distance information 
	//	to the output 
	public static void main(String[] args) {
		// Use functions to get n and k values from user
		int k = getK();
		int n = getN();
		System.out.println("N: " + n + "\tK: " + k);
		
		// Setup functions for TSP
		boolean[] v = new boolean[k];
		v[0] = true;
		int[] possibleShortestPath = new int[k + 1];
		int[] finalShortestPath = new int[k];
		
		// Initial Shortest Path is set to A->B->C->...->A
		int[] shortestPath = new int[k + 1];
		for (int i = 0; i < k; i++) {
			shortestPath[i] = i;
		}
		shortestPath[k] = 0;

		//////////////////////////////// *** City Setup *** ////////////////////////////////
		
		System.out.println("Now to setup the cities...");
		// Create new City object array called cityCoordinates
		City cityCoordinates[] = new City[k];
		// Initialize cityCoordinates array with City objects
		for (int i = 0; i < k; i++) {
			cityCoordinates[i] = new City(0, 0);
		}
		// Set cityCoordinates using citySetup return array
		cityCoordinates = cityCoordinates[0].citySetup(k, n, cityCoordinates);
		
		// Calculate distances between cities using calculateDistances return matrix
		System.out.println("Now to calculate the distances...");
		// Create new 2D array called distances within which to store distances between cities
		double distances[][] = new double[k][k];
		distances = cityCoordinates[0].calculateDistances(cityCoordinates, k, distances);
		
		/////////////////// *** Display relevant information to output *** ///////////////////
		
		System.out.println("Now to find the shortest possible path...");
	
		// Determine shortest path between cities
		shortestPath = tsp(distances, v, 0, k, 1, possibleShortestPath, shortestPath);
		
		// Set finalShortest Path to the return value of fixedShortesPath function
		finalShortestPath = fixedShortestPath(k, shortestPath);
		
		// Call function to display a distance matrix, a city map locations matrix, 
		//	a list of city coordinates, and a shortest path matrix
		Display.displaySolution(k, n, finalShortestPath, cityCoordinates, distances);
		
		waitForEnter();
	}
}
