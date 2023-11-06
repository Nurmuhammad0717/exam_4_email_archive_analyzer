import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Main {

    private static final Logger log = Logger.getLogger(Main.class.getName());

    static {
        try {

            FileHandler fileHandler = new FileHandler("output/logs",true);
            SimpleFormatter simpleFormatter = new SimpleFormatter();
            fileHandler.setFormatter(simpleFormatter);
            log.addHandler(fileHandler);

        } catch (IOException e) {

            log.warning(e.getMessage());

        }
    }

    public static void main(String[] args) {

        EmailAnalyseService emailAnalyseService = new EmailAnalyseService();

        ArrayList<Email> emailList = emailAnalyseService.getEmails("input/emails.txt");

        StringBuilder analysis = emailAnalyseService.analysis(emailList);

        FileWriter fileWriter = null;
        try {

            fileWriter = new FileWriter("output/e-mail_analysis_report.txt");
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(emailAnalyseService.analysis(emailList).toString());
            bufferedWriter.close();

        } catch (IOException e) {
          log.warning(e.getMessage());
        }


        for (Email email : emailList) {
            System.out.println(email);
        }

    }
}