import java.util.Scanner;

public class WFC {
	// declare the constants and variables
	public static final int MAX_LESSON_CUSTOMERS = 5;
	public static final int NUM_DAYS = 8;
	public static final int NUM_TYPES_OF_LESSONS = 7;
	private static final int NUM_WEEKENDS = 0;

	public static int[][] class_timetable = new int[NUM_DAYS][NUM_TYPES_OF_LESSONS];
	public static int[][] lesson_customers = new int[NUM_DAYS][NUM_TYPES_OF_LESSONS];
	public static String[] lesson_types = {"Spin", "Yoga", "Bodysculpt", "Zumba", "Aquacise", "Box Fit", "Other"};
	public static double[] lesson_prices = {10.00, 10.00, 10.00, 10.00, 10.00, 10.00, 8.00};
	public static int[][] lesson_ratings = new int[NUM_DAYS][NUM_TYPES_OF_LESSONS];
	public static String[][] lesson_reviews = new String[NUM_DAYS][NUM_TYPES_OF_LESSONS];
	public static String[][] lesson_status = new String[NUM_DAYS][NUM_TYPES_OF_LESSONS];

	// Declare lesson_bookings array
	static int[][] lesson_bookings = new int[NUM_DAYS][NUM_TYPES_OF_LESSONS];

	public static Scanner input = new Scanner(System.in);

	public static void main(String[] args) {
		// set up the initial timetable
		setupTimetable();

		// main loop
		while (true) {
			// display the main interface
			displayMainMenu();

			// get user's choice
			int choice = input.nextInt();

			// process user's choice
			switch (choice) {

			case 1: // book a lesson
				bookLesson();
				break;

			case 2: // change/cancel a booking
				changeCancelBooking();
				break;

			case 3: // attend a lesson
				attendLesson();
				break;

			case 4: // monthly lesson report
				monthlyLessonReport();
				break;

			case 5: // monthly champion fitness type report
				monthlyChampionFitnessTypeReport();
				break;

			case 6: // exit
				System.out.println("Thanks for using the WFCManager program!");
				System.exit(0);
				break;

			default:
				System.out.println("Invalid choice!");
				break;
			}
		}
	}

	public static void setupTimetable() {
		for (int i = 0; i < NUM_DAYS; i++) {
			for (int j = 0; j < NUM_TYPES_OF_LESSONS; j++) {
				// set up class_timetable
				if (i==0 || i==1) {
					// Saturday and Sunday
					class_timetable[i][j] = 1;
				} else {
					// other days
					class_timetable[i][j] = 0;
				}

				// set up lesson_customers
				lesson_customers[i][j] = 0;

				// set up lesson_ratings
				lesson_ratings[i][j] = 0;
			}
		}
	}

	public static void displayMainMenu() {
		System.out.println("1. Book a group fitness lesson");
		System.out.println("2. Change/Cancel a booking");
		System.out.println("3. Attend a lesson");
		System.out.println("4. Monthly lesson report");
		System.out.println("5. Monthly champion fitness type report");
		System.out.println("6. Exit");
		System.out.print("Please choose an option: ");
	}

