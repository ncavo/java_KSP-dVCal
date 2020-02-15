package dVCal;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.SortedSet;
import java.util.TreeSet;

public class dVCal {
	public dVCal() {
		init_price = 4650;
		init_mass = 3.050;

		max_price = 300000;
		min_dV1 = 4500;
		min_1st_TWR = 0.5;
		min_2st_TWR = 1.2;
		min_3st_TWR = 1.6;
		
		parts = new Part[max_candidate_parts];
		parts_count = 0;
		parts[parts_count++] = new Part(1, Tank.T400, 0, 50);
//		parts[parts_count++] = new Part(1, Tank.T800, 0, 30);
//		parts[parts_count++] = new Part(1, Tank.TX220, 0, 30);
//		parts[parts_count++] = new Part(1, Tank.TX440, 0, 15);
//		parts[parts_count++] = new Part(1, Tank.TX900, 0, 30);
		parts[parts_count++] = new Part(1, Engine.Terrier, 0, 5);
//		parts[parts_count++] = new Part(1, Engine.Swivel, 0, 5);
//		parts[parts_count++] = new Part(1, Engine.Poodle, 0, 5);

//		parts[parts_count++] = new Part(2, Tank.T400, 1, 30);
//		parts[parts_count++] = new Part(2, Tank.T800, 1, 30);
//		parts[parts_count++] = new Part(2, Tank.TX220, 1, 40);
//		parts[parts_count++] = new Part(2, Tank.TX440, 1, 20);
//		parts[parts_count++] = new Part(2, Tank.TX900, 1, 50);
		parts[parts_count++] = new Part(2, Tank.TX1800, 1, 50);
//		parts[parts_count++] = new Part(2, Tank.X200_16, 1, 50);
//		parts[parts_count++] = new Part(2, Tank.X200_32, 1, 10);
//		parts[parts_count++] = new Part(2, Tank.Jumbo_64, 1, 50);		
		parts[parts_count++] = new Part(2, Engine.Swivel, 0, 5);
//		parts[parts_count++] = new Part(2, Engine.Bobcat, 1, 5);
		parts[parts_count++] = new Part(2, Engine.Skiff, 0, 5);
		
//		parts[parts_count++] = new Part(3, Booster.Thumper, 1, 50);
		parts[parts_count++] = new Part(3, Booster.Kickback, 1, 50);
//		parts[parts_count++] = new Part(3, Booster.Pollux, 1, 50);
//		parts[parts_count++] = new Part(3, Booster.Thoroughbred, 1, 50);
//		parts[parts_count++] = new Part(3, Tank.TX440, 0, 50);
//		parts[parts_count++] = new Part(3, Engine.Skipper, 0, 10);
	}

	private double init_price;
	private double init_mass;

	private double max_price;
	private double min_dV1;
	private double min_1st_TWR;
	private double min_2st_TWR;
	private double min_3st_TWR;

	private Part[] parts; 
	private int parts_count;

	private int counter_not_available = 0;
	private int counter_max_price = 0;
	private int counter_min_dV1 = 0;
	private int counter_1st_TWR = 0;
	private int counter_2st_TWR = 0;
	private int counter_3st_TWR = 0;
	
	private enum Tank {
		// 연료통	가격	공허가격	만재중량	공허중량	액체연료	산화제
		T200("FL-T200 Fuel Tank", 275, 183.2, 1.125, 0.125, 90, 110),
		T400("FL-T400 Fuel Tank", 500, 316.4, 2.25, 0.25, 180, 220),
		T800("FL-T800 Fuel Tank", 800, 432.8, 4.5, 0.5, 360, 440),
		TX220("FL-TX220_Fuel_Tank", 220, 119.02, 1.2375, 0.1375, 99, 121),
		TX440("FL-TX440 Fuel Tank", 440, 238.04, 2.475, 0.275, 198, 242),
		TX900("FL-TX900 Fuel Tank", 900, 486.9, 5.0625, 0.5625, 405, 495),
		TX1800("FL-TX1800_Fuel_Tank", 1800, 973.8, 10.125, 1.125, 810, 990),
		X200_8("Rockomax X200-8 Fuel Tank", 800, 432.8, 4.5, 0.5, 360, 440),
		X200_16("Rockomax X200-16 Fuel Tank", 1550, 815.6, 9, 1, 720, 880),
		X200_32("Rockomax X200-32 Fuel Tank", 3000, 1531.2, 18, 2, 1440, 1760),
		Jumbo_64("Rockomax Jumbo-64 Fuel Tank", 5750, 2812.4, 36, 4, 2880, 3520);
		
