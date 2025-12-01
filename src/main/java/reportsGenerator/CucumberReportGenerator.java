package reportsGenerator;

import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;

import java.io.File;
import java.util.Collections;
import java.util.List;

public class CucumberReportGenerator {
	public static void generateReport(String jsonFilePath, String reportOutputPath, String projectName) {
		File reportOutputDirectory = new File(reportOutputPath);
		List<String> jsonFiles = Collections.singletonList(jsonFilePath);

		Configuration configuration = new Configuration(reportOutputDirectory, projectName);
		ReportBuilder reportBuilder = new ReportBuilder(jsonFiles, configuration);
		reportBuilder.generateReports();
	}
}