	public static void bookLesson() {
		System.out.println("\nHow would you like to check the timetable?");
		System.out.println("1. Specify day");
		System.out.println("2. Specify fitness type");

		int choice = input.nextInt();

		// Initialize lesson_bookings array
		for (int i = 0; i < NUM_DAYS; i++) {
			for (int j = 0; j < NUM_TYPES_OF_LESSONS; j++) {
				lesson_bookings[i][j] = 0;
			}
		}

		switch (choice) {
		case 1: // Specify day
			System.out.println("Please specify the day");
			System.out.println("1. Saturday");
			System.out.println("2. Sunday");

			int dayChoice = input.nextInt();

			if (dayChoice == 1) {
				// display Saturday lessons
				System.out.println("\nSaturday Lessons:");
				for (int i = 0; i < NUM_TYPES_OF_LESSONS; i++) {
					if (class_timetable[0][i] == 1) {
						System.out.println(
								lesson_types[i] + " " + lesson_prices[i] + " (" + lesson_customers[0][i] + "/" + MAX_LESSON_CUSTOMERS + ")"
								);
					}
				}
			} else if (dayChoice == 2) {
				// display Sunday lessons
				System.out.println("\nSunday Lessons:");
				for (int i = 0; i < NUM_TYPES_OF_LESSONS; i++) {
					if (class_timetable[1][i] == 1) {
						System.out.println(
								lesson_types[i] + " " + lesson_prices[i] + " (" + lesson_customers[1][i] + "/" + MAX_LESSON_CUSTOMERS + ")"
								);
					}
				}
			}

			// Get booking id
			System.out.println("\nPlease enter the booking id");
			int bookingId = input.nextInt();

			// select a lesson
			System.out.println("\nPlease select a lesson");
			String lessonType = input.next();

			// check if lesson is available
			for (int i = 0; i < NUM_TYPES_OF_LESSONS; i++) {
				if (lesson_types[i].equals(lessonType)) {
					// check if customer has already booked this lesson
					if (lesson_customers[dayChoice - 1][i] > 0) {
						// check if the booking id is the same
						if (lesson_bookings[dayChoice - 1][i] == bookingId) {
							System.out.println("\nYou have already booked this lesson. Please try another lesson.");
						} else {
							// store booking id
							lesson_bookings[dayChoice - 1][i] = bookingId;
							System.out.println("\nLesson successfully booked! Your booking id is " + bookingId);
							// book the lesson
							lesson_customers[dayChoice - 1][i]++;
						}
						break;
					} else if (lesson_customers[dayChoice - 1][i] < MAX_LESSON_CUSTOMERS) {
						// store booking id
						lesson_bookings[dayChoice - 1][i] = bookingId;
						System.out.println("\nLesson successfully booked! Your booking id is " + bookingId);
						// book the lesson
						lesson_customers[dayChoice - 1][i]++;
						break;
					} else {
						// lesson is full
						System.out.println("\nThis lesson is full. Please try another lesson.");
						break;
					}
				}
			}

			break;

		case 2: // Specify fitness type
			System.out.println("Please specify the fitness type");
			for (int i = 0; i < NUM_TYPES_OF_LESSONS; i++) {
				System.out.println((i+1) + ". " + lesson_types[i]);
			}
			int typeChoice = input.nextInt();

			// display lessons for selected fitness type
			System.out.println("\n" + lesson_types[typeChoice-1] + " Lessons:");
			for (int i = 0; i < NUM_DAYS; i++) {
				if (class_timetable[i][typeChoice-1] == 1) {
					System.out.println(
							"Day " + (i+1) + ": " + lesson_prices[typeChoice-1] + " (" + lesson_customers[i][typeChoice-1] + "/" + MAX_LESSON_CUSTOMERS + ")"
							);
				}
			}

			// Get booking id
			System.out.println("\nPlease enter the booking id");
			bookingId = input.nextInt();

			// select a day
			System.out.println("\nPlease select a day");
			System.out.println("1. Saturday");
			System.out.println("2. Sunday");
			int dayChoice2 = input.nextInt();

			// check if lesson is available
			if (class_timetable[dayChoice2-1][typeChoice-1] == 1) {
				int i = typeChoice-1;
				// check if customer has already booked this lesson
				if (lesson_customers[dayChoice2-1][i] > 0) {
					// check if the booking id is the same
					if (lesson_bookings[dayChoice2-1][i] == bookingId) {
						System.out.println("\nYou have already booked this lesson. Please try another lesson.");
					} else {
						// store booking id
						lesson_bookings[dayChoice2-1][i] = bookingId;
						System.out.println("\nLesson successfully booked! Your booking id is " + bookingId);
						// book the lesson
						lesson_customers[dayChoice2-1][i]++;
					}
				} else if (lesson_customers[dayChoice2-1][i] < MAX_LESSON_CUSTOMERS) {
					// store booking id
					lesson_bookings[dayChoice2-1][i] = bookingId;
					System.out.println("\nLesson successfully booked! Your booking id is " + bookingId);
					// book the lesson
					lesson_customers[dayChoice2-1][i]++;
				} else {
					// lesson is full
					System.out.println("\nThis lesson is full. Please try another lesson.");
				}
			} else {
				// lesson is not available on selected day
				System.out.println("\n" + lesson_types[typeChoice-1] + " lesson is not available on " + (dayChoice2 == 1 ? "Saturday" : "Sunday"));
			}

			break;

		default:
			System.out.println("Invalid option");
			break;
		}
	}