		public String name;
		public double price;
		public double empty_price;
		public double wet_mass;
		public double dry_mass;
		public double liquid_fuel;
		public double oxidizer;

		Tank(String name,
			double price,
			double empty_price,
			double wet_mass,
			double dry_mass,
			double liquid_fuel,
			double oxidizer) {
				this.name=name;
				this.price=price;
				this.empty_price=empty_price;
				this.wet_mass=wet_mass;
				this.dry_mass=dry_mass;
				this.liquid_fuel=liquid_fuel;
				this.oxidizer=oxidizer;
		}
	}

	private enum Engine {
		// 엔진	가격	중량	대기추력	진공추력	연료소모	대기 Isp	진공 Isp
		Terrier("LV-909 \"Terrier\" Liquid Fuel Engine", 390, 0.5, 14.78, 60, 3.547, 85, 345),
		Reliant("LV-T30 \"Reliant\" Liquid Fuel Engine", 1100, 1.25, 205.16, 240, 15.789, 265, 310),
		Swivel("LV-T45 \"Swivel\" Liquid Fuel Engine", 1200, 1.5, 167.97, 215, 13.703, 250, 320),
		Poodle("RE-L10 \"Poodle\" Liquid Fuel Engine", 1300, 1.75, 64.29, 250, 14.568, 90, 350),
		Skipper("RE-I5 \"Skipper\" Liquid Fuel Engine", 5300, 3, 568.75, 650, 41.426, 280, 320),
		Bobcat("LV-TX87 \"Bobcat\" Liquid Fuel Engine", 2000, 2, 374.19, 400, 26.316, 290, 310),
		Skiff("RE-I2 \"Skiff\" Liquid Fuel Engine", 2300, 1.6, 240.91, 300, 18.1818181818182, 265, 330),
		Mainsail("RE-M3 \"Mainsail\" Liquid Engine", 13000, 6, 1379.03, 1500, 96.7741935483871, 285, 310);
		
		public String name;
		public double price;
		public double mass;
		public double asl_thrust;
		public double vac_thrust;
		public double max_fuel_comsumption;
		public double asl_isp;
		public double vac_isp;

		Engine(String name,
			double price,
			double mass,
			double asl_thrust,
			double vac_thrust,
			double max_fuel_comsumption,
			double asl_isp,
			double vac_isp) {
				this.name=name;
				this.price = price;
				this.mass = mass;
				this.asl_thrust = asl_thrust;
				this.vac_thrust = vac_thrust;
				this.max_fuel_comsumption = max_fuel_comsumption;
				this.asl_isp = asl_isp;
				this.vac_isp = vac_isp;
		}
	}

	private enum Booster {
		// 부스터	가격	공허가격	만재중량	공허중량	고체연료	대기추력	진공추력	대기 Isp	진공 Isp	연소시간
		Hammer("RT-10 \"Hammer\" Solid Fuel Booster", 400, 175, 3.5625, 0.75, 375, 197.9, 227, 170, 195, 23.7),
		Thumper("BACC \"Thumper\" Solid Fuel Booster", 850, 358, 7.65, 1.5, 820, 250, 300, 175, 210, 42.2),
		Kickback("S1 SRB-KD25k \"Kickback\" Solid Fuel Booster", 2700, 1140, 24, 4.5, 2600, 593.86, 670, 195, 220, 62.8),
		Pollux("THK \"Pollux\" Solid Fuel Booster", 6000, 2520, 51.5, 8, 5800, 1155.556, 1300, 200, 225, 75.2903437970565),
		Thoroughbred("S2-17 \"Thoroughbred\" Solid Fuel Booster", 9000, 4200, 70, 10, 8000, 1515.217, 1700, 205, 230, 81.1785000507366);
		
