package utilities;

import java.sql.ResultSet;
import java.sql.Timestamp;

import main.connection;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;

public class utilitiesFunctions {
	static ResultSet rs = null;
	static connection connObject = new connection();

	public static boolean validUser(String username, String password) {
		try {
			rs = connObject.selectQuery(
					"SELECT email FROM Users where email='" + username + "' And " + "password='" + password + "'");
			if (!rs.next()) {
				return false;
			} else {
				return true;
			}
		} catch (Throwable e) {
//			e.printStackTrace();
			System.out.println("Wrong Username or password");
			return false;
		}
	}

	// does user exist
	public static boolean doesUserExists(String username) {
		try {
			rs = connObject.selectQuery("SELECT email FROM Users where email='" + username + "'");
			if (!rs.next()) {
				return false;
			} else {
				return true;
			}
		} catch (Throwable e) {
			System.out.println("Wrong Username");
			return false;
		}

	}

	// does Employee exist
	public static boolean doesEmployeeExists(int emp_id) {
		try {
			rs = connObject.selectQuery("SELECT email FROM Employees where eid='" + emp_id + "'");
			if (!rs.next()) {
				return false;
			} else {
				return true;
			}
		} catch (Throwable e) {
			System.out.println("Wrong Username");
			return false;
		}

	}

	public static String getRole(String email) {
		try {
			rs = connObject.selectQuery("SELECT role FROM Employees e where e.email='" + email + "'");
			if (!rs.next()) {
				rs = connObject.selectQuery("SELECT * FROM Customers e where e.email='" + email + "'");
				if (!rs.next()) {
					return "";
				} else {
					return "customer";
				}
			} else {
				return rs.getString("role");
			}
		} catch (Throwable e) {
//			e.printStackTrace();
			System.out.println("Wrong Username or password");
			return "";
		}

	}

	public static boolean createUser(String email, String password, String name, String add, String ph, String sc_id) {
		try {
			int ans = connObject.insertQuery("Insert into Users Values('" + email + "','" + password + "')");
			if (ans != 1) {
				System.out.println("Email already exists.");
				return false;
			} else {
				ans = connObject.insertQuery("Insert into Customers(id, email, name, tel, addr, sc_id) Values('1231','"
						+ email + "','" + name + "','" + ph + "','" + add + "','" + sc_id + "')");
				if (ans != 1) {
					System.out.println("Error occured while adding to customer table!");
					return false;
				} else {
					return true;
				}
			}
		} catch (Throwable e) {
//			e.printStackTrace();
			System.out.println("Error occured!");
			return false;
		}
	}

	public static boolean addCar(String licensePlate, String purchaseDate, String make, String model, String year,
			int currMilage, String lastServiceDate, String email) {
		try {
			rs = connObject.selectQuery(
					"SELECT vehicle_id FROM Vehicles v where v.make='" + make + "' " + "and v.model='" + model + "'");
			int vehicle_id = 0;
			if (!rs.next()) {
				System.out.println("Make Model doesn't exists");
				return false;
			} else {
				vehicle_id = rs.getInt("vehicle_id");
				int ans = 0;
				if (lastServiceDate.equals("")) {
					ans = connObject.insertQuery(
							"Insert into Owns(plate_no, last_rec_mileage, last_repair_date, purchase_date, vehicle_id, email, car_make_year) "
									+ "Values('" + licensePlate + "','" + currMilage + "',NULL, Date '" + purchaseDate
									+ "','" + vehicle_id + "','" + email + "','" + make + "')");
				} else {
					ans = connObject.insertQuery(
							"Insert into Owns(plate_no, last_rec_mileage, last_repair_date, purchase_date, vehicle_id, email, car_make_year) "
									+ "Values('" + licensePlate + "','" + currMilage + "', Date '" + lastServiceDate
									+ "',Date '" + purchaseDate + "','" + vehicle_id + "','" + email + "','" + make
									+ "')");
				}

				if (ans != 1) {
					System.out.println("Error occured while adding to vehicle table!");
					return false;
				} else {
					return true;
				}
			}
		} catch (Throwable e) {
//			e.printStackTrace();
			System.out.println("Error occured!");
			return false;
		}
	}

//	view profile
	public static ResultSet getCustomerInfo(String email) {
		try {
			rs = connObject.selectQuery("SELECT * FROM Customers WHERE email='" + email + "'");
			return rs;
		} catch (Throwable e) {
			System.out.println("Wrong Email");
			return rs;
		}
	}

