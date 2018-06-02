import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.ThreadLocalRandom;
/**
 * 
 * @author Saraiva - 2017
 * Feel free to change, criticize, laugh on this code.
 * MIT License
 *
 */
public class CaseTestGenerator {
/**
 * @variables 
 * size = Number of vertex on the graph
 * dense = Generate dense graph ( V^2/2 Edges), if false generates esparse graph
 * weight = True if Weight on edges
 * selfLoop = True if selfLoop is allowed
 * weightLowerLimit = Lower bound of random weight generated
 * weightUperLimit = Upper bound of random weight generated
 * separator = File separator 
 * file = Name of file, extension by default .txt - Change on line 46 if wanted
 */
	public static void main(String args[]) {
		int size = 25; 
		boolean dense = true; 
		boolean weight = false; 
		boolean selfLoop = false;
		int weightLowerLimit = 1;
		int weightUperLimit = 99;
		char separator = ' '; 
		String filename = "25DE"; 
		generateCaseTest(size, weightLowerLimit, weightUperLimit, dense, weight, separator, filename, selfLoop);

	}

	public static void generateCaseTest(int size, int weightLowerLimit,int weightUperLimit,
						boolean dense, boolean weight, char separator, String filename, boolean selfLoop) {

		int column1[], column2[];

		column1 = new int[size];
		column2 = new int[size];

		for (int i = 0; i < size; i++) {
			column1[i] = i;
			column2[i] = i + 1;
		}

		PrintWriter writer;
		try {
			writer = new PrintWriter(filename + ".txt", "UTF-8");

			if (dense) {
				for (int i = 0; i < size; i++) {
					for (int j = i + 1; j < size - 1; j++) {
						int pos1 = ThreadLocalRandom.current().nextInt(0, size - 1);
						int pos2 = ThreadLocalRandom.current().nextInt(0, size - 1);

						while (column1[pos1] == column2[pos2] && !selfLoop) {
							pos1 = ThreadLocalRandom.current().nextInt(0, size - 1);
							pos2 = ThreadLocalRandom.current().nextInt(0, size - 1);
						}
						if (weight)
							writer.println(column1[pos1] + "" +  separator + "" + column2[pos2] + "" +  separator
									+ "" +  ThreadLocalRandom.current().nextInt(weightLowerLimit, weightUperLimit + 1));
						else {
							writer.println(column1[pos1] + "" + separator + "" + column2[pos2]);
						}
							
					}
				}
			} else {
				for (int j = 0; j < size / 4; j++)
					for (int i = j; i < size; i++) {
						int pos1 = ThreadLocalRandom.current().nextInt(0, size - 1);
						int pos2 = ThreadLocalRandom.current().nextInt(0, size - 1);

						while (column1[pos1] == column2[pos2] && !selfLoop) {
							pos1 = ThreadLocalRandom.current().nextInt(0, size - 1);
							pos2 = ThreadLocalRandom.current().nextInt(0, size - 1);
						}

						if (weight) 
							writer.println(column1[pos1] + "" +  separator + "" +  column2[pos2] + "" +  separator
									+ "" +  ThreadLocalRandom.current().nextInt(weightLowerLimit, weightUperLimit + 1));
						 else 
							writer.println(column1[pos1] + "" +  separator + "" +  column2[pos2]);						
					}
			}			
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}