		public String name;
		public double price;
		public double empty_price;
		public double wet_mass;
		public double dry_mass;
		public double fuel;
		public double asl_thrust;
		public double vac_thrust;
		public double asl_isp;
		public double vac_isp;
		public double burn;

		Booster(String name,
			double price,
			double empty_price,
			double wet_mass,
			double dry_mass,
			double fuel,
			double asl_thrust,
			double vac_thrust,
			double asl_isp,
			double vac_isp,
			double burn) {
				this.name=name;
				this.price=price;
				this.empty_price=empty_price;
				this.wet_mass=wet_mass;
				this.dry_mass=dry_mass;
				this.fuel=fuel;
				this.asl_thrust=asl_thrust;
				this.vac_thrust=vac_thrust;
				this.asl_isp=asl_isp;
				this.vac_isp=vac_isp;
				this.burn=burn;
		}
	}

	private static class Part {
		public int stage;
		public Object part;
		public int min;
		public int max;

		public int type;
		public double price;
		public double wet_mass;
		public double dry_mass;
		public double thrust_asl;
		public double thrust_vac1_asl3;
		public double thrust_vac2_asl2;
		public double thrust_vac3_asl1;
		public double thrust_vac;
		public double fuelcsm_asl;
		public double fuelcsm_vac1_asl3;
		public double fuelcsm_vac2_asl2;
		public double fuelcsm_vac3_asl1;
		public double fuelcsm_vac;

		public Part(int stage, Object part, int min, int max) {
			this.stage = stage;
			this.part = part;
			this.min = min;
			this.max = max;

			if(part instanceof Tank) {
				type = 0;
				Tank tank = (Tank)part;
				price = tank.price;
				wet_mass = tank.wet_mass;
				dry_mass = tank.dry_mass;
			}
			else if(part instanceof Engine) {
				type = 1;
				Engine engine = (Engine)part;
				price = engine.price;
				wet_mass = engine.mass;
				dry_mass = engine.mass;
				thrust_asl = engine.asl_thrust;
				fuelcsm_asl = engine.asl_thrust / engine.asl_isp;
				thrust_vac1_asl3 = ((engine.vac_thrust * 1) + (engine.asl_thrust * 3)) / 4;
				fuelcsm_vac1_asl3 = ((engine.vac_thrust * 1) + (engine.asl_thrust * 3)) /
										((engine.vac_isp * 1) + (engine.asl_isp * 3));
				thrust_vac2_asl2 = ((engine.vac_thrust * 1) + (engine.asl_thrust * 1)) / 2;
				fuelcsm_vac2_asl2 = ((engine.vac_thrust * 1) + (engine.asl_thrust * 1)) /
										((engine.vac_isp * 1) + (engine.asl_isp * 1));
				thrust_vac3_asl1 = ((engine.vac_thrust * 3) + (engine.asl_thrust * 1)) / 4;
				fuelcsm_vac3_asl1 = ((engine.vac_thrust * 3) + (engine.asl_thrust * 1)) /
										((engine.vac_isp * 3) + (engine.asl_isp * 1));
				thrust_vac = engine.vac_thrust;
				fuelcsm_vac = engine.vac_thrust / engine.vac_isp;
			}
			else if(part instanceof Booster) {
				type = 2;
				Booster booster = (Booster)part;
				price = booster.price;
				wet_mass = booster.wet_mass;
				dry_mass = booster.dry_mass;
				thrust_asl = booster.asl_thrust;
				fuelcsm_asl = booster.asl_thrust / booster.asl_isp;
				thrust_vac1_asl3 = ((booster.vac_thrust * 1) + (booster.asl_thrust * 3)) / 4;
				fuelcsm_vac1_asl3 = ((booster.vac_thrust * 1) + (booster.asl_thrust * 3)) /
										((booster.vac_isp * 1) + (booster.asl_isp * 3));
				thrust_vac2_asl2 = ((booster.vac_thrust * 1) + (booster.asl_thrust * 1)) / 2;
				fuelcsm_vac2_asl2 = ((booster.vac_thrust * 1) + (booster.asl_thrust * 1)) /
										((booster.vac_isp * 1) + (booster.asl_isp * 1));
				thrust_vac3_asl1 = ((booster.vac_thrust * 3) + (booster.asl_thrust * 1)) / 4;
				fuelcsm_vac3_asl1 = ((booster.vac_thrust * 3) + (booster.asl_thrust * 1)) /
										((booster.vac_isp * 3) + (booster.asl_isp * 1));
				thrust_vac = booster.vac_thrust;
				fuelcsm_vac = booster.vac_thrust / booster.vac_isp;
			}
		}
	}

