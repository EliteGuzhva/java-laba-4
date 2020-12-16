package com.eliteguzhva;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogParser {
    final ByteArrayOutputStream _logContent = new ByteArrayOutputStream();
    List<LogData> data = new ArrayList<>();

    final private Pattern _clientVersionExpr = Pattern.compile("## (\\d+.\\d+.\\d+)");
    final private Pattern _userInfoExpr = Pattern.compile("(\\d+)/(\\d+)/(\\d+) (\\d+):(\\d+):(\\d+).*to: (\\w+)");
    final private Pattern _agentVersionExpr = Pattern.compile(".*ver:(\\d+.\\d+.\\d+)");

    public boolean parse(String filename) {
        File logFile = new File(filename);

        try {
            readFile(logFile);
            load();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }

        return true;
    }

    public void print() {
        data.forEach((v) -> System.out.println(v.toString()));
    }

    private void readFile(File logFile) throws Exception
    {
        FileInputStream fin = new FileInputStream(logFile);
        int n;
        byte[] buf = new byte[4096];
        while ((n = fin.read(buf)) > -1)
            _logContent.write(buf, 0, n);
    }

    private void load() throws Exception
    {
        if (_logContent.size() == 0)
            throw new IOException("Log file content is empty!");

        ByteArrayInputStream inputStream = new ByteArrayInputStream(_logContent.toByteArray());
        InputStreamReader streamReader = new InputStreamReader(inputStream);
        try(BufferedReader br = new BufferedReader(streamReader))
        {
            String line;
            LogData buffer = new LogData();
            while ((line = br.readLine()) != null)
            {
                Matcher matcher = _clientVersionExpr.matcher(line);
                if (matcher.find()) {
                    buffer.clientVersion = matcher.group(1).trim();
                    continue;
                }

                matcher = _userInfoExpr.matcher(line);
                if (matcher.find()) {
                    buffer.year = Integer.parseInt(matcher.group(1).trim());
                    buffer.month = Integer.parseInt(matcher.group(2).trim());
                    buffer.day = Integer.parseInt(matcher.group(3).trim());
                    buffer.hour = Integer.parseInt(matcher.group(4).trim());
                    buffer.minute = Integer.parseInt(matcher.group(5).trim());
                    buffer.second = Integer.parseInt(matcher.group(6).trim());
                    buffer.username = matcher.group(7).trim();
                    continue;
                }

                matcher = _agentVersionExpr.matcher(line);
                if (matcher.find()) {
                    buffer.agentVersion = matcher.group(1).trim();
                    data.add(buffer);
                    buffer = new LogData();
                }
            }
        }
    }
}
