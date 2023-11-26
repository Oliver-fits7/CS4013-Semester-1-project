import java.lang.StringBuilder;

public class StudentModule extends Module{
	double[] studentGrades;

	public StudentModule(String code, String name, int year, int semester, double credits, String gradingScheme, double[] weights, double[] grades){
		super(code, name, year, semester, credits, gradingScheme, weights);
		this.studentGrades = grades;
	}

	public StudentModule(){
		this("", "", 0, 0, 0.0, "", new double[0], new double[0]);
	}

	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString() + "and the grades are ");
		for(double grade : studentGrades){
			sb.append(grade + ", ");
		}
		return sb.toString();
	}

	public double[] getGrades(){
		return this.grades;
	}

	/**
	 * Used when adding a new student so that the grades come as a string.
	 */
	public String getGradesString(){
		StringBuilder sb = new StringBuilder();
		for(double d : this.grades){
			sb.append(d + ",");
		}
		sb.delete(sb.length()-1, sb.length());
		return sb.toString();
	}
}