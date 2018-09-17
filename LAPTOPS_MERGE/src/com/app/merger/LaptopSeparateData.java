package com.app.merger;

public class LaptopSeparateData implements Comparable<LaptopSeparateData>{
	String Model;
	String Processor;
	String ProcessorSeries;
	String RamMemory;
	String Disk;
	String OpticalDrive;
	String OperatingSystem;
	String Display;
	String GraphicsProcessor;
	String Class;
	String Others;
	String Symbol;
	String FullDescription;
	String ScreenSize;
	Double Price;
	String Location;
	String Id;
	String AllegroLink;
	
	public LaptopSeparateData() {
		Model = "";
		Processor = "";
		ProcessorSeries = "";
		RamMemory = "";
		Disk = "";
		OpticalDrive = "";
		OperatingSystem = "";
		Display = "";
		GraphicsProcessor = "";
		Class = "";
		Others = "";
		Symbol = "";
		FullDescription = "";
		ScreenSize = "";
		Price = -1.0;
		Location = "";
		Id = "";
		AllegroLink = "https://allegro.pl/show_item.php?item=";
	}

	@Override
	public int compareTo(LaptopSeparateData o) {
		return Price.compareTo(o.Price);
	}
	
	public int compareToStr(LaptopSeparateData o) {
		return Symbol.compareTo(o.Symbol);
	}
	
	public int compareToRev(LaptopSeparateData o) {
		return -(Price.compareTo(o.Price));
	}
	
	public int compareToId(LaptopSeparateData o) {
		return Id.compareTo(o.Id);
	}
	
	public void updateLink(String auctionNumber) {
		AllegroLink += auctionNumber;
	}
	
	public void replaceLink(String auctionLink) {
		AllegroLink = auctionLink;
	}
	
	public void removeLink() {
		AllegroLink = "https://allegro.pl/show_item.php?item=";
	}
	
	public boolean inAllegro()
	{
		return !(AllegroLink.substring(AllegroLink.length()-1, AllegroLink.length()).equals("="));
	}
	
	public void RefreshDescription()
	{
		if(inAllegro())
		{
			FullDescription = Symbol +"  ("+Id+")  [al]   |   "+Price.toString()+"z³   |   "+Location+"   |   "+Model+" / "+Processor+" / "+RamMemory+" / "+Disk+" / "+OpticalDrive+" / "+OperatingSystem+" / "+GraphicsProcessor+" / "+Display+" / "+Others+Class;
		}
		else
		{
			FullDescription = Symbol +"  ("+Id+")  [nw]   |   "+Price.toString()+"z³   |   "+Location+"   |   "+Model+" / "+Processor+" / "+RamMemory+" / "+Disk+" / "+OpticalDrive+" / "+OperatingSystem+" / "+GraphicsProcessor+" / "+Display+" / "+Others+Class;
		}
	}
}
