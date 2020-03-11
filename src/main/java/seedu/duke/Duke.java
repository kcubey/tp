package seedu.duke;

import seedu.cards.Card;
import seedu.cards.CardList;
import seedu.commands.Command;
import seedu.exception.EscException;
import seedu.parser.Parser;

import java.util.ArrayList;
import java.util.Scanner;

public class Duke {
    /**
     * Main entry-point for the java.duke.Duke application.
     */
    public static UI ui = new UI();
    private static Scanner in = new Scanner(System.in);
    private Storage storage = new Storage();
    private CardList cards = new CardList(storage.loadCards());

    /**
     *  Lists all the cards in the list.
     *   @param cards A list of card to be displayed.
     */
    public static void listCards(ArrayList<Card> cards) {
        System.out.println("Here is the list of questions.");
        for (int i = 0; i < cards.size(); i++) {
            int j = i + 1;
            System.out.println(j + ". " + cards.get(i).getQuestion());
        }
    }

    /**
     * Reads the user command.
     * @return User command.
     */
    private static String readCommand() {
        String userInput = "";
        do {
            System.out.println("Enter command: ");
            userInput = in.nextLine();

            System.out.println("[Command entered: " + userInput + "]");
            return userInput;
        } while (in.hasNextLine());
    }

    /**
     * Reads the user's commands and executes them until the user issues the exit command.
     */
    private void run() {
        ui.showWelcome();
        boolean isExit = false;
        while (!isExit) {
            try {
                String fullCommand = readCommand();
                Command c = Parser.parse(fullCommand);
                c.execute(cards);
                storage.saveCards(cards.getCards());
                isExit = c.isExit();
            } catch (EscException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Main method.
     */
    public static void main(String[] args) {
        new Duke().run();
    }
}