package com.app.merger;

import java.util.ArrayList;

import javax.swing.JOptionPane;

/*
 * Ten modu³ wydziela dane z ci¹gu opisu laptopa do osobnych pozycji
 * w strukturze LaptopSeparateData. Struktura ta jest wykorzystywana
 * w dalszym przetwarzaniu zestawu danych i do jego filtrowania.
 * 
 * Version: 20180704
 */

public class DataSeparator {
	public static ArrayList<LaptopSeparateData> process(ArrayList<String> in, ArrayList<InventData> loc)
	{
		// Create return array
		ArrayList<LaptopSeparateData> ret = new ArrayList<LaptopSeparateData>();
		int errors = 0;
		String message1 = "";
		
		// Main loop iterate over all data
		for (int i=0; i<in.size(); i+=4)
		{
			// Init some variables for processing
			String sym = in.get(i);
			String temp1 = in.get(i+1).trim().toUpperCase();
			String price = in.get(i+2).trim();
			String id = in.get(i+3).trim();
			String [] description = temp1.split("/");
			LaptopSeparateData obj = new LaptopSeparateData();
			String [] temps;
			int startIdx = 0;
			int endIdx = description.length-2;
			
			
			System.out.println("(Debug) Splitting data on position: "+sym);
			
			// And now our journey begins...
			
			try
			{
				// Set symbol
				obj.Symbol = sym.trim();
				
				// Set class
				temp1 = description[endIdx].trim();
				temps = temp1.split(" ");
				obj.Class = temps[1];
				endIdx--;
				
				// Set model and cpu
				temp1 = description[startIdx].trim();
				
				temp1 = temp1.replace("   ", " ");
				temp1 = temp1.replace("  ", " ");
				
				temps = temp1.split(" ");
				obj.Processor = temps[temps.length-2];
				
				for(int j=1; j<temps.length-2; j++)
				{
					obj.Model = obj.Model+=temps[j]+" ";
				}
				obj.Model = obj.Model.trim();
				temps = obj.Processor.split("-");
				obj.ProcessorSeries = temps[0];
				startIdx++;
				
				// Set ram memory
				obj.RamMemory = description[startIdx].trim();
				startIdx++;
				
				// Set disk
				obj.Disk = description[startIdx].trim();
				obj.Disk = obj.Disk.replace("   ", " ");
				obj.Disk = obj.Disk.replace("  ", " ");
				startIdx++;
				
				// Set optical drive
				obj.OpticalDrive = description[startIdx].trim();
				startIdx+=2;
				
				// Set operating system
				temp1 = description[startIdx].trim();
				temps = temp1.split(" ");
				if(temps.length==1)
				{
					obj.OperatingSystem = temps[0];
				}
				else
				{
					obj.OperatingSystem = temps[0]+" "+temps[1];
				}
				startIdx++;
				
				// Now the funny part - display or gpu?
				
				temp1 = description[startIdx].trim();
				if(temp1.contains("RADEON") ||
					temp1.contains("NVIDIA") ||
					temp1.contains("QUADRO") ||
					temp1.contains("AMD") ||
					temp1.contains("FORCE") ||
					temp1.contains("NVS") ||
					temp1.contains("FIRE") ||
					temp1.contains("PRO"))
				{
					// read the graphics card and display
					obj.GraphicsProcessor = temp1;
					obj.GraphicsProcessor = obj.GraphicsProcessor.replace("   ", " ");
					obj.GraphicsProcessor = obj.GraphicsProcessor.replace("  ", " ");
					obj.Display = description[startIdx+1].trim();
					startIdx++;
				}
				else
				{
					// read just the display
					obj.GraphicsProcessor = "IGPU";
					obj.Display = description[startIdx].trim();
				}
				
				startIdx++;
				
				for(; startIdx<=endIdx; startIdx++)
				{
					obj.Others+=description[startIdx].trim()+" / ";
				}
				
				obj.Location = "?";
				for(int j=0; j<loc.size(); j++)
				{
					if(loc.get(j).Symbol.equals(obj.Symbol))
					{
						obj.Location = loc.get(j).Location;
						break;
					}
				}
				
				obj.Price = Double.parseDouble(price.replaceAll(",","."));
				obj.Id = id;
				obj.FullDescription = obj.Symbol +"  ("+obj.Id+")  [nw]   |   "+obj.Price.toString()+"z³   |   "+obj.Location+"   |   "+obj.Model+" / "+obj.Processor+" / "+obj.RamMemory+" / "+obj.Disk+" / "+obj.OpticalDrive+" / "+obj.OperatingSystem+" / "+obj.GraphicsProcessor+" / "+obj.Display+" / "+obj.Others+obj.Class;
				ret.add(obj);
			}
			catch (Exception e)
			{
				errors++;
				message1 += sym+", ";
				if(errors%18==0)
				{
					message1+="\n";
				}
				System.out.println("(Debug) Splitting data on position: "+sym+" FAILED. This row will be skipped.");
			}
		}
		
		if(errors>0)
		{
			JOptionPane.showMessageDialog(null, "Wczytywanie zakoñczone.\nZnaleziono "+errors+" b³êdnych wpisów w bazie.\n\n"+message1);
		}
		
		return ret;
	}
}
