import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


public class Driver {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Scanner in = new Scanner(System.in);
		System.out.println("Enter path to a folder of simulation output .txt files or just a file:");
		while(in.hasNextLine())
		{
			String fileName = in.nextLine();
			if(fileName.equals("exit"))
			{
				break;
			}
			File file = new File(fileName);
			if (file.isDirectory())
			{
				for (File subFile : file.listFiles())
				{
					if (subFile.isFile())
					{
						graphFile(subFile);
					}
				}
			}
			else if (file.isFile())
			{
				graphFile(file);
			}
		}
		in.close();
		System.out.println("Exiting");
		System.exit(0);
	}
	
	public static void graphFile(File file) throws IOException
	{
		System.out.println("Graphing file: " + file.getName());
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(file));
			String line;
			br.readLine();
			ArrayList<ArrayList<Double>> sizes = new ArrayList<ArrayList<Double>>();
			ArrayList<ArrayList<Double>> colors = new ArrayList<ArrayList<Double>>();
			ArrayList<ArrayList<Double>> deathSizes = new ArrayList<ArrayList<Double>>();
			ArrayList<ArrayList<Double>> deathColors = new ArrayList<ArrayList<Double>>();
			int year = -1;
			int currentYear = -2;
			while ((line = br.readLine()) != null)
			{
				String[] data = line.split("\t");
				if (data.length < 3)
				{
					break;
				}
				currentYear = Integer.parseInt(data[0]);
				if (currentYear != year)
				{
					year = currentYear;
					sizes.add(new ArrayList<Double>());
					colors.add(new ArrayList<Double>());
					deathSizes.add(new ArrayList<Double>());
					deathColors.add(new ArrayList<Double>());
				}
				Double size = Double.parseDouble(data[1]);
				Double color = Double.parseDouble(data[2]);
				if (data.length >= 8 && !data[5].equals(""))
				{
					Double deathSize = Double.parseDouble(data[5]);
					Double deathColor = Double.parseDouble(data[2]);
					deathSizes.get(deathSizes.size() - 1).add(deathSize);
					deathColors.get(deathColors.size() - 1).add(deathColor);
				}
				sizes.get(sizes.size() - 1).add(size);
				colors.get(colors.size() -1).add(color);
			}
			new ScatterPlot(file.getPath(), "Population Beak Sizes", sizes, colors);
			new ScatterPlot(file.getPath(), "Age Death Beak Sizes", deathSizes, deathColors);
			br.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("File not found");
		}
	}
}
