package simulator.launcher;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import simulator.control.Controller;
import simulator.factories.*;
import simulator.model.*;
import simulator.view.MainWindow;

public class Main {

	private final static Integer _timeLimitDefaultValue = 10;
	private final static String _modeDefaultValue = "gui";
	private static Integer _ticks;
	private static String _mode;
	private static String _inFile = null;
	private static String _outFile = null;
	private static Factory<Event> _eventsFactory = null;
	private static Factory<LightSwitchingStrategy> _lssFactory = null;
	private static Factory<DequeuingStrategy> _dqsFactory = null;

	private static void parseArgs(String[] args) {

		// define the valid command line options
		//
		Options cmdLineOptions = buildOptions();

		// parse the command line as provided in args
		//
		CommandLineParser parser = new DefaultParser();
		try {
			CommandLine line = parser.parse(cmdLineOptions, args);
			parseHelpOption(line, cmdLineOptions);
			parseInFileOption(line);
			parseOutFileOption(line);
			parseTicksOption(line);
			parseModeOption(line);
			
			// if there are some remaining arguments, then something wrong is
			// provided in the command line!
			//
			String[] remaining = line.getArgs();
			if (remaining.length > 0) {
				String error = "Illegal arguments:";
				for (String o : remaining)
					error += (" " + o);
				throw new ParseException(error);
			}

		} catch (ParseException e) {
			System.err.println(e.getLocalizedMessage());
			System.exit(1);
		}

	}

	private static Options buildOptions() {
		Options cmdLineOptions = new Options();

		cmdLineOptions.addOption(
				Option.builder("i").longOpt("input").hasArg().desc("Events input file").build());
		
		cmdLineOptions.addOption(
				Option.builder("o").longOpt("output").hasArg().desc("Output file, where reports are written.").build());
		
		cmdLineOptions.addOption(
				Option.builder("h").longOpt("help").desc("Print this message").build());
		
		cmdLineOptions.addOption(
				Option.builder("t").longOpt("ticks").hasArg().desc("Ticks to the simulator`s main loop (default value is 10).").build());
		
		cmdLineOptions.addOption(
				Option.builder("m").longOpt("mode").hasArg().desc("Console mode or Gui mode").build());
		
		return cmdLineOptions;
	}

	private static void parseHelpOption(CommandLine line, Options cmdLineOptions) {
		if (line.hasOption("h")) {
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp(Main.class.getCanonicalName(), cmdLineOptions, true);
			System.exit(0);
		}
	}

	private static void parseInFileOption(CommandLine line) throws ParseException {
		_inFile = line.getOptionValue("i");
		if (_inFile == null) {
			throw new ParseException("An events file is missing");
		}
	}

	private static void parseOutFileOption(CommandLine line) throws ParseException {
		_outFile = line.getOptionValue("o");
	}

	private static void parseTicksOption(CommandLine line) throws ParseException {
		if (line.hasOption("t")) {
			_ticks = Integer.parseInt(line.getOptionValue("t")); 
		}
		else {
			_ticks = _timeLimitDefaultValue;
		}
	}
	
	private static void parseModeOption(CommandLine line) throws ParseException {
		if (line.hasOption("m")) {
			_mode = line.getOptionValue("m");
		}
		else {
			_mode = _modeDefaultValue;
		}
	}
	
	private static void initFactories() {

		// TODO complete this method to initialize _eventsFactory
		List<Builder<LightSwitchingStrategy>> lssBuilders = new ArrayList<>();
		lssBuilders.add(new RoundRobinStrategyBuilder());
		lssBuilders.add(new MostCrowdedStrategyBuilder());
		_lssFactory = new BuilderBasedFactory<>(lssBuilders);
		
		List<Builder<DequeuingStrategy>> dqsBuilders = new ArrayList<>();
		dqsBuilders.add(new MoveFirstStrategyBuilder());
		dqsBuilders.add(new MoveAllStrategyBuilder());
		_dqsFactory = new BuilderBasedFactory<>(dqsBuilders);
		
		List<Builder<Event>> eventsBuilders = new ArrayList<>();
		eventsBuilders.add(new NewJunctionEventBuilder(_lssFactory, _dqsFactory));
		eventsBuilders.add(new NewCityRoadEventBuilder());
		eventsBuilders.add(new NewInterCityRoadEventBuilder());
		eventsBuilders.add(new NewVehicleEventBuilder());
		eventsBuilders.add(new SetWeatherEventBuilder());
		eventsBuilders.add(new SetContClassEventBuilder());
		_eventsFactory = new BuilderBasedFactory<>(eventsBuilders);
		
	}

	private static void startBatchMode() throws IOException {
		// TODO complete this method to start the simulation
		InputStream in = new FileInputStream(new File(_inFile));
		OutputStream out;
		TrafficSimulator sim = new TrafficSimulator();
		Controller controller = new Controller(sim, _eventsFactory);
		
		
		if (_outFile == null) {
			out = System.out;
		}
		else {
			out = new FileOutputStream(new File(_outFile));
		}
		
		controller.loadEvents(in);
		controller.run(_ticks, out);
		in.close();
		out.close();
	}

	private static void startGUIMode() throws FileNotFoundException {
		
		TrafficSimulator sim = new TrafficSimulator();
		Controller controller = new Controller(sim, _eventsFactory);
		
		if (_inFile != null) {
			InputStream in = new FileInputStream(_inFile);
			controller.loadEvents(in);
		}
		
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				new MainWindow(controller);
			}
			
		});
		
	}
	
	private static void start(String[] args) throws IOException {
		initFactories();
		parseArgs(args);
		
		if (_mode.equals("gui")) {
			startGUIMode();
		}
		else {
			startBatchMode();
		}
		
	}

	// example command lines:
	//
	// -i resources/examples/ex1.json
	// -i resources/examples/ex1.json -t 300
	// -i resources/examples/ex1.json -o resources/tmp/ex1.out.json
	// --help

	public static void main(String[] args) {
		try {
			start(args);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
