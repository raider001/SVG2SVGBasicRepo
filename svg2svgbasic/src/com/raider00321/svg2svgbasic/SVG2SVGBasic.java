package com.raider00321.svg2svgbasic;

import java.io.File;

public class SVG2SVGBasic {

	/**
	 * @param args
	 */
	static DirectoryHandler handler;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		handler = new DirectoryHandler("Input","Output");
		
		File[] fileList = handler.getInputFolder().listFiles();
		for(int i = 0; i < fileList.length; i++)
		{
			if(fileList[i].toString().endsWith(".svg") || fileList[i].toString().endsWith(".SVG"))
			{
				new FileHandler(fileList[i], handler.getOutputPath());
			}
		}
	}

}
