import java.util.Random;

// Class to randomly assign x/y coordinates within N (K times) -> use integers
// and to calculate & store edges in matrix -> use doubles
public class City {
	int x;
	int y;

	// Function to get value of x
	int getX() {
		return this.x;
	}

	// Function to get value of y
	int getY() {
		return this.y;
	}

	// // Function to set value of x
	void setX(int x) {
		this.x = x;
	}

	// Function to set value of y
	void setY(int y) {
		this.y = y;
	}

	// City Constructor 
	public City(int x, int y) {
		this.x = x;
		this.y = y;
	}

	// City Copy Constructor
	City(City city) {
		this.x = city.x;
		this.y = city.y;
	}

	// Function to set up randomized city coordinates
	public City[] citySetup(int k, int n, City cityCoordinates[]) {
		Random rand = new Random();

		// Booleans to help track if we've used an (x,y) coordinate already
		boolean isSameX;
		boolean isSameY;

		int randX;
		int randY;

		// Create a 2D array within which to store used (x,y) values
		int usedCoordinates[][] = new int[k - 1][2]; 

		// Loop through values from 0-k to create unused x and y pairs
		for (int i = 0; i < k; i++) {
			// Generates random numbers until an unused pair is found
			do {
				isSameX = false;
				isSameY = false;

				// Generate random x/y positions
				randX = rand.nextInt(n);
				randY = rand.nextInt(n);

				// Loop through usedCoordiantes array to see if this pair of x/y 
				//	coordinates has been used
				for (int j = 0; j < i; j++) {
					// If the random x value is found within usedCoordinates array
					//	set isSameX to true;
					if (randX == usedCoordinates[j][0]) {
						isSameX = true;
					}
					// If the random y value is found within usedCoordinates array
					//	set isSameY to true;
					if (randY == usedCoordinates[j][1]) {
						isSameY = true;
					}
				}
			} while (isSameX && isSameY); // continue looping until isSameX and isSameY are both false

			// Create new city and assign validated random x/y positions
			City city = new City(randX, randY);

			// If i value does not equal number of cities (k) minus 1
			if (i != k - 1) {
				// Place used x values in usedCoordinates array
				usedCoordinates[i][0] = randX; 
				// Place used y values in usedCoordinates array
				usedCoordinates[i][1] = randY; 
			}

			// Assign the city to the cityCoordinates array using copy constructor
			cityCoordinates[i] = new City(city);
		}

		return cityCoordinates;
	}

	// Function to calculate distances between cities
	public double[][] calculateDistances(City[] cityCoordinates, int k, double[][] distances) {
		double dist; // Holds distance between two current cities

		// Loop through values of k
		for (int i = 0; i < k; i++) {
			// Get first pair of x/y coordinates
			double cityOneX = cityCoordinates[i].getX();
			double cityOneY = cityCoordinates[i].getY();

			// Loop through values of k
			for (int j = i; j < k; j++) {
				// If i and j are equal, set distances values to 0
				//	because it is comparing the distance from a city to itself
				if (i == j) {
					distances[i][j] = 0;
					continue;
				}

				// Get second pair of x/y coordinates
				double cityTwoX = cityCoordinates[j].getX();
				double cityTwoY = cityCoordinates[j].getY();

				///////////// Distance Formula: sqrt( (x2 - x1)^2 + (y2 - y1)^2 ) /////////////
				
				// Store expressions of the Distance Formula (above)
				double xDifferenceSquared = Math.pow((cityTwoX - cityOneX), 2);
				double yDifferenceSquared = Math.pow((cityTwoY - cityOneY), 2);

				// Set dist to the distance as calculated using Distance Formula (above)
				dist = Math.sqrt(xDifferenceSquared + yDifferenceSquared);

				// Assign the value in the [i][j] and [j][i] entries of the distances 2D array.
				//	Because this distance is the same between city A and city B as it is between
				// 	City B and City A, the distance between cities A and B need only be calculated once
				distances[i][j] = dist;
				distances[j][i] = dist;
			}
		}
		
		return distances;
	}

}