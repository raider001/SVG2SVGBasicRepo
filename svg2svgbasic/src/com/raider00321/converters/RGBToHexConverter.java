package com.raider00321.converters;

public class RGBToHexConverter {

	public String RGBToHexConverter(int r, int g, int b)
	{
		String hexFormat="";
		int red2calc = (int)(((double)r / 16 - Math.floor(r/16)) * 16);
		int green2calc = (int)(((double)g / 16 - Math.floor(g/16)) * 16);
		int blue2calc = (int)(((double)b / 16 - Math.floor(b/16)) * 16);
		
		int red = r / 16;
		int green = g / 16;
		int blue = b / 16;
		if(red > 9)
		{
			if(red == 10){hexFormat+="A";}
			if(red == 11){hexFormat+="B";}
			if(red == 12){hexFormat+="C";}
			if(red == 13){hexFormat+="D";}
			if(red == 14){hexFormat+="E";}
			if(red == 15){hexFormat+="F";}
		}else{hexFormat+=red+"";}	
		if(red2calc > 9)
		{
			if(red2calc == 10){hexFormat+="A";}
			if(red2calc == 11){hexFormat+="B";}
			if(red2calc == 12){hexFormat+="C";}
			if(red2calc == 13){hexFormat+="D";}
			if(red2calc == 14){hexFormat+="E";}
			if(red2calc == 15){hexFormat+="F";}
		}else{hexFormat+=red2calc+"";}
		if(green > 9)
		{
			if(green == 10){hexFormat+="A";}
			if(green == 11){hexFormat+="B";}
			if(green == 12){hexFormat+="C";}
			if(green == 13){hexFormat+="D";}
			if(green == 14){hexFormat+="E";}
			if(green == 15){hexFormat+="F";}
		}else{hexFormat+=green+"";}	
		if(green2calc > 9)
		{
			if(green2calc == 10){hexFormat+="A";}
			if(green2calc == 11){hexFormat+="B";}
			if(green2calc == 12){hexFormat+="C";}
			if(green2calc == 13){hexFormat+="D";}
			if(green2calc == 14){hexFormat+="E";}
			if(green2calc == 15){hexFormat+="F";}
		}else{hexFormat+=green2calc+"";}
		if(blue > 9)
		{
			if(blue == 10){hexFormat+="A";}
			if(blue == 11){hexFormat+="B";}
			if(blue == 12){hexFormat+="C";}
			if(blue == 13){hexFormat+="D";}
			if(blue == 14){hexFormat+="E";}
			if(blue == 15){hexFormat+="F";}
		}else{hexFormat+=blue+"";}	
		if(blue2calc > 9)
		{
			if(blue2calc == 10){hexFormat+="A";}
			if(blue2calc == 11){hexFormat+="B";}
			if(blue2calc == 12){hexFormat+="C";}
			if(blue2calc == 13){hexFormat+="D";}
			if(blue2calc == 14){hexFormat+="E";}
			if(blue2calc == 15){hexFormat+="F";}
		}else{hexFormat+=red2calc+"";}
		return "#"+hexFormat;
	}
}
