package hr.fer.zemris.lsystems.impl;

import java.awt.Color;

import hr.fer.zemris.java.custom.collections.Dictionary;
import hr.fer.zemris.lsystems.LSystem;
import hr.fer.zemris.lsystems.LSystemBuilder;
import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.commands.ColorCommand;
import hr.fer.zemris.lsystems.impl.commands.DrawCommand;
import hr.fer.zemris.lsystems.impl.commands.PopCommand;
import hr.fer.zemris.lsystems.impl.commands.PushCommand;
import hr.fer.zemris.lsystems.impl.commands.RotateCommand;
import hr.fer.zemris.lsystems.impl.commands.ScaleCommand;
import hr.fer.zemris.lsystems.impl.commands.SkipCommand;

public class LSystemBuilderImpl implements LSystemBuilder {
	
	/**
	 * Default value for unitLength.
	 */
	private static final double DEFAULT_UNIT_LENGTH = 	0.1;
	
	/**
	 * Default value for unitLengthDegreeScaler.
	 */
	private static final double DEFAULT_UNIT_LENGTH_DEGREE_SCALER =0.1;
	
	/**
	 * Default value for angle.
	 */	
	private static final double DEFAULT_ANGLE = 0;
	
	/**
	 * Default origin vector (null vector).
	 */
	private static final Vector2D DEFAULT_ORIGIN = 	new Vector2D(0, 0);
	
	/**
	 * Default axiom is an empty string.
	 */
	private static final String DEFAULT_AXIOM = "";
	
	protected double unitLength;
	protected double unitLengthDegreeScaler;
	protected Vector2D origin;
	protected double angle;
	protected String axiom;
	
	/**
	 * Dictionary to store all productions for this class.
	 */
	protected Dictionary<Character, String> productions;
	
	/**
	 * Dictionary to store all commands for this class.
	 */
	protected Dictionary<Character, Command> commands;
	
	/**
	 * Constructor method that sets all fields and objects in this {@link LSystemBuilderImpl}
	 * to default values.
	 */
	public LSystemBuilderImpl() {
		super();
		
		this.unitLength = DEFAULT_UNIT_LENGTH;
		this.unitLengthDegreeScaler = DEFAULT_UNIT_LENGTH_DEGREE_SCALER;
		this.origin = DEFAULT_ORIGIN;
		this.angle = DEFAULT_ANGLE;
		this.axiom = DEFAULT_AXIOM;
		this.productions = new Dictionary<>();
		this.commands = new Dictionary<Character, Command>();
	}
	
	/**
	 * Returns new object implementing {@link LSystem}.
	 * 
	 * @return new object implementing {@link LSystem}
	 */
	@Override
	public LSystem build() {
		return new LSystemImpl();
	}

	/**
	 * Configures values for this {@link LSystemBuilderImpl} fields from some
	 * given <code>String</code> array.
	 * 
	 * @param arg0 String array containing values to be set
	 * @return this {@link LSystemBuilderImpl} after modifications
	 */
	@Override
	public LSystemBuilder configureFromText(String[] arg0) {
		for (String s : arg0) {
			String[] help = s.trim().split("\\s+");
			
			//case s starts with substring "origin", set origin
			if (s.startsWith("origin")) {
				double argX= Double.parseDouble(help[1]);
				double argY = Double.parseDouble(help[2]);
				setOrigin(argX, argY);
				
			//case s starts with "angle", set angle
			}else if (s.startsWith("angle")){
				double newAngle = Double.parseDouble(help[1]);
				setAngle(newAngle);
				
			//case s starts with "unitLengthDegreeScaler", set that value
			}else if (s.startsWith("unitLengthDegreeScaler")) {
				double newUnitLengthDegreeScaler;
				if (s.contains("/")) {
					//case new value is provided as a fraction
					String sub = s.substring(23);
					String[] doubleValues = sub.split("/");
					doubleValues[0].trim();
					doubleValues[1].trim();
					newUnitLengthDegreeScaler = Double.parseDouble(doubleValues[0])/Double.parseDouble(doubleValues[1]);
				}else {
					//case new value is provided as a simple double
					newUnitLengthDegreeScaler = Double.parseDouble(help[1]);
				}
				setUnitLengthDegreeScaler(newUnitLengthDegreeScaler);
				
			//case s starts with "unitLength", set that value
			}else if (s.startsWith("unitLength")) {
				double newUnitLength = Double.parseDouble(help[1]);
				setUnitLength(newUnitLength);
				
				//case s starts with "command", register a new command
			}else if (s.startsWith("command")) {
				if (help.length == 4) {
					registerCommand(help[1].charAt(0), help[2]+" "+help[3]);
				}else {
					registerCommand(help[1].charAt(0), help[2]);
				}
				
				
			//case s starts with "axiom", set new axiom
			}else if (s.startsWith("axiom")) {
				setAxiom(help[1]);
			
			//case s starts with "production", register a new production
			}else if (s.startsWith("production")) {
				registerProduction(help[1].charAt(0), help[2]);
			}
		}
		return this;
	}

