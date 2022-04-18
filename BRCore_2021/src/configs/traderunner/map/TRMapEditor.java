package configs.traderunner.map;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import lib.map.FeldTyp;
import lib.map.Map;
import lib.map.mapEditorRaster.FMapEditorStarter;
import lib.map.mapEditorRaster.MapEditorModel;

public class TRMapEditor {
	
	public static void main(String [] args) {
		
		
//		FeldTyp ffrei = new FeldTyp("Frei", "[ ]", Color.WHITE);
//		FeldTyp fwand = new FeldTyp("Wand", "[#]", Color.DARK_GRAY);
//		FeldTyp fstart = new FeldTyp("Start", "[S]", Color.GREEN);
//		FeldTyp fshop = new FeldTyp("Shop", "[?]", Color.ORANGE);
//		FeldTyp fbot = new FeldTyp("Bot", "[@]", Color.RED.darker());
		
		FeldTyp ffrei = new FeldTyp("Frei", "[ ]", Color.WHITE);
		FeldTyp fwand = new FeldTyp("Wand", "[#]", Color.DARK_GRAY);
		FeldTyp fstart = new FeldTyp("Start", "[S]", Color.GREEN);
		FeldTyp fshop = new FeldTyp("Shop", "[?]", Color.ORANGE);
		FeldTyp fbot = new FeldTyp("Bot", "[@]", Color.RED.darker());
		
		List<FeldTyp> feldTypen = new ArrayList<>();
		feldTypen.add(ffrei);
		feldTypen.add(fwand);
		feldTypen.add(fstart);
		feldTypen.add(fshop);
		feldTypen.add(fbot);
		
		Map m = new Map();
		m.setFeldTypen(feldTypen);
		
		MapEditorModel mem = new MapEditorModel();
		mem.setMap(m);
		
		FMapEditorStarter mes = new FMapEditorStarter(mem);
		mes.requestFocus();
		
	}

}
