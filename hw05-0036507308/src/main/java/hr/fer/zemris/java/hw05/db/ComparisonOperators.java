package hr.fer.zemris.java.hw05.db;

public class ComparisonOperators {
	
	//satisfied if first string value is less than second string value
	public static final IComparisonOperator LESS = ((v1, v2) -> v1.compareTo(v2) < 0);

	//satisfied if first string value is less or equal to second string
	public static final IComparisonOperator LESS_OR_EQUALS = ((v1, v2) -> v1.compareTo(v2) <= 0);
	
	//satisfied if first string is greater than second string
	public static final IComparisonOperator GREATER = ((v1, v2) -> v1.compareTo(v2) > 0);
	
	//satisfied if  first string is greater than or equals second string
	public static final IComparisonOperator GREATER_OR_EQUALS = ((v1, v2) -> v1.compareTo(v2) >= 0);
	
	//satisfied if strings are equal
	public static final IComparisonOperator EQUALS = ((v1, v2) -> v1.compareTo(v2) == 0);
	
	//satisfied if strings are not equal
	public static final IComparisonOperator	NOT_EQUALS = ((v1, v2) -> v1.compareTo(v2) != 0);
	
	//satisfied if 
	public static final IComparisonOperator LIKE = (v1, v2) -> {
		if (v2.length()-1>v1.length()) {
			return false;
		}
		String[] splitValues = v2.split("\\*");
		if (v2.equals("*")) return true;
		
		int count = countWildcardChars(v2);
		
		if (count > 1) {
			throw new IllegalArgumentException("Too many wildcard characters!");
		}
		if (count == 0) {
			return (v1.equals(v2));
		}
		if (v2.startsWith("*")) {
			return v1.endsWith(splitValues[1]);
			
		}else if (v2.endsWith("*")) {
			return v1.startsWith(splitValues[0]);
			
		}else {
			return v1.startsWith(splitValues[0]) && v1.endsWith(splitValues[1]);
		}
	};

	private static int countWildcardChars(String v2) {
		char[] array = v2.toCharArray();
		int count = 0;
		for (char c : array) {
			if (c=='*') {
				count++;
			}
		}
		return count;
	}
	
	
}
