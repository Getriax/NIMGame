package NimGame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;


public class Game {
    private int stacksNumber;
    private ArrayList<Stack> stacks;
    private Scanner in;
    private boolean computerStarts;

    public Game() {
        in = new Scanner(System.in);
        System.out.print("Podaj liczbe staków: ");
        this.stacksNumber = in.nextInt();

        this.stacks = new ArrayList<>();
        for (int i = 0; i < stacksNumber; i++) {
            System.out.print("Podaj liczbe patyczkow staku " + (i + 1) + ": ");
            int temp = in.nextInt();
            stacks.add(new Stack(temp));
        }
        System.out.print("Kto zaczyna? 0 - komputer | 1 - osoba: ");
        computerStarts = in.nextInt() == 0;

        System.out.println("************ GAME IS STARTING ***********");
    }

    private void userTurn() {

        int stackNum = -1;
        int branchesNum = -1;
        System.out.println("\n Obecne wartosci \n ----------------------------");
        System.out.println("Numer stosu | Liczba patykow");
        for (int i = 0; i < stacksNumber; i++) {
            System.out.printf("%11d | %14d", (i + 1), stacks.get(i).getNumber());
            System.out.println("");
        }
        System.out.println("----------------------------");
        while (stackNum < 0 || stackNum > stacksNumber) {
            System.out.print("Podaj stos: ");
            stackNum = in.nextInt() - 1;
            if (!stacks.get(stackNum).exists()) {
                stackNum = -1;
            }
        }

        while (branchesNum < 0 || branchesNum > stacks.get(stackNum).getNumber()) {
            System.out.print("Podaj liczbę patyczków: ");
            branchesNum = in.nextInt();
        }


        stacks.get(stackNum).substractNumber(branchesNum);
    }

    private void computerTurn() {
        ArrayList<ArrayList<Character>> binaryChars = new ArrayList<>();
        int maxNumberOfChars = 0;
        int maxStackNumber = 0;
        int indexOfMax = 0;

        for (int i = 0; i < stacksNumber; i++) {
            if (stacks.get(i).getNumber() > maxStackNumber) {
                maxStackNumber = stacks.get(i).getNumber();
                indexOfMax = i;
            }
            binaryChars.add(new ArrayList<Character>());
            for (Character ch : stacks.get(i).getNumberBinary().toCharArray()) {
                binaryChars.get(i).add(ch);
            }
            Collections.reverse(binaryChars.get(i));
            maxNumberOfChars = maxNumberOfChars > binaryChars.get(i).size() ? maxNumberOfChars : binaryChars.get(i).size();
        }

        String primitveSum = "";


        for (int i = 0; i < maxNumberOfChars; i++) {
            int numberOfOnes = 0;
            for (int j = 0; j < binaryChars.size(); j++) {
                if (binaryChars.get(j).size() > i) {

                    if (binaryChars.get(j).get(i) == '1')
                        numberOfOnes++;

                }
            }
            int numToPrint = numberOfOnes % 2 == 0 ? 0 : 1;
            primitveSum = numToPrint + primitveSum;

        }

        String binaryMax = Integer.toString(maxStackNumber, 2);

        while (maxStackNumber < Integer.parseInt(checkSubstring(primitveSum, binaryMax), 2)) {
            int newMax = 0;
            for (int i = 0; i < stacks.size(); i++) {
                if (newMax < stacks.get(i).getNumber() &&  stacks.get(i).getNumber() < maxStackNumber) {

                    newMax = stacks.get(i).getNumber();
                    indexOfMax = i;
                }
            }
            maxStackNumber = newMax;
            binaryMax = Integer.toString(maxStackNumber, 2);

        }
        int subVal = maxStackNumber - Integer.parseInt(checkSubstring(primitveSum, binaryMax), 2);
        if (subVal == 0) {
            int indx = 0;
            int val = 0;
            for(int i = 0; i < stacks.size(); i++) {
                if(stacks.get(i).exists()) {
                    indx = i;
                    val = stacks.get(i).getNumber();
                    stacks.get(i).substractNumber(stacks.get(i).getNumber());
                    break;
                }
                System.out.println("\n !!! Komputer odjal " + val + " patykow z " + (i + 1) + " stosu !!!");
            }
        }
        else {
            stacks.get(indexOfMax).substractNumber(subVal);
            System.out.println("\n !!! Komputer odjal " + subVal + " patykow z " + (indexOfMax + 1) + " stosu !!!");
        }

    }
    private String checkSubstring(String decimalSum, String maxBinary) {
        String subVal = "";
        if (decimalSum.length() != maxBinary.length()) {
            if(decimalSum.length() < maxBinary.length()) {
                int timesZero = maxBinary.length() - decimalSum.length();
                for (int i = 0; i < timesZero; i++)
                    decimalSum = "0" + decimalSum;
            }
            else {
                int timesZero = decimalSum.length() - maxBinary.length();
                for (int i = 0; i < timesZero; i++)
                    maxBinary = "0" + maxBinary;
            }
        }
        for (int j = 0; j < decimalSum.length(); j++) {
            if (decimalSum.charAt(j) == '1') {
                subVal += maxBinary.charAt(j) == '1' ? 0 : 1;
            } else {
                subVal += maxBinary.charAt(j);
            }
        }

        return subVal;
    }

    public void startGame() {
        if (computerStarts)
            while (true) {
                computerTurn();
                if (stacksAvaliable()) {
                    userTurn();
                    if (stacksAvaliable())
                        continue;
                    else {
                        System.out.println("\n $$$$$$ Wygrales gratulacje $$$$$$$");
                        break;
                    }
                } else {
                    System.out.println("\n######## Komputer wygral #########");
                    break;
                }
            }
        else
            while (true) {
                userTurn();
                if (stacksAvaliable()) {
                    computerTurn();
                    if (stacksAvaliable())
                        continue;
                    else {
                        System.out.println("\n######## Komputer wygral #########");
                        break;
                    }
                } else {
                    System.out.println("\n $$$$$$ Wygrales gratulacje $$$$$$$");
                    break;
                }
            }
    }

    public boolean stacksAvaliable() {
        ArrayList<Boolean> stacksExistence = new ArrayList<>();
        for (Stack stack : stacks) {
            stacksExistence.add(stack.exists());
        }
        return stacksExistence.stream().filter(el -> el).count() > 0;
    }

}
