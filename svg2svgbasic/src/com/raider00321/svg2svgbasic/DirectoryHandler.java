package com.raider00321.svg2svgbasic;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DirectoryHandler {

	/**
	 * @param args
	 */
	File inputFolder;
	Path input;
	Path output;
	public DirectoryHandler(String _inputPath, String _outputPath)
	{
		input = Paths.get(_inputPath);
		output = Paths.get(_outputPath);
		if(Files.notExists(input))
		{
			try {
				Files.createDirectory(input);
			} catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
		if(Files.notExists(output))
		{
			try {
				Files.createDirectory(output);
			} catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
		inputFolder = input.toFile();
	}
	public File getInputFolder()
	{
		return inputFolder;
	}
	public Path getOutputPath()
	{
		return output;
	}

}