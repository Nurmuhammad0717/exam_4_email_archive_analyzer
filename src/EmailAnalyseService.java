import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static javax.sound.midi.MidiSystem.getReceiver;

public class EmailAnalyseService {

    private static final Logger logger = Logger.getLogger(EmailAnalyseService.class.getName());

    static {

        try {

            Handler handler = new FileHandler("output/logs.log",true);
            SimpleFormatter simpleFormatter = new SimpleFormatter();
            handler.setFormatter(simpleFormatter);
            logger.addHandler(handler);

        } catch (IOException e) {
            logger.warning(e.getMessage());
        }

    }

    ArrayList<Email> emailList = new ArrayList<>();

    public ArrayList<Email> getEmails(String path) {

        try {

            FileReader reader = new FileReader(path);
            BufferedReader bufferedReader = new BufferedReader(reader);

            String line;
            line = bufferedReader.readLine();
            while (line != null) {
                emailList.add(new Email(getDate(line), getSender(line), getReceiver(line), getTheme(line)));
                line=bufferedReader.readLine();
            }


        } catch (IOException e) {
            logger.severe(e.getMessage());
        }

        return emailList;

    }

    private String getTheme(String line) {
        String[] s = line.split(" " );
        return s[4];
    }

    private String getReceiver(String line) {
        String[] split = line.split(" ");
        return split[3];
    }

    private String getSender(String line) {
        String[] s = line.split(" ");
        return s[2];
    }

    private String getDate(String line) {
        String[] s = line.split(" ");
        String date = s[0];

        Pattern pattern = Pattern.compile("\\[\\d{4}-\\d{2}-\\d{2}\\s\\d{2}:\\d{2}:\\d{2}]");
        Matcher matcher = pattern.matcher(line);
        if (matcher.find()) {
            return matcher.group();
        }
        return null;
    }

    StringBuilder stringBuilder = new StringBuilder();

    public StringBuilder analysis(ArrayList<Email> data) {

        stringBuilder.append("The number of all messages : " + data.size()+"\n");
        stringBuilder.append(getActiveSender(data)+"\n");
        stringBuilder.append( getActiveReceiver(data)+"\n");

        logger.info(stringBuilder.toString());

        return stringBuilder;

    }


    private String getActiveReceiver(ArrayList<Email> data) {
        String user = null;
        int max = 0;
        for (int i = 0; i < data.size(); i++) {

            String name = data.get(i).getReceiver();
            int count = 0;

            for (int j = i + 1; j < data.size() - 1; j++) {

                if (name.equals(data.get(j).getReceiver())) {
                    count++;
                }

            }
            if (name.equals(data.get(data.size() - 1))) count++;

            if (count > max) {
                max = count;
                user = data.get(i).getReceiver();
            }
        }

        if (user == null) {
            return "All users showed the same activeness";
        }
        return "The most messages received by :" + user;
    }


    private String getActiveSender(ArrayList<Email> data) {

        String user = null;
        int max = 0;
        for (int i = 0; i < data.size(); i++) {

            String name = data.get(i).getSender();
            int count = 0;

            for (int j = i + 1; j < data.size() - 1; j++) {

                if (name.equals(data.get(j).getSender())) {
                    count++;
                }

            }
            if (name.equals(data.get(data.size() - 1))) count++;

            if (count >= max) {
                max = count;
                user = data.get(i).getSender();
            }
        }

            if (user == null) {
                return "All users showed the same activeness";
            }
          return "The most messages send by :" + user;
    }
}



