package com.leandroyabut.utopiaairlines.application.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ColumnWhitelist {

    private static ColumnWhitelist columnWhitelist;

    public List<String> getWhitelist() {
        List<String> whitelist = new ArrayList<>();
        Scanner scanner = null;
        try (FileInputStream inputStream = new FileInputStream(
                getClass().getClassLoader().getResource("config/whitelist.txt").getPath())) {
            scanner = new Scanner(inputStream);
            while(scanner.hasNextLine()) {
                whitelist.add(scanner.nextLine());
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(scanner!=null) scanner.close();
        }
        return whitelist;
    }

}
