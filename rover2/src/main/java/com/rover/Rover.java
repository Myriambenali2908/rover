package com.rover;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Rover {

    public static void main(String[] argv) throws IOException {
        BufferedReader input = null;
        String line, line1, line2;
        String[] roverPosition;
        String instructions;
        int x;
        int y;
        int limitX = 0;
        int limitY = 0;
        try {
            input = new BufferedReader(new FileReader(argv[0]));
            if ((line = input.readLine()) != null) {
                String[] s = line.split(" ");
                limitX = Integer.parseInt(s[0]);
                limitY = Integer.parseInt(s[1]);
            }
            while ((line1 = input.readLine()) != null
                    && (line2 = input.readLine()) != null) {
                roverPosition = line1.split(" ");
                x = Integer.parseInt(roverPosition[0]);
                y = Integer.parseInt(roverPosition[1]);
                instructions = line2;
                String direction = roverPosition[2];
                for (int i = 0; i < instructions.length(); i++) {
                    if (instructions.charAt(i) != 'M') {
                        direction = getNextDirection(direction, instructions.charAt(i));
                    } else if (instructions.charAt(i) == 'M') {
                        int[] pos = getGridPosition(direction, x, y, limitX, limitY);
                        x = pos[0];
                        y = pos[1];
                    }
                }
                System.out.println(x + " " + y + " " + direction);
            }
        } catch (FileNotFoundException exc) {
            System.out.println("Erreur d'ouverture du fichier");
        } finally {
            input.close();
        }
    }

    private static String getNextDirection(String direction, char rotation) {
        String compass = "NESW";
        int posDirection = compass.lastIndexOf(direction);
        int posTarget = posDirection;
        if (rotation == 'L') {
            posTarget = (posDirection - 1 + 4) % 4;
        } else if (rotation == 'R') {
            posTarget = (posDirection + 1 + 4) % 4;
        }
        return String.valueOf(compass.charAt(posTarget));
    }

    private static int[] getGridPosition(String direction, int x, int y, int limitX, int limitY) {
        switch (direction) {
            case "N":
                return new int[]{x, y + 1 <= limitY ? y + 1 : y};
            case "E":
                return new int[]{x + 1 <= limitX ? x + 1 : x, y};
            case "S":
                return new int[]{x, y - 1 >= 0 ? y - 1 : y};
            case "W":
                return new int[]{x - 1 >= 0 ? x - 1 : x, y};
            default:
                break;
        }
        return new int[]{x, y};
    }
}
