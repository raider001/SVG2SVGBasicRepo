package com.raider00321.converters;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


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
			addTabs();
			RemoveSVGTags();
			ConvertProperties();
			ConvertRGB();
			addComment();
			addRecursion();
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
	public void addRecursion()
	{
		//this code gets the data that may be recursive
		Map<String,ArrayList> map = new HashMap<String,ArrayList>();
		ArrayList<String> tempList = new ArrayList<String>();
		boolean inDefs = false;
		String mapName = "";
		for(int i = 0; i < list.size(); i ++)
		{
			
			if(list.get(i).contains("</defs>"))
			{
				map.put(mapName, tempList);
				inDefs = false;
			}
			if(inDefs)
			{
				if(list.get(i).contains("id="))
				{
					mapName = list.get(i).substring(list.get(i).indexOf("id=\"")+4,list.get(i).indexOf("\"",list.get(i).indexOf("id=\"")+4));
					System.out.println("mapName: "+mapName);
				}
				tempList.add(list.get(i));
			}
			if(list.get(i).contains("<defs>"))
			{
				tempList = new ArrayList<String>();
				inDefs = true;
			}
			
		}
		//end of getting the recursive parts. Starts to add the new data
		for(int i = 0; i < list.size(); i ++)
		{
			if(list.get(i).contains("<use"))
			{
				int hashInt = list.get(i).indexOf("#");
				int endOfID = list.get(i).indexOf("\"", hashInt);
				String id = list.get(i).substring(hashInt+1,endOfID);
				map.get(id);
				for(int j = map.get(id).size() - 1; j >= 0; j--)
				{
					String currLine = (String)map.get(id).get(j);
					if(currLine.contains("id=\""+id))
					{
						int startProperties = list.get(i).indexOf("\"",list.get(i).indexOf(id)) + 1;
						String properties = list.get(i).substring(startProperties,list.get(i).lastIndexOf(">"));
						currLine = currLine.replace(">", properties + ">");
						System.out.println(currLine);
					}
					list.add(i + 1,currLine);
				}
				list.remove(i);
			}
			if(list.get(i).contains("</use>"))
			{
				list.remove(i);
			}
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
	 /* 
	  * Modify property values
	  */
	public void ConvertProperties()
	{
		for(int i = 0; i < list.size();i++)
		{
			if(list.get(i).contains("style="))
			{
				list.set(i,list.get(i).replace("style=\"",""));
				list.set(i, list.get(i).substring(0, list.get(i).lastIndexOf("\"")) + list.get(i).substring(list.get(i).lastIndexOf("\"")+1));
				list.set(i, list.get(i).replaceAll(":","=\""));
				list.set(i, list.get(i).replaceAll(";","\" "));
				//list.set(i, list.get(i).substring(0, list.get(i).lastIndexOf("\"")) + list.get(i).substring(list.get(i).lastIndexOf("\"")+1));
				String temp = ""; 
				temp = list.get(i).substring(0,list.get(i).indexOf(">",list.get(i).indexOf("="))) + "\"";
				
				list.get(i).indexOf(">",list.get(i).lastIndexOf("="))
				
				temp = temp + list.get(i).substring(list.get(i).indexOf(">",list.get(i).lastIndexOf("=")),list.get(i).length());
				list.set(i, temp);
				list.set(i, list.get(i).replace(" \">", ">"));
			}
		}
	}
	/*
	 * Makes it more readable and parseable
	 */
	public void addTabs()
	{
		for(int i = 0; i < list.size(); i++)
		{
			String tempList[];
			list.get(i).replaceAll(">", ">lL");
			tempList = list.get(i).split("lL");
			System.out.println(list.get(i));
			if(tempList.length > 1)
			{
				
				for(int j = tempList.length - 1; j >= 0; j--)
				{
					list.add(i+1,tempList[j] + ">");
				}
				list.remove(i);

			}
		
		}
	}
	public void addComment()
	{
		list.add("<!-- Converted By SVG2SVGBasic Converter 2014 By Raider00321 -->");
	}
	/*
	 * removes all viewbox svg tags
	 */
	public void RemoveSVGTags()
	{
		int svgDepth = 0;
		for(int i=0;i<list.size();i++) 
		{
			if(list.get(i).indexOf("<svg viewBox") != -1)
			{
				
				list.remove(i);
				System.out.println(list.get(i));

			}
			if(list.get(i).contains("<svg"))
			{
				svgDepth +=1;
			}
		}
		int currentDepth = 0;
		for(int i = list.size()-1; i >= 0; i--)
		{
			if(list.get(i).contains("</svg>"))
			{
				currentDepth++;
			}
			if(currentDepth > svgDepth)
			{
				currentDepth--;
				list.remove(i);
			}
		}
		
	}
}