	public static void changeCancelBooking() {
		System.out.println("\nPlease enter your booking ID:");
		int bookingId = input.nextInt();

		// search for booking id
		boolean found = false;
		int dayIndex = -1;
		int typeIndex = -1;
		for (int i = 0; i < NUM_DAYS; i++) {
			for (int j = 0; j < NUM_TYPES_OF_LESSONS; j++) {
				if (lesson_bookings[i][j] == bookingId) {
					found = true;
					dayIndex = i;
					typeIndex = j;
					break;
				}
			}
		}

		// check if booking id is found
		if (found) {
			System.out.println("\nWhat would you like to do?");
			System.out.println("1. Change booking");
			System.out.println("2. Cancel booking");

			int choice = input.nextInt();

			switch (choice) {
			case 1: // Change booking
				System.out.println("\nPlease select a new lesson:");
				for (int i = 0; i < NUM_TYPES_OF_LESSONS; i++) {
					if (class_timetable[dayIndex][i] == 1 && i != typeIndex && lesson_customers[dayIndex][i] < MAX_LESSON_CUSTOMERS) {
						System.out.println(lesson_types[i] + " " + lesson_prices[i] + " (" + lesson_customers[dayIndex][i] + "/" + MAX_LESSON_CUSTOMERS + ")");
					}
				}

				String newLessonType = input.next();

				// check if lesson is available
				for (int i = 0; i < NUM_TYPES_OF_LESSONS; i++) {
					if (lesson_types[i].equals(newLessonType)) {
						// check if new lesson is full
						if (lesson_customers[dayIndex][i] == MAX_LESSON_CUSTOMERS) {
							System.out.println("\nThis lesson is full. Please try another lesson.");
							break;
						} else {
							// change lesson
							lesson_customers[dayIndex][typeIndex]--;
							lesson_customers[dayIndex][i]++;
							System.out.println("\nBooking successfully changed!");
							break;
						}
					}
				}
				break;

			case 2: // Cancel booking
				lesson_customers[dayIndex][typeIndex]--;
				lesson_bookings[dayIndex][typeIndex] = 0;
				System.out.println("\nBooking successfully cancelled!");
				break;

			default:
				System.out.println("Invalid choice!");
				break;
			}
		} else {
			System.out.println("\nNo booking found with this ID. Please try again.");
		}
	}

