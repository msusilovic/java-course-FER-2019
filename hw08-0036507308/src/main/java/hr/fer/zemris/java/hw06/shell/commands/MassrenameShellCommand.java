package hr.fer.zemris.java.hw06.shell.commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import hr.fer.zemris.java.hw06.shell.ArgumentsParser;
import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.FilterResult;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw08.massrename.NameBuilder;
import hr.fer.zemris.java.hw08.massrename.NameBuilderParser;

public class MassrenameShellCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] parts = ArgumentsParser.getMassrenameArguments(arguments);
		
		if(parts[3] == null) {
			env.writeln("Invalid number of arguments for massrename command!");
			return ShellStatus.CONTINUE;
		}
		
		Path dir1 = env.getCurrentDirectory().resolve(parts[0]);
		Path dir2 = env.getCurrentDirectory().resolve(parts[1]);
		
		if (!checkDir(dir1.toFile()) || !checkDir(dir2.toFile())) {
			env.writeln ("Paths given are not directories!");
			return ShellStatus.CONTINUE;
		}
		
		
		try {
			List<FilterResult> selected = filter(dir1, parts[3]);
			
			switch (parts[2]) {
				case "filter":
					filter(env, selected);
					break;
				case "groups":
					groups(env, selected);
					break;
				case "show":
					try {
						show(env, selected, parts[4]);
					}catch(IndexOutOfBoundsException e){
						env.writeln("Missing expression argument for show command!");
					}catch(IllegalArgumentException e) {
						env.writeln("Invalid expression.");
					}
					break;
				case "execute":
					try {
						execute(env, selected, dir1, dir2, parts[4]);
					}catch(IndexOutOfBoundsException e) {
						env.writeln("Missing expression argument for show command!");
					}catch(IllegalArgumentException e) {
						env.writeln("Invalid expression.");
					}
					break;
			}
		} catch (IOException e) {
			env.writeln("Could not filter files from given directory.");
		}
		
		return ShellStatus.CONTINUE;
		
	}

	private void show(Environment env, List<FilterResult> selected, String expression) {
		
		NameBuilderParser parser = new NameBuilderParser(expression);
		NameBuilder builder = parser.getNameBuilder();
		
		for (FilterResult fr : selected) {
			StringBuilder sb = new StringBuilder();
			builder.execute(fr, sb);
			String newName = sb.toString();
			
			env.writeln(fr.toString() + " => " + newName);
		}
		
	}
	
	private void execute(Environment env, List<FilterResult> selected,Path dir1, Path dir2, String expression) 
			throws IOException {
		
		NameBuilderParser parser = new NameBuilderParser(expression);
		NameBuilder builder = parser.getNameBuilder();
		
		for (FilterResult fr : selected) {
			StringBuilder sb = new StringBuilder();
			builder.execute(fr, sb);
			String newName = sb.toString();
			
			Path oldPath = getPath (dir1, fr.toString());
			Path newPath = getPath (dir2, newName);
			
			Files.move(oldPath, newPath);
		}
	}

	private Path getPath(Path dir2, String newName) {
		
		 return dir2.resolve(newName);
	}


	private void groups(Environment env, List<FilterResult> selected) throws IOException {
		
		for (FilterResult fr : selected) {
			env.write(fr.toString() + " ");
			for (int i = 0; i < fr.numberOfGroups(); i++) {
				env.write(i + ": " + fr.group(i) + " ");
			}
			env.writeln("");
		}
		

	}

	private void filter(Environment env, List<FilterResult> selected) throws IOException {
		selected.stream().forEach(f -> env.writeln(f.toString()));
	}

	private boolean checkDir(File dir1) {
		if (!dir1.exists() || !dir1.isDirectory()) {
			return false;
		}
		return true;
	}

	@Override
	public String getCommandName() {
		
		return "massrename";
	}

	@Override
	public List<String> getCommandDescription() {
		
		return null;
	}
	
	
	private List<FilterResult> filter(Path dir, String pattern) throws IOException{
		File[] listAll= dir.toFile().listFiles();
		
	
			Pattern p = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
		
		List<FilterResult> results = new ArrayList<>();
		
		for (File f : listAll) {
			Matcher m = p.matcher(f.getName());
			List<String> groups = new ArrayList<>();
			if (f.isFile() && m.matches()) {
				
				int numberOfGroups = 1 + m.groupCount();
				groups.add(f.getName());
				
				for (int i = 1; i < numberOfGroups; i++) {
					groups.add(m.group(i));
				}
				
				results.add(new FilterResult (f, groups, numberOfGroups));
			}
		}
		
		return results;
	}

}
