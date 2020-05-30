package hr.fer.zemris.java.custom.scripting.exec;

/**
 * Class that contains some value and supports some basic arithmetic operations
 * such as adding, subtracting, multiplying, dividing and comparing.
 * <p>Operations are implemented so they are applicable only for types <code>String</code>,
 * <code>Double</code> and <code>Integer</code>.
 *  
 * @author Martina
 *
 */
public class ValueWrapper {

	/**
	 * Value that this object wraps.
	 */
	private Object value;

	/**
	 * Constructor method for initialising value.
	 * 
	 * @param value value to set
	 */
	public ValueWrapper(Object value) {
		this.value = value;
	}
	
	/**
	 * Increments value by given incValue if arithmetic operation is possible.
	 * 
	 * @param incValue	increment value
	 */
	public void add(Object incValue) {
		checkType(incValue);
		checkType(value);
		
		value = convertTypesAndCalculate(value, incValue, "add");
	}
	
	/**
	 * Subtracts decValue from value if possible.
	 * 
	 * @param decValue	value to subtract
	 */
	public void subtract(Object decValue) {
		checkType(value);
		checkType(decValue);

		value = convertTypesAndCalculate (value, decValue, "sub");
	}
	
	/**
	 * Multiplies value by given mulValue if possible.
	 * 
	 * @param mulValue	value to multiply with
	 */
	public void multiply(Object mulValue) {
		checkType(value);
		checkType(mulValue);
		
		value = convertTypesAndCalculate (value, mulValue, "mul");
	}
	
	/**
	 * Divides value with given divValue if possible.
	 * 
	 * @param divValue	value to divide with
	 */
	public void divide(Object divValue) {
		checkType(value);
		checkType(divValue);
		
		value = convertTypesAndCalculate(value, divValue, "div");
	}

	/**
	 * Compares this value to some other given value if comparison is possible.
	 * 
	 * @param withValue	value to compare with
	 * @return	a positive int if this value is larger than given, false otherwise,
	 * 			zero if values are equal
	 */
	public int numCompare(Object withValue) {
		checkType(value);
		checkType(withValue);

		
		if ((value == null) && (withValue == null)) {
			return 0;
		}
		if (value == null) {
			return (int) convertTypesAndCalculate(0, withValue, "cmp");
		}else if(withValue == null) {
			return (int) convertTypesAndCalculate(value, 0, "cmp");
		}
		return (int) convertTypesAndCalculate(value, withValue, "cmp");
	}
	
	/**
	 * Converts <code>String</code> values to <code>Double</code> or <code>Integer</code>
	 * values and then performs given operation with two arguments.
	 * 
	 * @param value1	first argument in arithmetic operation
	 * @param value2	second argument in arithmetic operation
	 * @param opType	type of arithmetic operation to be executed
	 * @return result of operation
	 */
	private static Object convertTypesAndCalculate(Object value1, Object value2, String opType) {
		Number first = null; 
		Number second = null;
		
		if (value1 == null) {
			first = Integer.valueOf(0);
		}else if (value1 instanceof String) {
			String str = (String)value1;
			first = stringToNumber(str);
		}else {
			first = (Number)value1;
		}
		if (value2 == null) {
			second = Integer.valueOf(0);
		}else if (value2 instanceof String) {
			String str = (String)value2;
			second = stringToNumber(str);
		}else {
			second = (Number) value2;
			
		}
		
		if (first instanceof Double && second instanceof Double) {
			return calculateDoubles ((Double)first, (Double)second, opType);
		}else if (first instanceof Integer && second instanceof Integer){
			return calculateIntegers((Integer)first, (Integer)second, opType);	
		}else if (first instanceof Double && second instanceof Integer) {
			return calculateDoubleAndInteger((Double)first, (Integer) second, opType);
		}else {
			return calculateIntegerAndDouble((Integer)first, (Double)second, opType);
		}
	}
	
