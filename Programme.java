import java.util.HashMap;
import java.util.ArrayList;
import java.lang.StringBuilder;
import java.util.Map;
import java.io.IOException;

public class Programme {
	private String code;
	private String name;
	private String year;
	private HashMap<Integer, ArrayList<TeacherModule>> semesterModules;

	public Programme(String code, String name, String year, HashMap<Integer, ArrayList<TeacherModule>> semesterModules){
		this.code = code;
		this.name = name;
		this.year = year;
		this.semesterModules = semesterModules;
	}

	public Programme(){
		this("", "", "", new HashMap<Integer, ArrayList<TeacherModule>>());
	}

	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append(this.code + " " + this.name + " " + this.year + "\n");
		for(Map.Entry<Integer, ArrayList<TeacherModule>> entry : this.semesterModules.entrySet()){
			sb.append(entry.getKey() + ": ");
			for(TeacherModule mod : entry.getValue()){
				sb.append(mod.toString() + ", ");
			}
			sb.append("\n");
		}
		return sb.toString();
	}

	/**
	 * This works in conjunction with the grade calculator to return a string containing
	 * statistics about the programme, those being the average qca, median qca and the number of failing students.
	 */
	public String getStatistics(){
		ArrayList<TeacherModule> allMods = new ArrayList<TeacherModule>();
		for(Map.Entry<Integer, ArrayList<TeacherModule>> entry : this.semesterModules.entrySet()){
			allMods.addAll(entry.getValue());
		}
		double avgQCA = GradeCalculator.averageQCA(allMods);
		double medianQCA = GradeCalculator.medianQCA(allMods);
		int numFailed = 0;
		for(TeacherModule teachMod : allMods){
			for(Map.Entry<String, StudentModule> entry: teachMod.getStudentModules().entrySet()){
				if(GradeCalculator.failedModule(entry.getValue()))
					numFailed++;
			}
		}

		String out = "The average QCA of the programme is " + avgQCA +
					 "\nThe median QCA of the programme is " + medianQCA + 
					 "\nThe number of failed students in the programme is " + numFailed;

		return out;
	}

	/**
	 * This returns a list of the failing students in the programme.
	 */
	public String getFailingStudents(){
		ArrayList<Student> failedStudents = new ArrayList<Student>();
		ArrayList<TeacherModule> allMods = new ArrayList<TeacherModule>();
		for(Map.Entry<Integer, ArrayList<TeacherModule>> entry : this.semesterModules.entrySet()){
			allMods.addAll(entry.getValue());
		}

		for(TeacherModule teachMod : allMods){
			for(Map.Entry<String, double[]> entry : teachMod.getGrades().entrySet()){
				Student curStudent = null;
				try{
					curStudent = CSVEditor.getStudent(entry.getKey());
				}catch(IOException e){;}
				if(GradeCalculator.failedSemester(curStudent.getModules()) && !failedStudents.contains(curStudent)){
					failedStudents.add(curStudent);
				}
			}
		}

		StringBuilder out = new StringBuilder();
		for(Student student : failedStudents){
			out.append(student.getID() + " is failing with a QCA of " + GradeCalculator.semesterQca(student.getModules()) + ".\n");
		}

		return out.toString();
	}

	/**
	 * This returns a string containing all of a programmes information.
	 */
	public String getProgDetails(){
		StringBuilder sb = new StringBuilder();
		sb.append(this.code + " " + this.name + " " + this.year + "\n");
		for(Map.Entry<Integer, ArrayList<TeacherModule>> entry : this.semesterModules.entrySet()){
			sb.append("Semester " + entry.getKey() + ": ");
			for(TeacherModule teachMod : entry.getValue()){
				sb.append("\n" + teachMod.toCSV());
			}
		}
		return sb.toString();
	}

	/**
	 * Utility method when adding a programme. It does not contain the added modules as that is handled by
	 * the addModule method when using the addProgramme method
	 * @return A string containing the information about the programme as seen in the programmes csv.
	 */
	public String getProgAsCSVLine(){
		StringBuilder sb = new StringBuilder();
		sb.append(this.code + "," + this.name + "," + this.year);
		for(Map.Entry<Integer, ArrayList<TeacherModule>> entry : this.semesterModules.entrySet()){
			sb.append("," + entry.getKey());
		}
		return sb.toString();
	}

	public String getCode(){
		return this.code;
	}

	public String getName(){
		return this.name;
	}

	public String getYear(){
		return this.year;
	}

	public HashMap<Integer, ArrayList<TeacherModule>> getSemesterModules(){
		return this.semesterModules;
	}

	public ArrayList<TeacherModule> getAllModules(){
		ArrayList<TeacherModule> outMods = new ArrayList<TeacherModule>();

		for(Map.Entry<Integer, ArrayList<TeacherModule>> entry : this.semesterModules.entrySet()){
			outMods.addAll(entry.getValue());
		}

		return outMods;
	}
}