	private static class CalResult implements Comparable<CalResult> {
		public int[] parts;
		public double price;
		public double dV1;
		public double dV2;

		public CalResult(int count) {
			parts = new int[count];
		}

		@Override
		public int compareTo(CalResult o) {
			return (int) (((o.dV1 / o.price) - (dV1 / price)) * 1000);
		}
	}
	
	private static final int max_candidate_parts = 32;
	private static final int possible_types_stage = 4;
	public static void main(String[] args) {
		dVCal dc = new dVCal();
		SortedSet<CalResult> results = new TreeSet<CalResult>();
		double last_dV1_per_price = 0;
		int[] cmb = new int[dc.parts_count];
		for(int i = 0; i < dc.parts_count; i++)
			cmb[i] = dc.parts[i].min;
		long loop_counter = 0;
		while(true) {
			boolean end_loop = false;
			for(int i = 0; i < dc.parts_count; i++) {
				cmb[i]++;
				if(cmb[i] <= dc.parts[i].max) break;
				cmb[i] = dc.parts[i].min;
				if(i + 1 == dc.parts_count) end_loop = true;
			}
			if(end_loop) break;
			if(++loop_counter % 20000001 == 0) {
				loop_counter = 0;
				for(int i = 0; i < dc.parts_count; i++) {
					System.out.print(cmb[i]);
					if(i + 1 < dc.parts_count && dc.parts[i].stage != dc.parts[i + 1].stage)
						System.out.print("/");
					else
						System.out.print(" ");
				}
				System.out.print(" max_price=");
				System.out.print(dc.counter_max_price);
				System.out.print(" 1st_TWR=");
				System.out.print(dc.counter_1st_TWR);
				System.out.print(" 2st_TWR=");
				System.out.print(dc.counter_2st_TWR);
				System.out.print(" 3st_TWR=");
				System.out.print(dc.counter_3st_TWR);
				System.out.print(" min_dV1=");
				System.out.print(dc.counter_min_dV1);
				if(results.size() > 0) {
					System.out.print(" best_dV1/price=");
					System.out.print(Math.round(results.first().dV1 / results.first().price * 1000));
					System.out.print(" worst_dV1/price=");
					System.out.print(Math.round(results.last().dV1 / results.last().price * 1000));
				}
				System.out.println();
			}
			int temp_table1[][] = new int[possible_types_stage][3];
			double temp_table2[][] = new double[possible_types_stage][7];
			for(int i = 0; i < dc.parts_count; i++) {
				if(cmb[i] == 0) continue;
				Part part = dc.parts[i];
				int temp_index;
				if(part.stage == 23) temp_index = 3;
				else temp_index = part.stage - 1;
				temp_table1[temp_index][part.type] += cmb[i];
				temp_table2[temp_index][0] += part.price * cmb[i];
				temp_table2[temp_index][1] += part.wet_mass * cmb[i];
				temp_table2[temp_index][2] += part.dry_mass * cmb[i];
				temp_table2[temp_index][3] += part.thrust_asl * cmb[i];
				temp_table2[temp_index][4] += part.fuelcsm_asl * cmb[i];
				switch(part.stage) {
				case 1:
					temp_table2[temp_index][5] += part.thrust_vac * cmb[i];
					temp_table2[temp_index][6] += part.fuelcsm_vac * cmb[i];
					break;
				case 2:
					temp_table2[temp_index][5] += part.thrust_vac1_asl3 * cmb[i];
					temp_table2[temp_index][6] += part.fuelcsm_vac1_asl3 * cmb[i];
					break;
				case 23:
					temp_table2[temp_index][5] += part.thrust_vac1_asl3 * cmb[i];
					temp_table2[temp_index][6] += part.fuelcsm_vac1_asl3 * cmb[i];
					break;
				case 3:
					temp_table2[temp_index][5] += part.thrust_asl * cmb[i];
					temp_table2[temp_index][6] += part.fuelcsm_asl * cmb[i];
					break;
				}
			}
			
			boolean not_available = false;
			for(int i = 0; i < possible_types_stage; i++) {
				if(temp_table1[i][0] != 0 && temp_table1[i][1] == 0) not_available = true;
				else if(temp_table1[i][0] == 0 && temp_table1[i][1] != 0) not_available = true;
			}
			if(not_available) {
				dc.counter_not_available++;
				continue;
			}
			
			double price = 0.0;
			double dry_mass = 0.0;
			double wet_mass = 0.0;
			double dV1 = 0.0;
			double dV2 = 0.0;

			price += dc.init_price + temp_table2[0][0] + temp_table2[1][0] + temp_table2[2][0] + temp_table2[3][0];
			if(price > dc.max_price) {
				dc.counter_max_price++;
				continue;
			}

			dry_mass += dc.init_mass + temp_table2[0][2];
			wet_mass += dc.init_mass + temp_table2[0][1];
			if(dry_mass < wet_mass && temp_table2[0][4] > 0 && temp_table2[0][6] > 0) {
				dV1 += Math.log(wet_mass/dry_mass)*9.81*(temp_table2[0][3] / temp_table2[0][4]);
				dV2 += Math.log(wet_mass/dry_mass)*9.81*(temp_table2[0][5] / temp_table2[0][6]);
				
				if(temp_table2[0][5] / (wet_mass * 9.81) < dc.min_1st_TWR) {
					dc.counter_1st_TWR++;
					continue;
				}				
			}

			dry_mass = wet_mass + temp_table2[1][2] + temp_table2[3][2];
			wet_mass += temp_table2[1][1] + ((temp_table2[3][2] + temp_table2[3][1]) / 2);
			if(dry_mass < wet_mass && temp_table2[1][4] + temp_table2[3][4] > 0 && temp_table2[1][6] + temp_table2[3][6] > 0) {
				dV1 += Math.log(wet_mass/dry_mass)*9.81*((temp_table2[1][3] + (temp_table2[3][3] / 2)) / (temp_table2[1][4] + (temp_table2[3][4] / 2)));
				dV2 += Math.log(wet_mass/dry_mass)*9.81*((temp_table2[1][5] + (temp_table2[3][5] / 2)) / (temp_table2[1][6] + (temp_table2[3][6] / 2)));

				if((temp_table2[1][5] + (temp_table2[3][5] / 2)) / (wet_mass * 9.81) < dc.min_2st_TWR) {
					dc.counter_2st_TWR++;
					continue;
				}
			}

			dry_mass = wet_mass + temp_table2[2][2];
			wet_mass += temp_table2[2][1] + temp_table2[3][1] - ((temp_table2[3][2] + temp_table2[3][1]) / 2);
			if(dry_mass < wet_mass && temp_table2[2][4] + temp_table2[3][4] > 0 && temp_table2[2][6] + temp_table2[3][6] > 0) {
				dV1 += Math.log(wet_mass/dry_mass)*9.81*((temp_table2[2][3] + (temp_table2[3][3] / 2)) / (temp_table2[2][4] + (temp_table2[3][4] / 2)));
				dV2 += Math.log(wet_mass/dry_mass)*9.81*((temp_table2[2][5] + (temp_table2[3][5] / 2)) / (temp_table2[2][6] + (temp_table2[3][6] / 2)));
			}

			if((temp_table2[2][5] + (temp_table2[3][5] / 2)) / (wet_mass * 9.81) < dc.min_3st_TWR) {
				dc.counter_3st_TWR++;
				continue;
			}
			if(dV1 < dc.min_dV1) {
				dc.counter_min_dV1++;
				continue;
			}
			
			if(last_dV1_per_price < dV1 / price * 1000) {
				CalResult result = new CalResult(dc.parts_count);
				for(int i = 0; i < dc.parts_count; i++)
					result.parts[i] = cmb[i];
				result.price = price;
				result.dV1 = dV1;
				result.dV2 = dV2;
				results.add(result);
				if(results.size() > 100) {
					CalResult last = results.last();
					last_dV1_per_price = last.dV1 / last.price * 1000;
					results.remove(last);
				}
			}
		}
		System.out.println("done.");

        String line = new String();
        line += "max_price = " + dc.max_price + System.lineSeparator();
        line += "min_dV1 = " + dc.min_dV1 + System.lineSeparator();
        line += "min_1st_TWR = " + dc.min_1st_TWR + System.lineSeparator();
        line += "min_2st_TWR = " + dc.min_2st_TWR + System.lineSeparator();
        line += "min_3st_TWR = " + dc.min_3st_TWR + System.lineSeparator();
        line += "init_price = " + dc.init_price + System.lineSeparator();
    	line += "init_mass = " + dc.init_mass + System.lineSeparator();
        line += System.lineSeparator();
        
    	line += "counter_not_available = " + dc.counter_not_available + System.lineSeparator();
    	line += "counter_max_price = " + dc.counter_max_price + System.lineSeparator();
    	line += "counter_min_dV1 = " + dc.counter_min_dV1 + System.lineSeparator();
    	line += "counter_1st_TWR = " + dc.counter_1st_TWR + System.lineSeparator();
    	line += "counter_2st_TWR = " + dc.counter_2st_TWR + System.lineSeparator();
    	line += "counter_3st_TWR = " + dc.counter_3st_TWR + System.lineSeparator();
        line += System.lineSeparator();
        
        for(int i = 0; i < dc.parts_count; i++) {
        	line += i + 1;
			if(dc.parts[i].part instanceof Tank) line += ".(" + dc.parts[i].stage + ")" + ((Tank)dc.parts[i].part).name + System.lineSeparator();
			else if(dc.parts[i].part instanceof Engine) line += ".(" + dc.parts[i].stage + ")" + ((Engine)dc.parts[i].part).name + System.lineSeparator();
			else if(dc.parts[i].part instanceof Booster) line += ".(" + dc.parts[i].stage + ")" + ((Booster)dc.parts[i].part).name + System.lineSeparator();
        }
        line += System.lineSeparator();
        
		for(CalResult result : results) {
			for(int i = 0; i < dc.parts_count; i++) {
				line += result.parts[i];
				if(i + 1 < dc.parts_count && dc.parts[i].stage != dc.parts[i + 1].stage)
					line += "/";
				else
					line += " ";
			}
			line += "  price=";
			line += Math.round(result.price);
			line += "  dV1=";
			line += Math.round(result.dV1);
			line += " dV1/price=";
			line += Math.round(result.dV1 / result.price * 1000);
			line += "  dV2=";
			line += Math.round(result.dV2);
			line += " dV2/price=";
			line += Math.round(result.dV2 / result.price * 1000);
			line += System.lineSeparator();
		}
		File file = new File("result.txt");
	    try {
	        FileWriter fw = new FileWriter(file);
	        fw.write(line);
	        fw.close();
	      } catch (IOException e) {
	        e.printStackTrace();
	      }
	}
}