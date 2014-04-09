package com.raider00321.svg2svgbasic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Scanner;

import com.raider00321.converters.Converter1P0To1P1Basic;

public class FileHandler {

	public FileHandler(File file, Path output)
	{
		String str;
		try
		{
			BufferedReader reader = new BufferedReader(new FileReader(file));
			while((str = reader.readLine())!= null )
			{

				if(str.contains("SVG 1.0"))
				{
					// run svg 1.0-1.1Basic converter
					new Converter1P0To1P1Basic(file, output);
					reader.close();
					break;
				}
				if(str.contains("SVG 1.2"))
				{
					// run svg 1.2-1.1Basic converter
					reader.close();
					break;
				}
			}
		}
		catch(IOException e)
		{
			System.out.println("Opps! file is protected");
		}
	}
}