	public static ResultSet getCustomerCars(String email) {
		try {
			rs = connObject
					.selectQuery("SELECT * FROM Owns O1 JOIN Vehicles V1 ON O1.vehicle_id=V1.vehicle_id WHERE email='"
							+ email + "'");
			return rs;
		} catch (Throwable e) {
			System.out.println("Wrong Email");
			return rs;
		}
	}

//	update customer profile

	public static ResultSet updateCustomerName(String email, String name) {
		try {
			rs = connObject.selectQuery("UPDATE Customers SET name='" + name + "' " + "WHERE email='" + email + "'");
			return rs;
		} catch (Throwable e) {
			System.out.println("Wrong Email");
			return rs;
		}
	}

	public static ResultSet updateCustomerPassword(String email, String password) {
		try {
			rs = connObject
					.selectQuery("UPDATE Users SET password='" + password + "' " + "WHERE email='" + email + "'");
			return rs;
		} catch (Throwable e) {
			System.out.println("Wrong Email");
			return rs;
		}
	}

	public static ResultSet updateCustomerAddress(String email, String addr) {
		try {
			rs = connObject.selectQuery("UPDATE Customers SET addr='" + addr + "' " + "WHERE email='" + email + "'");
			return rs;
		} catch (Throwable e) {
			System.out.println("Wrong Email");
			return rs;
		}
	}

	public static ResultSet updateCustomerPhoneNumber(String email, String tel) {
		try {
			rs = connObject.selectQuery("UPDATE Customers SET tel='" + tel + "' " + "WHERE email='" + email + "'");
			return rs;
		} catch (Throwable e) {
			System.out.println("Wrong Email");
			return rs;
		}
	}

//	register car 
//	check what needs to be done with lastServD exactly
	public static boolean registerCar(String email, String licensePlate, String purD, String make, String model,
			String year, String currMil, String lastServD) {
		return true;
	}

//	view customer history
	public static ResultSet viewCustomerHistory(String email) {
		return rs;
	}

//	schedule customer service based on maintenance and repair and schedule or reschedule, do completely.

//	show all invoices
	public static ResultSet viewInvoices(String email) {
		return rs;
	}

//	show invoices based on service id
	public static ResultSet viewInvoiceById(String email, String serviceId) {
		return rs;
	}

// get Employee info
	public static ResultSet getEmployeeInfo(String email) {
		try {
			rs = connObject.selectQuery(
					"SELECT eid,s_date,service_centre_id,email,E.addr AS e_addr,E.tel AS e_tel ,wage,freq,E.name AS e_name,SC.name AS sc_name FROM Employees E JOIN Service_center SC ON E.service_centre_id=SC.sc_id WHERE email='"
							+ email + "'");
			return rs;
		} catch (Throwable e) {
			System.out.println("Wrong Email");
			return rs;
		}
	}

//	update employee profile

	public static ResultSet updateEmployeeName(String email, String name) {
		try {
			rs = connObject.selectQuery("UPDATE Employees SET name='" + name + "' " + "WHERE email='" + email + "'");
			return rs;
		} catch (Throwable e) {
			System.out.println("Wrong Email");
			return rs;
		}
	}

	public static ResultSet updateEmployeePassword(String email, String password) {
		try {
			rs = connObject
					.selectQuery("UPDATE Users SET password='" + password + "' " + "WHERE email='" + email + "'");
			return rs;
		} catch (Throwable e) {
			System.out.println("Wrong Email");
			return rs;
		}
	}