	public static void attendLesson() {

		System.out.println("Please enter your booking id:");
	    int bookingId = input.nextInt();
	    input.nextLine();  // added this line
	    int lessonType = -1;
	    int weekend = -1;
	    for (int i = 0; i < NUM_DAYS; i++) {
	        for (int j = 0; j < NUM_TYPES_OF_LESSONS; j++) {
	            if (lesson_bookings[i][j] == bookingId) {
	                lessonType = j;
	                weekend = i / 2;
	                break;
	            }
	        }
	        if (lessonType != -1 && weekend != -1) {
	            break;
	        }
	    }

	    // check if booking id is valid
	    boolean isValidBookingId = false;

	    for (int i = 0; i < NUM_DAYS; i++) {
	        for (int j = 0; j < NUM_TYPES_OF_LESSONS; j++) {
	            if (lesson_bookings[i][j] == bookingId) {
	                isValidBookingId = true;
	                lesson_bookings[i][j] = 0; // reset booking id
	                System.out.println("\nYou have successfully attended the lesson.");
	                break;
	            }
	        }
	        if (isValidBookingId) {
	            break;
	        }
	    }

	    if (!isValidBookingId) {
	        System.out.println("\nInvalid booking id!");
	        return; // return to the menu
	    }else {

		    // write a review
		    System.out.println("\nPlease write a review for the lesson (max. 200 characters):");
		    String review = input.nextLine();
	
		    if (review.length() > 200) {
		        System.out.println("\nReview exceeds maximum length!");
		    } else {
		        // provide a numerical rating
		        System.out.println("\nPlease provide a numerical rating (1-5):");
		        int rating = input.nextInt();
	
		        if (rating >= 1 && rating <= 5) {
		            System.out.println("\nReview and rating successfully provided!");
		        } else {
		            System.out.println("\nInvalid rating!");
		        }
		        // store the rating in the lesson_ratings array
		        lesson_ratings[weekend][lessonType] += rating;
		        lesson_customers[weekend][lessonType] += 1;
		    }
	    }
	}

	public static void monthlyLessonReport() {
		// your code here
		System.out.println("Enter the month number: ");
		int monthNum = input.nextInt();

		System.out.println("\nMonthly lesson report for month " + monthNum);
		for (int i = 0; i < 4; i++) {
			int weekend = i + 1;
			System.out.println("\nWeekend " + weekend + ":");
			for (int j = 0; j < NUM_TYPES_OF_LESSONS; j++) {
				System.out.println(lesson_types[j] + ": " + lesson_customers[i][j] + " customers, average rating: " + lesson_ratings[i][j]);
			}
		}
	}

	public static void monthlyChampionFitnessTypeReport() {
		System.out.print("Enter month number (e.g., 03 for March): ");
		String month = input.next();
		double[] incomeByType = new double[NUM_TYPES_OF_LESSONS];

		for (int i = 0; i < NUM_DAYS; i++) {
			String date = month + "/" + (i + 1) + "/2023";
			for (int j = 0; j < NUM_TYPES_OF_LESSONS; j++) {
				double income = lesson_bookings[i][j] * lesson_prices[j];
				incomeByType[j] += income;
			}
		}

		// Display fitness types by income
		System.out.println("\nMonthly Champion Fitness Type Report for " + month + "/2023\n");
		System.out.println("Fitness Type\t\tIncome");
		System.out.println("------------\t\t------");

		for (int i = 0; i < NUM_TYPES_OF_LESSONS; i++) {
			double income = incomeByType[i];
			String type = lesson_types[i];
			System.out.printf("%-20s\t$%.2f\n", type, income);
		}

		// Sort fitness types by income
		for (int i = 0; i < NUM_TYPES_OF_LESSONS - 1; i++) {
			for (int j = i + 1; j < NUM_TYPES_OF_LESSONS; j++) {
				if (incomeByType[i] < incomeByType[j]) {
					// Swap incomeByType[i] and incomeByType[j]
					double temp = incomeByType[i];
					incomeByType[i] = incomeByType[j];
					incomeByType[j] = temp;

					// Swap lesson_types[i] and lesson_types[j]
					String tempType = lesson_types[i];
					lesson_types[i] = lesson_types[j];
					lesson_types[j] = tempType;
				}
			}
		}

		// Display fitness types by income (sorted)
		System.out.println("\nFitness Types Ranked by Income\n");
		System.out.println("Rank\tFitness Type\t\tIncome");
		System.out.println("----\t------------\t\t------");

		for (int i = 0; i < NUM_TYPES_OF_LESSONS; i++) {
			double income = incomeByType[i];
			String type = lesson_types[i];
			System.out.printf("%-4d\t%-20s\t$%.2f\n", (i + 1), type, income);
		}

	}

}
