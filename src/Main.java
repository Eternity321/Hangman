import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        new Main().startGame();
    }

    public void startGame() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Enter [S] to start or [E] to exit");
            String choice = scanner.nextLine().toUpperCase();
            switch (choice) {
                case "S":
                    showOptions();
                    return;
                case "E":
                    scanner.close();
                    return;
                case "O":
                    showOptions();
                    break;
                default:
                    System.out.println("Invalid input. Please try again.");
            }
        }
    }

    public void showOptions() {
        System.out.println("Select language options:");
        System.out.println("[1] Russian");
        System.out.println("[2] English");
        System.out.println("[M] Main Menu");
        Scanner scanner = new Scanner(System.in);
        String optionChoice = scanner.nextLine().toLowerCase();
        switch (optionChoice) {
            case "1":
                gameLogic("src/resources/ru_words.txt");
                break;
            case "2":
                gameLogic("src/resources/en_words.txt");
                break;
            case "m":
                startGame();
                break;
            default:
                System.out.println("Invalid option. Please try again.");
                showOptions();
        }
    }

    public void gameLogic(String filename) {
        Scanner scanner = new Scanner(System.in);
        int countLife = 6;
        char[] word = choiceWord(filename);
        if (word == null) {
            System.out.println("Word list is empty. Exiting the game.");
            return;
        }
        boolean[] guessedLetters = new boolean[word.length];
        List<Character> usedLetters = new ArrayList<>();
        boolean gameWon = false;
        while (countLife > 0) {
            printHangman(countLife);
            for (int i = 0; i < word.length; i++) {
                if (guessedLetters[i]) {
                    System.out.print(word[i]);
                } else {
                    System.out.print("*");
                }
            }
            System.out.println();
            System.out.println("Letters used:" + usedLetters);
            System.out.println("Enter a letter.");
            String input = scanner.nextLine();
            if (input.length() != 1 || !Character.isLetter(input.charAt(0))) {
                System.out.println("Please enter only one letter.");
                continue;
            }
            char letter = Character.toLowerCase(input.charAt(0));
            if (usedLetters.contains(letter)) {
                System.out.println("Letter " + letter + " has already been used.");
                continue;
            }
            usedLetters.add(letter);
            boolean found = false;
            for (int i = 0; i < word.length; i++) {
                if (word[i] == letter) {
                    guessedLetters[i] = true;
                    found = true;
                }
            }
            if (!found) {
                countLife--;
                System.out.println("Letter " + letter + " is not in the word. You have " + countLife + " tries left");
            }
            boolean allGuessed = true;
            for (boolean guessed : guessedLetters) {
                if (!guessed) {
                    allGuessed = false;
                    break;
                }
            }
            if (allGuessed) {
                gameWon = true;
                System.out.print("Congratulations! You guessed the word: ");
                for (char c : word) {
                    System.out.print(c);
                }
                System.out.println();
                startGame();
                break;
            }
        }
        if (!gameWon) {
            printHangman(0);
            System.out.print("You ran out of tries. The word was: ");
            for (char c : word) {
                System.out.print(c);
            }
            System.out.println();
            startGame();
        }
    }

    public char[] choiceWord(String filename) {
        try {
            File file = new File(filename);
            Scanner scanner = new Scanner(file);
            List<String> words = new ArrayList<>();
            while (scanner.hasNext()) {
                words.add(scanner.next());
            }
            scanner.close();
            Random random = new Random();
            if (words.isEmpty()) {
                return null;
            }
            int randomIndex = random.nextInt(words.size());
            String randomWord = words.get(randomIndex);
            return randomWord.toCharArray();
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
            return null;
        }
    }

    public void printHangman(int countLife) {
        switch (countLife) {
            case 6:
                System.out.println(" _______");
                System.out.println("|       |");
                System.out.println("|");
                System.out.println("|");
                System.out.println("|");
                System.out.println("|________");
                break;
            case 5:
                System.out.println(" _______");
                System.out.println("|       |");
                System.out.println("|       O");
                System.out.println("|");
                System.out.println("|");
                System.out.println("|________");
                break;
            case 4:
                System.out.println(" _______");
                System.out.println("|       |");
                System.out.println("|       O");
                System.out.println("|       |");
                System.out.println("|");
                System.out.println("|________");
                break;
            case 3:
                System.out.println(" _______");
                System.out.println("|       |");
                System.out.println("|       O");
                System.out.println("|      /|");
                System.out.println("|");
                System.out.println("|________");
                break;
            case 2:
                System.out.println(" _______");
                System.out.println("|       |");
                System.out.println("|       O");
                System.out.println("|      /|\\");
                System.out.println("|");
                System.out.println("|________");
                break;
            case 1:
                System.out.println(" _______");
                System.out.println("|       |");
                System.out.println("|       O");
                System.out.println("|      /|\\");
                System.out.println("|      /  ");
                System.out.println("|________");
                break;
            case 0:
                System.out.println(" _______");
                System.out.println("|       |");
                System.out.println("|       O");
                System.out.println("|      /|\\");
                System.out.println("|      / \\ ");
                System.out.println("|________");
                break;
        }
    }
}