	public static ResultSet updateEmployeeEmail(String email, String new_email) {
		try {
			rs = connObject.selectQuery("UPDATE Users SET email='" + new_email + "' " + "WHERE email='" + email + "'");
			return rs;
		} catch (Throwable e) {
			System.out.println("Wrong Email");
			return rs;
		}
	}

	public static ResultSet updateEmployeeAddress(String email, String addr) {
		try {
			rs = connObject.selectQuery("UPDATE Employees SET addr='" + addr + "' " + "WHERE email='" + email + "'");
			return rs;
		} catch (Throwable e) {
			System.out.println("Wrong Email");
			return rs;
		}
	}

	public static ResultSet updateEmployeePhoneNumber(String email, String tel) {
		try {
			rs = connObject.selectQuery("UPDATE Employees SET tel='" + tel + "' " + "WHERE email='" + email + "'");
			return rs;
		} catch (Throwable e) {
			System.out.println("Wrong Email");
			return rs;
		}
	}

// Manager
	public static void addEmpolyee(String name, String addr, String email, String tel, String role, String s_date,
			int wage, String sc_id) {
		try {
			if (role.equalsIgnoreCase("mechanic")) {
				connObject.insertQuery("Insert into Users(email, password) " + "Values('" + email + "','12345678')");
				connObject.insertQuery(
						"Insert into Employees(name, email, tel, s_date, wage, freq, role, service_centre_id) "
								+ "Values('" + name + "'," + "'" + email + "'," + "'" + tel + "', Date " + "'" + s_date
								+ "'," + "'" + wage + "', 'hour'," + "'" + role + "'," + "'" + sc_id + "')");
			} else if (role.equalsIgnoreCase("receptionist")) {
				connObject.insertQuery("Insert into Users(email, password) " + "Values('" + email + "','12345678')");
				connObject.insertQuery(
						"Insert into Employees(name, email, tel, s_date, wage, freq, role, service_centre_id) "
								+ "Values('" + name + "'," + "'" + email + "'," + "'" + tel + "', Date " + "'" + s_date
								+ "'," + "'" + wage + "', 'month'," + "'" + role + "'," + "'" + sc_id + "')");
			}
			System.out.println("Employee Added Successfully");
		} catch (Throwable e) {
			System.out.println("Error occured!");
		}
	}

	public static int generateNewEmployeeId() {
		try {
			int new_id = 0;
			rs = connObject.selectQuery("SELECT MAX(eid) FROM Employees");
			while (rs.next()) {
				new_id = rs.getInt("max(eid)") + 1;
			}
			return new_id;
		} catch (Throwable e) {
			System.out.println("Wrong Username or password");
			return 0;
		}
	}

// get Employee info
	public static ResultSet getInventory(String sc_id) {
		try {
			rs = connObject.selectQuery("Select P.part_id AS part_id, P.name, Q.current_quantity,"
					+ "Q.unit_price,Q.make,Q.min_inventory_thold,Q.min_order_quantity " + "FROM (SELECT * "
					+ "FROM Inventory I " + "JOIN Parts_to_make PM ON I.parts_to_make_id=PM.parts_to_make_id) Q "
					+ "JOIN Parts P ON P.part_id=Q.part_id " + "WHERE service_center_id='" + sc_id + "'");
			return rs;
		} catch (Throwable e) {
			System.out.println("Wrong Service Center Id");
			return rs;
		}
	}

	public ResultSet getOrderHistory(String service_centre_id) {
		// TODO Auto-generated method stub
		return null;
	}

	public ResultSet getNotifications(String service_centre_id) {
		return null;
//		try {
//			rs = connObject.selectQuery("SELECT  + "WHERE E.service_centre_id='" + service_centre_id + "'");
//			return rs;
//		} catch (Throwable e) {
//			System.out.println("Error");
//			return rs;
//		}
	}

