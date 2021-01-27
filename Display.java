// Class to display maps and relevant maps information to the output screen
class Display {
	// Array for labeling cities with alphabetic identifiers
	private static String[] cityLetter = { "A", "B", "C", "D", "E", "F", "G", "H", "I" };

	// Function to change elements of the map
	private static void modifyDisplayMap(String label, int x, int y, String[][] worldArray) {
		worldArray[x][y] = label;
	}

	// Function to display the current map
	private static void displayDisplayMap(int k, String[][] worldArray) {
		// Loop through rows of the current map
		for (int i = 0; i < k; i++) 
		{
			// Loop through column of the current map
			for (int j = 0; j < k; j++) 
			{
				// Print the map to the output
				System.out.print(worldArray[j][i]);
				System.out.print("  ");
			}
			System.out.println();
		}
		System.out.println();
	}

	// Function to create a blank map 
	private static void initializeDisplayMap(int k, String[][] worldArray) {
		// Loop through rows 
		for (int i = 0; i < k; i++) 
		{
			// Loop through columns
			for (int j = 0; j < k; j++) 
			{
				// Fill each element of the empty map array with a "."
				worldArray[j][i] = ".";
			}
		}
	}

	// Function to display the map with the location of each city
	private static void displayMapCityLocations(int k, int n, City[] city) {
		// Create a new 2D array called worldArray
		String[][] worldArray = new String[n][n];

		// Call function to initialize a map
		initializeDisplayMap(n, worldArray);

		// Loop through values of k
		for (int i = 0; i < k; i++) {
			// Call function to modify the map with city alphabetic identifiers using validated (x,y) coordinates
			modifyDisplayMap(cityLetter[i], city[i].x, city[i].y, worldArray);
		}

		// Display the map to the output
		System.out.println("City Map Locations");
		displayDisplayMap(n, worldArray);
	}

	// Function to display the map of cities with the order of the shortest path
	private static void displayMapShortestPath(int k, int n, City[] city, int[] shortestPath) {
		// Create a new 2D array called worldArray
		String[][] worldArray = new String[n][n];
		
		// Call function to initialize a map
		initializeDisplayMap(n, worldArray);

		// Call function modify the map using validated (x,y) coordinates
		modifyDisplayMap("0", city[0].x, city[0].y, worldArray);

		// Loop through values of k
		for (int i = 1; i < k; i++) {
			// Call function to modify the map with shortest path distances
			modifyDisplayMap(Integer.toString(i), city[shortestPath[i - 1]].x, city[shortestPath[i - 1]].y, worldArray);
		}

		// Display the map showing the shortest distance between cities to the output
		System.out.println("Shortest path through cities");
		displayDisplayMap(n, worldArray);
	}

	// Function to display coordinates of cites
	public static void displayCityCoordinates(int k, City[] city) {
		System.out.println("City Coordinates");
		// Loop through values of k
		for (int i = 0; i < k; i++) {
			// Display each city with its corresponding coordinates to the output
			System.out.println(cityLetter[i] + "(" + city[i].x + "," + city[i].y + ")");
		}
		System.out.println();
	}

	// Function to display distance matrix
	public static void displayArrayOfDistances(int k, double[][] arrayOfDistancesMatrix) {
		System.out.println("\nDistance Matrix");
		String str;

		// Loop through values of k
		for (int i = 0; i < k; i++) {
			// Display each city letter above each column
			str = String.format("%6s", cityLetter[i]); 
			System.out.print(str);
		}
		System.out.println();

		// Loop through rows
		for (int i = 0; i < k; i++)
		{
			// Display each city letter to the left of each row
			str = String.format("%-1s", cityLetter[i]); 
			System.out.print(str);

			// Loop through columns
			for (int j = 0; j < k; j++) // columns
			{
				// Display value of distance array rounded to 1 decimal place
				str = String.format("%6.1f", arrayOfDistancesMatrix[i][j]); 
				System.out.print(str);
			}
			System.out.println();
		}
		System.out.println();
	}

	// Function for calculating and displaying the total distance of a path
	private static void displayTotalDistanceOfPath(int k, double[][] arrayOfDistancesMatrix,
			int[] arrayOfShortestPath) {
		double total = 0;
		int currentPos = 0;

		// Loop through values of k
		for (int i = 0; i < k; i++) {
			// Determine cumulative distance between cities
			total += arrayOfDistancesMatrix[currentPos][arrayOfShortestPath[i]];
			currentPos = arrayOfShortestPath[i];
		}

		// Format output of total (float) distance value to one decimal place
		String str = String.format("%.1f", total);
		System.out.println("Total Distance: " + str);
	}

	// Function for displaying all final information
	static void displaySolution(int k, int n, int[] arrayOfShortestPath, City[] arrayOfCityObjects,
			double[][] arrayOfDistancesMatrix) {
		// Call corresponding functions to display distance array, Map of cities, 
		// 	city coordinates, and map of shortest path
		displayArrayOfDistances(k, arrayOfDistancesMatrix);
		displayMapCityLocations(k, n, arrayOfCityObjects);
		displayCityCoordinates(k, arrayOfCityObjects);
		displayMapShortestPath(k, n, arrayOfCityObjects, arrayOfShortestPath);
	}

	// Function for displaying shortest path with total distance
	static void displayShortestPath(int k, double[][] arrayOfDistancesMatrix, int[] arrayOfShortestPath) {

		System.out.print("Order of current shortest path: ");
		System.out.print("A");
		// Loop through values of k
		for (int i = 0; i < k; i++) {
			// Display -> followed by a the next closest city's alphabetic identifier 
			System.out.print("->");
			System.out.print(cityLetter[arrayOfShortestPath[i]]);
		}
		System.out.print("    ");

		// Call function to display total distance of shortest path
		displayTotalDistanceOfPath(k, arrayOfDistancesMatrix, arrayOfShortestPath);
	}
}