	/**
	 * Performs operation if first argument is of type <code>Double</code> and second
	 * argument is of type <code>Integer</code>.
	 * 
	 * @param arg1		first argument in arithmetic operation
	 * @param arg2		second argument in arithmetic operation
	 * @param opType 	type of operation to be executed
	 * @return result of operation
	 */
	private static Object calculateDoubleAndInteger(Double arg1, Integer arg2, String opType) {
		switch (opType) {
		case "add":
			return arg1+arg2;
		case "sub":
			return arg1-arg2;
		case "mul":
			return arg1*arg2;
		case "cmp":
			return Double.compare(arg1, arg2);
		default:
			if (arg2 == 0) throw new IllegalArgumentException("Division by zero is not possible.");
			return arg1/arg2;
		}
	}

	

	/**
	 * Performs operation if first argument is of type <code>Integer</code> and second
	 * argument is of type <code>Double</code>.
	 * 
	 * @param arg1 		first argument of arithmetic operation
	 * @param arg2 		second argument of arithmetic operation
	 * @param opType 	type of operation
	 * @return	result of operation
	 */
	private static Object calculateIntegerAndDouble(Integer arg1, Double arg2, String opType) {
		switch (opType) {
		case "add":
			return arg1+arg2;
		case "sub":
			return arg1-arg2;
		case "mul":
			return arg1*arg2;
		case "cmp":
			return Double.compare(arg1, arg2);
		default:
			if (arg2 == 0) throw new IllegalArgumentException("Division by zero is not possible.");
			return arg1/arg2;
		}
	}
	
	/**
	 * Performs operation when both arguments are of type <code>Integer</code>.
	 * 
	 * @param arg1 		first argument of arithmetic operation
	 * @param arg2		second argument of arithmetic operation
	 * @param opType 	type of operation
	 * @return result of operation
	 */
	private static Object calculateIntegers(Integer arg1, Integer arg2, String opType) {
		switch (opType) {
			case "add":
				return arg1+arg2;
			case "sub":
				return arg1-arg2;
			case "mul":
				return arg1*arg2;
			case "cmp":
				return Integer.compare(arg1, arg2);
			default:
				if (arg2 == 0) throw new IllegalArgumentException("Division by zero is not possible.");
				return arg1/arg2;
		}

	}

	
	/**
	 * 
	 * Performs operation when both arguments are of type <code>Double</code>.
	 * 
	 * @param arg1 		first argument of arithmetic operation
	 * @param arg2		second argument of arithmetic operation
	 * @param opType 	type of operation
	 * @return result of operation
	 */
	private static Object calculateDoubles(Double arg1, Double arg2, String opType) {
		switch (opType) {
		case "add":
			return arg1+arg2;
		case "sub":
			return arg1-arg2;
		case "mul":
			return arg1*arg2;
		case "cmp":
			return Double.compare(arg1, arg2);
		default:
			if (arg2 == 0) throw new IllegalArgumentException("Division by zero is not possible.");
			return arg1/arg2;
		}
		
	}

	/**
	 * Parses <code>String</code> into <code>Double</code> or <code>Integer</code>,
	 * according to it's content.
	 * <p>If value contains '.' or 'E' it is parsed as <code>Double</code>, otherwise 
	 * it is parsed as <code>Integer</code>.
	 * 
	 * @param str	value to be turned into a number
	 * @return	numeric value of given string value
	 */
	private static Number stringToNumber(String str) {
		Number result;
		if (str.contains(".") || str.toUpperCase().contains("E")) {
			try {
				result = Double.parseDouble(str);
			}catch(Exception e) {
				throw new RuntimeException("String can't be parsed to double");
			}
		}else {
			try {
				result = Integer.parseInt(str);
			}catch(Exception e) {
				throw new RuntimeException("String can't be parsed to integer");
			}
		}
		return result;
	}

	/**
	 * Checks if type of value is one of types that support arithmetic operations 
	 * implemented in {@link ValueWrapper} class.
	 * <p>Acceptable types are String, Integer and Double.
	 * 
	 * @param value	an Object of which a class  is to be checked.
	 */
	private void checkType(Object value) {
		if (value != null) {
			Object type = value.getClass();
			if (!type.equals(String.class) && !type.equals(Integer.class) && !type.equals(Double.class)) {
				throw new RuntimeException("Arithmetic operation not defined for this value.");
			}
		}
		
	}

	/**
	 * Returns value stored in this {@link ValueWrapper}.
	 * 
	 * @return stored value
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * Sets value to given parameter value.
	 * 
	 * @param value value to set
	 */
	public void setValue(Object value) {
		this.value = value;
	}
	
}