	public void addNewCar(String make, String model, String year, int milesA, String monthsA, String partsA, int milesB,
			String monthsB, String partsB, int milesC, String monthsC, String partsC) {
		// TODO Auto-generated method stub

	}

	public ResultSet getCarServiceDetails() {
		// TODO Auto-generated method stub
		return null;
	}

	public ResultSet getServiceHistory(String email) {
		try {
			rs = connObject.selectQuery("SELECT plate_no,last_rec_mileage, last_repair_date,O.email, "
					+ "schedule_id, start_time,mechanic_id,status,name,service_centre_id, "
					+ "m_type,maintenance_schedule_id,repair_schedule_id,rid FROM Owns O JOIN (SELECT * FROM (Select * from Schedule S "
					+ "JOIN Employees E " + "ON S.mechanic_id= E.eid " + "WHERE S.status='complete') W "
					+ "FULL OUTER JOIN Maintenance_schedule MS " + "ON W.schedule_id=MS.maintenance_schedule_id "
					+ "\r\n" + "FULL OUTER JOIN Repair_schedule RS " + "ON W.schedule_id=RS.repair_schedule_id) X "
					+ "ON O.plate_no=X.customer_plate_no " + " " + "WHERE O.email='" + email + "'");
			return rs;
		} catch (Throwable e) {
			System.out.println("Error");
			return rs;
		}
	}

	public ResultSet getInvoiceDetails() {
		// TODO Auto-generated method stub
		return null;
	}

// Schedule
	public static ResultSet getLastMaintenanceType(String licensePlate) {
		try {
			rs = connObject.selectQuery(
					"Select S2.m_type, S2.last_rec_mileage FROM ( Select M.m_type, S.start_time, O.last_rec_mileage from Maintenance_schedule M, Schedule S, Owns O WHERE O.plate_no=S.customer_plate_no AND S.schedule_id=M.maintenance_schedule_id AND S.customer_plate_no ='"
							+ licensePlate + "') S2 WHERE ROWNUM=1 ORDER BY S2.start_time DESC");
			return rs;
		} catch (Throwable e) {
			System.out.println("Wrong License Plate");
			return rs;
		}
	}

	public static ResultSet getVehicleMaintenanceTypeDetails(String licensePlate, String serviceType) {
		try {
			rs = connObject.selectQuery(
					"select M2.miles from Owns O, (select DISTINCT m_type, vehicle_id, miles from Maintenance_uses) M2 where O.plate_no = '"
							+ licensePlate + "' AND M2.vehicle_id = O.vehicle_id AND M2.M_type = '" + serviceType
							+ "'");
			return rs;
		} catch (Throwable e) {
			System.out.println("Wrong License Plate");
			return rs;
		}
	}

	public static ResultSet getMechanicByName(String mechanicName) {
		try {
			rs = connObject
					.selectQuery("SELECT * FROM Employees where name='" + mechanicName + "' AND role ='mechanic'");
			return rs;
		} catch (Throwable e) {
			System.out.println("Wrong Mechanic Name");
			return rs;
		}
	}