	/**
	 *Registers a new {@link Command} object into this {@link LSystemBuilderImpl}
	 *dictionary of commands.
	 *
	 *@param arg0 character that triggers this command
	 *@param arg1 String representation of a new command
	 *@return this {@link LSystemBuilderImpl} after modifications
	 */
	@Override
	public LSystemBuilder registerCommand(char arg0, String arg1) {
		String[] commandHelp = arg1.strip().split(" ");
		Command command = null;
		if(arg1.startsWith("draw")) {
			command = new DrawCommand(Double.parseDouble(commandHelp[1]));
				
		}else if(arg1.startsWith("skip")) {
			command = new SkipCommand(Double.parseDouble(commandHelp[1]));
				
		}else if(arg1.startsWith("scale")) {
			command = new ScaleCommand(Double.parseDouble(commandHelp[1]));
			
		}else if(arg1.startsWith("rotate")) {
			command = new RotateCommand(Math.toRadians(Double.parseDouble(commandHelp[1])));
			
		}else if(arg1.equals("push")) {
			command = new PushCommand();
			
		}else if(arg1.equals("pop")) {
			command = new PopCommand();
			
		}else if (arg1.contains("color")) {
			Color newColor = Color.decode("0x" + commandHelp[1]);
			command = new ColorCommand(newColor);
		}
		
		commands.put(arg0, command);
		
		return this;
	}

	/**
	 * Registers a new production in this {@link LSystemBuilderImpl} dictionary 
	 * of productions.
	 * 
	 * @param arg0	character of this production
	 * @param arg1	string to replace some character
	 * @return this {@link LSystemBuilderImpl} after modifications
	 */
	@Override
	public LSystemBuilder registerProduction(char arg0, String arg1) {
		productions.put(arg0, arg1);
		return this;
	}

	/**
	 * Sets angle to given value.
	 * 
	 * @param arg0	angle to set
	 * @return this {@link LSystemBuilderImpl} after modifications
	 */
	@Override
	public LSystemBuilder setAngle(double arg0) {
		this.angle = Math.toRadians(arg0);
		return this;
	}
	
	/**
	 * Sets axiom to given value.
	 * 
	 * @param arg0	axiom to set
	 * @param axiom 
	 * @return this {@link LSystemBuilderImpl} after modifications
	 */
	@Override
	public LSystemBuilder setAxiom(String arg0) {
		this.axiom = arg0;
		return this;
	}

	/**
	 * Sets origin vector to new vector from given parameters.
	 * 
	 * @param arg0 x value of new origin vector
	 * @param arg1 y value of new origin vector
	 * @return this {@link LSystemBuilderImpl} after modifications
	 */
	@Override
	public LSystemBuilder setOrigin(double arg0, double arg1) {
		this.origin = new Vector2D(arg0, arg1);
		return this;
		
	}

	/**
	 * Sets unit length to given value.
	 * 
	 * @param arg0 unit length value to set
	 * @return this {@link LSystemBuilderImpl} after modifications
	 */
	@Override
	public LSystemBuilder setUnitLength(double arg0) {
		this.unitLength = arg0;
		return this;
		
	}

	/**
	 * Sets unit degree scaler to given value.
	 * 
	 * @param arg0 unit degree scaler value to set
	 * @return this {@link LSystemBuilderImpl} after modifications
	 */
	@Override
	public LSystemBuilder setUnitLengthDegreeScaler(double arg0) {
		this.unitLengthDegreeScaler = arg0;
		return this;
		
	}
	
	/**
	 * An implementation of {@link LSystem} used in this class.
	 * 
	 * @author Martina
	 *
	 */
	private class LSystemImpl implements LSystem {

		/**
		 * Creates new Context with values from super class, changes current state and draws.
		 * 
		 * @param arg0	number of levels to be drawn
		 * @param arg1	{@link Painter} object to draw with
		 */
		@Override
		public void draw(int arg0, Painter arg1) {
			Context context = new Context();
			
			Vector2D startDirection = new Vector2D(1, 0).rotated(angle).turnedToUnit();
			
			double scaledUnitLength = unitLength*Math.pow(unitLengthDegreeScaler, arg0);
			
			TurtleState state = new TurtleState(origin, startDirection, Color.BLACK, scaledUnitLength);
			context.pushState(state);
			
			String generated = this.generate(arg0);
			
			for (char c : generated.toCharArray()) {
				Command currentCommand = commands.get(c);
				if (currentCommand != null) {
					currentCommand.execute(context, arg1);
				}
			}
		}

		/**
		 * Generates new sequence from axiom and using productions.
		 * 
		 * @param arg0	number of levels to generate a sequence
		 */
		@Override
		public String generate(int arg0) {
			
			StringBuilder sb = new StringBuilder();
			String sequenceString = axiom;
			char[] sequence = sequenceString.toCharArray();
			
			for (int i = 0; i < arg0; i++) {
				for (int j = 0; j < sequence.length; j++) {
					String toAppend = productions.get(sequence[j]);
					if (toAppend != null) {
						sb.append(toAppend);
					}else {
						sb.append(sequence[j]);
					}
					
				}
				sequenceString = sb.toString();
				sequence = sequenceString.toCharArray();
				sb = new StringBuilder();
			}
			
			return sequenceString;
		}
		
	}

}
