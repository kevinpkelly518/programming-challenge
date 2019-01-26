package de.exxcellent.challenge.solution;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.lang.Math;

public class CSVReader {
	// Separator for CSV (possible to change for other file types, would require consideration of the parseLine function)
	private static final char SEPARATOR = ',';
	
	// Class for determining and storing minimum difference value
	public MinDiffFinder finder;
	
	public class MinDiffFinder {
		public String result_key;
		public Integer result_value;
		
		/**
		 * Constructor
		 */
		public MinDiffFinder() {
			// Start minimum value as highest value
			result_value = Integer.MAX_VALUE;
		}
		/**
		 *  Difference method
		 * @param operand_1: First value of the difference calculation
		 * @param operand_2: Second value of the difference calculation
		 * @return The absolute value of the difference of the two operands
		 */
		private Integer calculateDifference(Integer operand_1, Integer operand_2) {
			return Math.abs(operand_1 - operand_2);
		}
		/**
		 *  Update method (Could be overridden to allow for more columns)
		 * @param in_key: Name of the row from the first column
		 * @param in_value_1: First column value
		 * @param in_value_2: Second column value
		 */
		public void update(String in_key, String in_value_1, String in_value_2) {
			Integer temp_result = calculateDifference(Integer.valueOf(in_value_1), Integer.valueOf(in_value_2));
			
			// If the new result is lower than the current value, then store the new result only
			if (temp_result < result_value) {
				result_value = temp_result;
				result_key = in_key;
			}
		}
	}	
	/**
	 * Constructor
	 * @param search_field_1: First title of column to be calculated
	 * @param search_field_2: Second title of column to be calculated
	 * @param in_file: Location of .csv file
	 * @throws FileNotFoundException
	 */
	public CSVReader(String search_field_1, String search_field_2, String in_file) throws FileNotFoundException {
		// Column numbers to be used based on the input search fields
		Integer column_1, column_2;
		
		// Scanner for the data file
		Scanner scanner = new Scanner(new File(in_file));
		
		// Parse the first line of the file
		List<String> header = parseLine(scanner.nextLine());
		
		// Find the wanted columns based on the input and header
		column_1 = findColumn(search_field_1, header);
		column_2 = findColumn(search_field_2, header);
		
		finder = new MinDiffFinder();
		
		// Extract the columns of the data from the file one by one and update for each
		while (scanner.hasNext()) {
			List<String> line = parseLine(scanner.nextLine(), column_1, column_2);
			finder.update(line.get(0), line.get(1), line.get(2));
		}
	}
	/**
	 * Method for parsing a line from a .csv file
	 * @param csv_line: A line of comma separated values
	 * @return A list of the parsed values
	 */
	private static List<String> parseLine(String csv_line) {
		// Result to be returned
		List<String> result = new ArrayList<>();
		
		// Character array of the line for iterating
		char[] csv_line_chars = csv_line.toCharArray();
		
		// Temporary character storage to add to the result
		StringBuffer temp_word = new StringBuffer();
		
		for (char character : csv_line_chars) {
			// If the separator, then reached the end of the current string
			if (character == SEPARATOR) {
				result.add(temp_word.toString());
				temp_word = new StringBuffer();
			// Break at the end of the line
			} else if (character == '\n') {
				break;
			// Otherwise add to the temporary word
			} else {
				temp_word.append(character);
			}
			
		}
		return result;
	}
	/**
	 * Overridden parse line method that only parses title column and the two specified in constructor
	 * @param csv_line: A line of comma separated values
	 * @param col_1: Title of one column to parse
	 * @param col_2: Title of second column to parse
	 * @return A list of the parsed values
	 */
	private static List<String> parseLine(String csv_line, Integer col_1, Integer col_2) {
		List<String> result = new ArrayList<>();
		char[] csv_line_chars = csv_line.toCharArray();
		StringBuffer temp_word = new StringBuffer();
		
		// Introduce a counter to track the column
		Integer counter = 0;
		
		for (char character : csv_line_chars) {
			if (character == SEPARATOR) {
				// Only want first column and ones specified
				if (counter == 0 || counter == col_1 || counter == col_2) {
					result.add(temp_word.toString());
					temp_word = new StringBuffer();
					counter++;
				} else {
					temp_word = new StringBuffer();
					counter++;
				}
			} else if (character == '\n') {
				break;
			} else {
				temp_word.append(character);
			}
			
		}
		return result;
	}
	/**
	 * Determines the column number from the header
	 * @param search_field: Desired column title
	 * @param header_field: Header of the .csv file
	 * @return Number of the column in the data
	 */
	private static Integer findColumn(String search_field, List<String> header_field) {
		for (Integer i = 0; i < header_field.size(); i++) {
			if (search_field.equals(header_field.get(i))) {
				return i;
			}
		}
		// Error should be handled here
		return 0;
	}
}