	public static ResultSet getMaintenanceMissingParts(String licensePlate, String serviceType) {
		try {
			rs = connObject.selectQuery(
					"SELECT T11.sc_id, T3.part_id, T3.Parts_to_make_id, Inv.current_quantity, Inv.min_inventory_thold, Inv.min_order_quantity, T3.quantity FROM Inventory Inv, "
							+ "(SELECT PM.Parts_to_make_id, T2.part_id, T2.quantity FROM Parts_to_make PM, "
							+ "(SELECT I.part_id, I.quantity, I.vehicle_id, T1.m_type, T1.make FROM Involves I, "
							+ "(SELECT MU.sid, MU.vehicle_id, MU.m_type, V.make FROM Owns O, Maintenance_uses MU, Vehicles V "
							+ "WHERE O.plate_no = 'IRM-1212' AND " + "O.vehicle_id = MU.vehicle_id AND " + "MU.m_type='"
							+ serviceType + "' AND " + "V.vehicle_id = O.vehicle_id) T1 "
							+ "WHERE I.service_id = T1.sid AND " + "T1.vehicle_id = I.vehicle_id) T2 "
							+ "WHERE T2.part_id=Pm.part_id AND " + "T2.make = PM.make) T3, "
							+ "(SELECT C1.sc_id from Customers C1, Owns O1 WHERE O1.plate_no = '" + licensePlate
							+ "' AND O1.email = C1.email) T11 " + "WHERE "
							+ "T3.Parts_to_make_id = Inv.Parts_to_make_id AND " + "T11.sc_id=Inv.service_center_id AND "
							+ "T3.quantity > Inv.current_quantity;");
			return rs;
		} catch (Throwable e) {
			System.out.println("Wrong License Plate");
			return rs;
		}
	}

	public static ResultSet findMaintenanceScheduleDates(String mechanic_name, String plate_no, String m_type) {
		try {
			int day_increment = 1;
			int mechanic_id = 0;
			String s3="";
			if (!mechanic_name.equals("")) {
				rs = getMechanicByName(mechanic_name);
				if (rs.next()) {
					mechanic_id = rs.getInt("eid");
					s3="AND mechanic_id='" + mechanic_id +"'";
				}
			}

			int totalTime = 0;
			if (m_type.equals("A")) {
				totalTime = getTotalTimeForMaintenance(plate_no, "A");
			} else if (m_type.equals("B")) {
				totalTime = getTotalTimeForMaintenance(plate_no, "A") + getTotalTimeForMaintenance(plate_no, "B");
			} else if (m_type.equals("C")) {
				totalTime = getTotalTimeForMaintenance(plate_no, "A") + getTotalTimeForMaintenance(plate_no, "B")
						+ getTotalTimeForMaintenance(plate_no, "C");
			}

			java.util.Date utilDate = new java.util.Date();
			Calendar cal = Calendar.getInstance();
			cal.setTime(utilDate);
			cal.add(Calendar.DATE, day_increment);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			cal.set(Calendar.HOUR_OF_DAY, 4);
			while (day_increment <= 10) {
				String s1 = new java.sql.Timestamp(cal.getTimeInMillis()).toString();
				cal.set(Calendar.HOUR_OF_DAY, 11);
				String s2 = new java.sql.Timestamp(cal.getTimeInMillis()).toString();
				rs = connObject.selectQuery(
						"select S.start_time, S.mechanic_id from Schedule S WHERE S.start_time > TIMESTAMP '" + s1
								+ "' AND S.start_time < TIMESTAMP '" + s1 + "'" + s3
								+ " ORDER BY start_time DESC");
				if (rs.next()) {
					long diff = compareTwoTimeStamps(rs.getTimestamp("end_time"),
							new java.sql.Timestamp(cal.getTimeInMillis()));
					if (compareTwoTimeStamps(rs.getTimestamp("end_time"),
							new java.sql.Timestamp(cal.getTimeInMillis())) >= totalTime) {
						addToSchedule(rs.getTimestamp("end_time"), plate_no, mechanic_id,
								new Timestamp(rs.getTimestamp("end_time").getTime() + (totalTime)));
						break;
					} else {
						day_increment = day_increment + 1;
					}
				} else {
					cal.set(Calendar.HOUR_OF_DAY, 8);
					Timestamp t1 = new java.sql.Timestamp(cal.getTimeInMillis());
					Timestamp t2 = new java.sql.Timestamp(cal.getTimeInMillis());
					t2.setTime(t1.getTime() + totalTime);
					addToSchedule(t1, plate_no, mechanic_id, t2);
					break;
				}
				// apply scheduling instead of the current greedy approach

			}
			return rs;
		} catch (Throwable e) {
			System.out.println("Wrong License Plate");
			return rs;
		}
	}

