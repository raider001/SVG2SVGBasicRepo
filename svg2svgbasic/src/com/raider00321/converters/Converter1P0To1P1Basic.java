package com.raider00321.converters;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.util.ArrayList;


public class Converter1P0To1P1Basic 
{
	ArrayList<String> list = new ArrayList<String>();
	public Converter1P0To1P1Basic(File file, Path output)
	{
		try 
		{
			String str;
			//PrintWriter writer = new PrintWriter(output.toString(), "UTF-8");
			BufferedReader reader = new BufferedReader(new FileReader(file.toString()));
			while((str = reader.readLine()) != null)
			{
				list.add(str);
			}
		}
		catch (FileNotFoundException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
		//find all instanced to viewBox and remove them.
		
		try 
		{
			String op = output.toString() +"\\"+ file.toString().subSequence(file.toString().indexOf("\\") + 1, file.toString().length());
			System.out.println(op);
			PrintWriter writer = new PrintWriter(op);
			
			RemoveSVGTags();
			ConvertProperties();
			ConvertRGB();
			addComment();
			for(int k = 0; k < list.size(); k++)
			{
				writer.println(list.get(k));
			}
			writer.close();
		} 
		catch (FileNotFoundException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/*
	 * Modify property values
	 */
	public void ConvertRGB()
	{
		for(int i = 0; i < list.size(); i++)
		{
			while(list.get(i).indexOf("rgb") != -1)
			{
				int rgbIndex = list.get(i).indexOf("rgb");
				int rgbEndIndex = list.get(i).indexOf(")", rgbIndex);
				if(rgbIndex != -1)
				{
					rgbIndex +=4;
					String rgbString = list.get(i).substring(rgbIndex, rgbEndIndex);
					String[] rgbVals = rgbString.split(",");
					int[] rgb = new int[3];
					for(int j = 0; j < 3; j++)
					{
						rgb[j] =Integer.parseInt(rgbVals[j]);
					}
					String hexColor = new RGBToHexConverter().RGBToHexConverter(rgb[0], rgb[1], rgb[2]);
					list.set(i,list.get(i).substring(0, rgbIndex-4) + hexColor + list.get(i).substring(rgbEndIndex+1,list.get(i).length()));
				}
			}
			
		}
	}
	public void ConvertProperties()
	{
		for(int i = 0; i < list.size();i++)
		{
			if(list.get(i).contains("style="))
			{
				list.set(i,list.get(i).replace("style=\"",""));
				list.get(i).lastIndexOf("\"");
				list.set(i, list.get(i).substring(0, list.get(i).lastIndexOf("\"")) + list.get(i).substring(list.get(i).lastIndexOf("\"")+1));
				list.set(i, list.get(i).replaceAll(":","=\""));
				list.set(i, list.get(i).replaceAll(";","\" "));
				list.set(i, list.get(i).replace(">", "\" >"));
			}
		}
	}
	/*
	 * Removes all viewBox svg tags from file
	 */
	
	public void addComment()
	{
		list.add("<!-- Converted By SVG2SVGBasic Converter 2014 By Raider00321 -->");
	}
	public void RemoveSVGTags()
	{
		int svgDepth = 0;
		for(int i=0;i<list.size();i++) 
		{
			if(list.get(i).indexOf("<svg") != -1)
			{
				svgDepth +=1;
			}
			if(list.get(i).indexOf("</svg>") != -1)
			{
				svgDepth -=1;
			}
			if(list.get(i).indexOf("<svg viewBox") != -1)
			{
				int removalStart = list.get(i).indexOf("<svg viewBox");
				int removalEnd = list.get(i).indexOf(">", removalStart);
				list.set(i,list.get(i).substring(removalEnd + 1));
				int tempRef = 0;
				for(int j = list.size()-1; j > 0; j--)
				{
					if(list.get(j).indexOf("</svg>") != -1)
					{
						
						tempRef++;
						if(tempRef == svgDepth)
						{
							System.out.println("bang");
							svgDepth--;
							list.set(j, list.get(j).replace("</svg>", ""));

						}
					}
				}
			}
		}
		
	}
}