import java.io.File;
import java.io.IOException;
import java.util.List;

import org.pb.x12.*;


public class ParserDemo {

	/**
	 * @param args
	 */
	private static Cf loadCf() {

	    Cf cfX12 = new Cf("X12");

	    Cf cfISA = cfX12.addChild("ISA", "ISA");
	    Cf cfGS = cfISA.addChild("GS", "GS");
	    Cf cfST = cfGS.addChild("ST", "ST", "835", 1);

	    cfST.addChild("1000A", "N1", "PR", 1);
	    cfST.addChild("1000B", "N1", "PE", 1);

	    Cf cf2000 = cfST.addChild("2000", "LX");

	    Cf cf2100 = cf2000.addChild("2100", "CLP");
	    cf2100.addChild("2110", "SVC");

	    cfISA.addChild("GE", "GE");
	    cfX12.addChild("IEA", "IEA");

	    //System.out.println(cfX12);
	    return cfX12;
	}
	
	
	
	
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		X12Simple x12;
//		try {
//			x12 = (X12Simple) new X12SimpleParser().parse(new File("C:\\Users\\vmanchala\\Desktop\\835.txt"));
//			for (Segment s : x12) {
//			     if (s.getElement(0).equals("CLP")) {
//			         System.out.println("Total Change Amount " + s.getElement(3));
//			     }
//			 }
//		} catch (FormatException | IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		 ///////////////////////////////////////////////////////////////////////
		
		Cf cf835 = loadCf();
		Parser parser = new X12Parser(cf835);
		try {
		X12 x12 = (X12) parser.parse(new File("C:\\Users\\vmanchala\\Desktop\\835.txt"));//"C:\\test\\835.txt"));


		Double totalChargeAmount = 0.0;

		// Calculate the total charge amount
		List<Loop> loops = x12.findLoop("2100");

		for (Loop loop : loops) {
		    for (Segment s : loop) {
		        if (s.getElement(0).equals("CLP")) {
		            totalChargeAmount = totalChargeAmount + Double.parseDouble(s.getElement(3));
		            System.out.println("Total Charged Amount = " + totalChargeAmount.toString());
		        }
		        if (s.getElement(0).equals("NM1")) {
		            totalChargeAmount = totalChargeAmount + Double.parseDouble(s.getElement(2));
		            System.out.println("Total Charged Amount_Venu = " + totalChargeAmount.toString());
		        }
//		        if (s.getElement(0).equals("REF")) {
//		            totalChargeAmount = totalChargeAmount + Double.parseDouble(s.getElement(1));
//		        }
		    }
		}
		System.out.println("Total Charged Amount = " + totalChargeAmount.toString());
		} catch (FormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