	public static int addToSchedule(Timestamp start_time, String licensePlate, int mechanicId, Timestamp end_time) {
		try {
			int ans = connObject.insertQuery("INSERT into Schedule " + "Values(" + "'1', " + "TIMESTAMP '" + start_time
					+ "', " + "'" + licensePlate + "', " + mechanicId + ", " + "'pending', " + "TIMESTAMP '" + end_time
					+ "')");
			return ans;
		} catch (Throwable e) {
			System.out.println("Wrong License Plate");
			return -1;
		}
	}

	public static String getNextMaintenanceType(String email, String licensePlate, int currMileage) {
		try {
			int mid1, mid2;
			int A = 0, B = 0, C = 0;
			ResultSet a = getVehicleMaintenanceTypeDetails(licensePlate, "A");
			if (a.next()) {
				A = a.getInt("miles");
			}
			ResultSet b = getVehicleMaintenanceTypeDetails(licensePlate, "B");
			if (b.next()) {
				B = b.getInt("miles");
			}
			ResultSet c = getVehicleMaintenanceTypeDetails(licensePlate, "C");
			if (c.next()) {
				C = c.getInt("miles");
			}
			rs = getLastMaintenanceType(licensePlate);

			if (rs.next()) {
				if (rs.getString("m_type").equals("A")) {
					mid1 = (rs.getInt("last_rec_mileage") + B + (rs.getInt("last_rec_mileage") + B + C)) / 2;
					if (currMileage <= mid1) {
						return "B";
					} else if (currMileage > mid1) {
						return "C";
					}
				} else if (rs.getString("m_type").equals("B")) {
					return "C";
				} else if (rs.getString("m_type").equals("C")) {
					mid1 = (rs.getInt("last_rec_mileage") + A + (rs.getInt("last_rec_mileage") + A + B)) / 2;
					mid2 = (rs.getInt("last_rec_mileage") + A + B + (rs.getInt("last_rec_mileage") + A + B + C)) / 2;
					if (currMileage <= mid1) {
						return "A";
					} else if (currMileage > mid1 && currMileage <= mid2) {
						return "B";
					} else if (currMileage > mid2) {
						return "C";
					}
				}
			} else {
				mid1 = (A + (A + B)) / 2;
				mid2 = (A + B + (A + B + C)) / 2;
				if (currMileage <= mid1) {
					return "A";
				} else if (currMileage > mid1 && currMileage <= mid2) {
					return "B";
				} else if (currMileage > mid2) {
					return "C";
				}
			}
			return "";
		} catch (Throwable e) {
			System.out.println("Wrong Email");
			return "";
		}
	}

	public static int getTotalTimeForMaintenance(String plate_no, String m_type) {
		try {
			rs = connObject.selectQuery("Select sum(hours_to_complete) as sum from Service S, "
					+ "(SELECT MU.sid, MU.vehicle_id, MU.m_type FROM Owns O, Maintenance_uses MU, Vehicles V WHERE O.plate_no = '"
					+ plate_no + "' AND O.vehicle_id = MU.vehicle_id AND MU.m_type='" + m_type
					+ "' AND V.vehicle_id = O.vehicle_id ) T1  where S.sid = T1.sid");
			if (!rs.next()) {
				return 0;
			} else {
				return (int) (rs.getFloat("sum") * 60 * 60 * 1000);
			}
		} catch (Throwable e) {
			System.out.println("Wrong values");
			return 0;
		}
	}

	public static String getEndTimeForMaintenance(String plate_no, String m_type, String schedule_id) {
		try {
			float totalTime = getTotalTimeForMaintenance(plate_no, m_type);

			rs = connObject.selectQuery("Select * from Schedule where schedule_id='" + schedule_id + "'");
			if (!rs.next()) {
				System.out.println("Wrong schedule_id");
				return "";
			} else {
				Timestamp time = rs.getTimestamp("start_time");

				Long duration = (long) (totalTime * 60 * 60 * 1000);
				time.setTime(time.getTime() + duration);
				return time.toString();
			}
		} catch (Throwable e) {
			System.out.println("Something went wrong");
			return "";
		}
	}

