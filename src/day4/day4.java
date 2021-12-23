package day4;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class day4 {
    public static void main(String[] args) {
        List<String> inputs = new ArrayList<>();
        Map<Integer, List<Integer>> boardMap = new HashMap<>();

        try {
            inputs.addAll(Files.readAllLines(Paths.get(day4.class.getResource("puzzleinput.txt").toURI())));
        } catch (Exception ex) {
            System.out.println("An error occurred attempting to read your input file.");
        }

        List<Integer> draws = Arrays.stream(inputs.get(0).split(",")).map(input -> Integer.parseInt(input)).collect(Collectors.toList());
        List<Map[][]> boards = new ArrayList<>();

        for (int i = 2; i < inputs.size(); i += 6) {
            Map[][] board = new HashMap[5][5];

            for (int j = 0; j < 5; j++) {
                List<String> inputLine = Arrays.stream(inputs.get(i + j).split(" ")).filter(val -> !val.equals("")).collect(Collectors.toList());

                for (int y = 0; y < inputLine.size(); y++) {
                    int value = Integer.parseInt(inputLine.get(y));
                    board[j][y] = new HashMap();
                    board[j][y].put(value, false);

                    if (!boardMap.containsKey(value)) {
                        boardMap.put(value, new ArrayList<>());
                    }

                    boardMap.get(value).add(boards.size());
                }
            }

            boards.add(board);
        }

        System.out.println("The solution to part 1 is: " + calculateWinningBoardScore(boards, draws, boardMap, true));
        System.out.println("The solution to part 2 is: " + calculateWinningBoardScore(boards, draws, boardMap, false));
    }

    public static int calculateWinningBoardScore(List<Map[][]> boards, List<Integer> draws, Map<Integer, List<Integer>> boardMap, boolean firstToWin) {
        boolean boardWon = false;
        int winningBoard = 0;
        int winningDraw = 0;
        Set<Integer> winningBoards = new HashSet<>();

        for (int draw : draws) {
            for (int board : boardMap.get(draw)) {
                for (int x = 0; x < 5; x++) {
                    for (int y = 0; y < 5; y++) {
                        if (boards.get(board)[x][y].containsKey(draw)) {
                            boards.get(board)[x][y].put(draw, true);
                        }
                    }
                }

                boardWon = didBoardWin(boards.get(board));

                if (boardWon) {
                    winningBoards.add(board);
                    winningBoard = board;
                    winningDraw = draw;
                }

                if ((firstToWin && boardWon) || (!firstToWin && winningBoards.size() == boards.size())) {
                    break;
                }
            }

            if ((firstToWin && boardWon) || (!firstToWin && winningBoards.size() == boards.size())) {
                break;
            }
        }

        return calculateBoardScore(boards.get(winningBoard), winningDraw);
    }

    public static boolean didBoardWin(Map[][] board) {
        boolean won = false;
        boolean horizontalWin = true;
        boolean verticalWin = true;

        for (int x = 0; x < 5; x++) {
            for (int y = 0; y < 5; y++) {
                if (horizontalWin && board[x][y].containsValue(false)) {
                    horizontalWin = false;
                }
                if (verticalWin && board[y][x].containsValue(false)) {
                    verticalWin = false;
                }
            }

            if (horizontalWin || verticalWin) {
                won = true;
                break;
            }

            horizontalWin = true;
            verticalWin = true;
        }

        return won;
    }

    public static int calculateBoardScore(Map[][] board, int winningDraw) {
        int sum = 0;

        for (int x = 0; x < 5; x++) {
            for (int y = 0; y < 5; y++) {
                Map.Entry<Integer, Boolean> entry = (Map.Entry<Integer, Boolean>) board[x][y].entrySet().iterator().next();

                if (entry.getValue() == false) {
                    sum += entry.getKey();
                }
            }
        }

        return sum * winningDraw;
    }
}
