package com.company;


import java.util.Scanner;


public class Battleship {
    static String player1;
    static String player2;
    static int[][] battlefield1 = new int[10][10];
    static int[][] battlefield2 = new int[10][10];
    static int[][] monitor1 = new int[10][10];
    static int[][] monitor2 = new int[10][10];
    static int x;
    static int y;
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int min = -200;
        int max = 200;
        // Перебираем значения до тех пор пока не произойдет сбой
        while (true) {
            double a = (int) (Math.random() * ((max - min) + 1)) + min;
            double b = (int) (Math.random() * ((max - min) + 1)) + min;
            double c = (int) (Math.random() * ((max - min) + 1)) + min;
            System.out.println(a);
            System.out.println(b);
            System.out.println(c);
            if (!testFunctions(a, b, c)){
                break;
            }
        }
        System.out.println("Player1 enter your name");
        player1 = scanner.nextLine();
        System.out.println("Player2 enter your name");
        player2 = scanner.nextLine();
        placeships(player1, battlefield1);
        placeships(player2, battlefield2);

        while (true) {
            makeMove(player1, monitor1, battlefield2);
            if (ThisIsVictory()) {
                break;
            }
            makeMove(player2, monitor2, battlefield1);
            if (ThisIsVictory()) {
                break;
            }
        }
    }

    // Фазер
    public static boolean testFunctions(double a, double b, double c) {
        int z = 0;
        int x = 0;
        int d = 0;
        boolean p;
        boolean m;
        boolean itog;
        if (a == 0) {
            p = false;
        } else p = true;
        if (c == 0) {
            m = false;
        } else m = true;

        if (p) {
            z = -2;
        }
        if (b < 5) {
            if (!p && m) {
                x = 1;
            }
            d = 2;
        }
        if (z + x + d == 3) {
            itog = false;
        } else itog = true;
        assert itog;
        return itog;
    }

    // placeShips - расстановка кораблей
    public static void placeships(String player, int[][] battlefield) {
        int deck = 4;
        while (deck >= 1) {

            System.out.println(player + " please place your " + deck + "-deck ship on the battlefield:");
            drawField(battlefield);
            System.out.println("Please enter OX coordinate");
            x = scanner.nextInt();
            while (x > 9 || x < 0) {
                System.out.println("Please enter OX coordinate again");
                x = scanner.nextInt();
            }

            System.out.println("Please enter OY coordinate");
            y = scanner.nextInt();
            while (y > 9 || y < 0) {
                System.out.println("Please enter OY coordinate again");
                y = scanner.nextInt();
            }
            System.out.println("Choose direction:");
            System.out.println("1. Vertical");
            System.out.println("2. Horizontal");
            int rotation = scanner.nextInt();
            if (!isAvailable(x, y, deck, rotation)) {
                System.out.println("The ship goes out of the battlefield, place the ship again");
                continue;
            }
            for (int i = 0; i < deck; i++) {
                int flag = 1;
                do {
                    switch (rotation) {
                        case 1:
                            battlefield[x][y + i] = 1;
                            flag = 1;
                            break;
                        case 2:
                            battlefield[x + i][y] = 1;
                            flag = 1;
                            break;
                        default:
                            System.out.println("Please enter the correct placement");
                            rotation = scanner.nextInt();
                            flag = 0;
                    }

                } while (flag == 0);

            }
            deck--;

        }
    }

    // drawField - отрисовка поля боя
    public static void drawField(int[][] battlefield) {
        System.out.println("  0 1 2 3 4 5 6 7 8 9");
        // Отрисовываем поле через двумерный массив
        for (int i = 0; i < battlefield.length; i++) {
            System.out.print(i + " ");
            for (int j = 0; j < battlefield.length; j++) {
                if (battlefield[j][i] == 0) {
                    System.out.print("- ");
                } else {
                    System.out.print("X ");
                }
            }
            System.out.println();
        }
    }

    // makeMove - выстрелы игроков и отражение действий который сделал игрок, попал по кораблю, не попал и тд
    public static void makeMove(String player, int[][] monitor, int[][] battlefield) {
        int flag = 1;
        while (flag == 1) {
            System.out.println(player + " make you turn");
            System.out.println("  0 1 2 3 4 5 6 7 8 9");
            for (int i = 0; i < monitor.length; i++) {
                System.out.print(i + " ");
                for (int j = 0; j < monitor.length; j++) {
                    // 0 - значит игрок не стрелял
                    if (monitor[j][i] == 0) {
                        System.out.print("- ");
                    }
                    // 1 - игрок стрелял, но не попал
                    if (monitor[j][i] == 1) {
                        System.out.print("* ");
                    }
                    // 2 - игрок стрелял и попал
                    if (monitor[j][i] == 2) {
                        System.out.print("X ");
                    }
                }
                System.out.println();
            }
            System.out.println("Please enter OX coordinate");
            x = scanner.nextInt();
            while (x > 9 || x < 0) {
                System.out.println("Please enter OX coordinate again");
                x = scanner.nextInt();
            }

            System.out.println("Please enter OY coordinate");
            y = scanner.nextInt();
            while (y > 9 || y < 0) {
                System.out.println("Please enter OY coordinate again");
                y = scanner.nextInt();
            }
            if (battlefield[x][y] == 1) {
                System.out.println("Congratulation, you hit the target");
                monitor[x][y] = 2;
                if (ThisIsVictory()) {
                    break;
                }
            }
            if (battlefield[x][y] == 0) {
                System.out.println("Unfortunately, you missed the target.");
                monitor[x][y] = 1;
                flag = 0;
            }

        }

    }

    // ThisIsVictory - функция проверяет победил ли игрок
    public static boolean ThisIsVictory() {
        int counter1 = 0;
        int counter2 = 0;
        // В два счётчика собираем сколько клеток подбили всего игрок 1 и игрок 2
        for (int i = 0; i < monitor1.length; i++) {
            for (int j = 0; j < monitor1.length; j++) {
                if (monitor1[j][i] == 2) {
                    counter1++;
                }
            }
        }

        for (int i = 0; i < monitor2.length; i++) {
            for (int j = 0; j < monitor2.length; j++) {
                if (monitor2[j][i] == 2) {
                    counter2++;
                }
            }
        }
        // Так как у нас 4-палубный, 3-ех палубный, 2-ух палубный и одно палубный корабль, то для победы
        // нам нужно подбить 10 клеток, что и является победой одного из игроков
        if (counter1 == 10) {
            System.out.println(player1 + " WINNER, WINNER CHICKEN DINNER!");
        }
        if (counter2 == 10) {
            System.out.println(player2 + " WINNER, WINNER CHICKEN DINNER!");
        }
        if (counter1 == 10 || counter2 == 10) {
            return true;
        }
        return false;
    }


    public static boolean isAvailable(int x, int y, int deck, int rotation) {
        // Проверка не поставил ли игрок корабль таким образом, что он выходит за зону поля боя
        if (rotation == 1) {
            if (y + deck > 10) {
                return false;
            }
        }
        if (rotation == 2) {
            if (x + deck > 10) {
                return false;
            }
        }
        return true;

    }
}