	public static boolean updateEndTimeForMaintenanceInSchedule(String plate_no, String m_type, String schedule_id) {
		try {
			float totalTime = getTotalTimeForMaintenance(plate_no, m_type);

			rs = connObject.selectQuery("Select * from Schedule where schedule_id='" + schedule_id + "'");
			if (!rs.next()) {
				System.out.println("Wrong schedule_id");
				return false;
			} else {
				Timestamp time = rs.getTimestamp("start_time");

				Long duration = (long) (totalTime * 60 * 60 * 1000);
				time.setTime(time.getTime() + duration);
				connObject.insertQuery("Update Schedule SET end_time=TIMESTAMP '" + time.toString() + "'");
				return true;
			}
		} catch (Throwable e) {
			System.out.println("Something went wrong");
			return false;
		}
	}

	public static int getTotalTimeForRepair(String repair_name) {
		try {
			rs = connObject.selectQuery("Select sum(hours_to_complete) as sum from Service S,"
					+ "(Select R2.sid from Repair R1, Repair_uses R2 where R1.repair_name='" + repair_name
					+ "' and R1.rid = R2.rid) T1 where S.sid = T1.sid");
			if (!rs.next()) {
				return 0;
			} else {
				return (int) (rs.getFloat("sum") * 60 * 60 * 1000);
			}
		} catch (Throwable e) {
			System.out.println("Wrong values");
			return 0;
		}
	}

	public static String getEndTimeForRepair(String repair_name, String schedule_id) {
		try {
			float totalTime = getTotalTimeForRepair(repair_name);

			rs = connObject.selectQuery("Select * from Schedule where schedule_id='" + schedule_id + "'");
			if (!rs.next()) {
				System.out.println("Wrong schedule_id");
				return "";
			} else {
				Timestamp time = rs.getTimestamp("start_time");

				Long duration = (long) (totalTime * 60 * 60 * 1000);
				time.setTime(time.getTime() + duration);
				return time.toString();
			}
		} catch (Throwable e) {
			System.out.println("Something went wrong");
			return "";
		}
	}

	public static boolean updateEndTimeForRepairInSchedule(String repair_name, String schedule_id) {
		try {
			float totalTime = getTotalTimeForRepair(repair_name);

			rs = connObject.selectQuery("Select * from Schedule where schedule_id='" + schedule_id + "'");
			if (!rs.next()) {
				System.out.println("Wrong schedule_id");
				return false;
			} else {
				Timestamp time = rs.getTimestamp("start_time");

				Long duration = (long) (totalTime * 60 * 60 * 1000);
				time.setTime(time.getTime() + duration);
				connObject.insertQuery("Update Schedule SET end_time=TIMESTAMP '" + time.toString() + "'");
				return true;
			}
		} catch (Throwable e) {
			System.out.println("Something went wrong");
			return false;
		}
	}

	public static long compareTwoTimeStamps(java.sql.Timestamp currentTime, java.sql.Timestamp oldTime) {
		long milliseconds1 = oldTime.getTime();
		long milliseconds2 = currentTime.getTime();

		long diff = milliseconds2 - milliseconds1;

		return diff;
	}

	public ResultSet getEmployeePayrollDetails(String email) {
		try {
			rs = connObject.selectQuery(
					"SELECT E.eid, E.name AS e_name, E.wage AS compensation, "
					+ "E.freq, PR.units, PC.start_date, PC.end_date "
					+ "FROM Employees E JOIN Payment_record PR ON E.eid=PR.eid "
					+ "JOIN Payment_cycle PC ON PR.pcid=PC.pcid WHERE email='"
							+ email + "'");
			return rs;
		} catch (Throwable e) {
			System.out.println("Wrong Email");
			return rs;
		}
	}
}
