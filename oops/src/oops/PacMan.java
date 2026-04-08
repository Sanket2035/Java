package oops;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

/**
 * Console-Based Pac-Man Game in Java
 * ====================================
 * Controls : W = Up | S = Down | A = Left | D = Right | Q = Quit
 * Symbols  : C = Pac-Man | G = Ghost | . = Dot | O = Power Pellet | # = Wall
 */
public class PacMan {

    // ─── Constants ───────────────────────────────────────────────────────────────
    static final int ROWS = 15;
    static final int COLS = 25;

    static final char WALL         = '#';
    static final char DOT          = '.';
    static final char POWER_PELLET = 'O';
    static final char EMPTY        = ' ';
    static final char PACMAN       = 'C';
    static final char GHOST        = 'G';
    static final char SCARED_GHOST = 'S';   // ghost during power-up

    static final int DOT_SCORE    = 10;
    static final int PELLET_SCORE = 50;
    static final int GHOST_SCORE  = 200;
    static final int POWER_TICKS  = 15;    // how many moves power-up lasts

    // ─── Map Layout (15 rows × 25 cols) ─────────────────────────────────────────
    static final String[] MAP_TEMPLATE = {
        "#########################",
        "#............#..........#",
        "#.##.###.###.#.###.##.###",  // fixed to 25 chars
        "#O##.###.###.#.###.##.##O#",
        "#.##.###.###.#.###.##.###",
        "#........................#",
        "#.##.#.#######.#.##.####",
        "#....#....#....#........#",
        "#.######..#..######.####",
        "#........###........####",
        "#.##.###.###.###.##.####",
        "#.##.###.###.###.##.####",
        "#O...............#.....O#",
        "#.##.#.#######.#.##.####",
        "#########################"
    };

    // ─── Game State ──────────────────────────────────────────────────────────────
    static char[][] board;
    static int pacRow, pacCol;
    static int score     = 0;
    static int lives     = 3;
    static int dotsLeft  = 0;
    static int powerTimer = 0;

    static List<Ghost> ghosts = new ArrayList<>();

    // ─── Ghost Class ─────────────────────────────────────────────────────────────
    static class Ghost {
        int row, col;
        int prevRow, prevCol;
        char symbol;

        Ghost(int r, int c, char s) {
            row = r; col = c;
            prevRow = r; prevCol = c;
            symbol = s;
        }

        /** Simple AI: try to move toward Pac-Man; fall back to random valid move */
        void move(char[][] b) {
            int[] dRow = {-1, 1,  0, 0};
            int[] dCol = { 0, 0, -1, 1};

            // Score each direction (lower Manhattan distance = better)
            int bestScore = Integer.MAX_VALUE;
            int bestDir   = -1;

            // Shuffle direction order slightly to avoid deadlocks
            int start = (int)(Math.random() * 4);

            for (int i = 0; i < 4; i++) {
                int d  = (start + i) % 4;
                int nr = row + dRow[d];
                int nc = col + dCol[d];

                if (nr < 0 || nr >= ROWS || nc < 0 || nc >= COLS) continue;
                if (b[nr][nc] == WALL) continue;
                // Avoid immediately reversing unless no other option
                if (nr == prevRow && nc == prevCol && bestDir != -1) continue;

                int dist = Math.abs(nr - pacRow) + Math.abs(nc - pacCol);

                // During power-up, flee from Pac-Man
                if (powerTimer > 0) dist = -dist;

                if (dist < bestScore) {
                    bestScore = dist;
                    bestDir   = d;
                }
            }

            if (bestDir != -1) {
                prevRow = row;
                prevCol = col;
                row += dRow[bestDir];
                col += dCol[bestDir];
            }
        }
    }

    // ─── Initialisation ──────────────────────────────────────────────────────────
    static void initBoard() {
        board    = new char[ROWS][COLS];
        dotsLeft = 0;
        ghosts.clear();

        for (int r = 0; r < ROWS; r++) {
            String row = MAP_TEMPLATE[r];
            for (int c = 0; c < COLS; c++) {
                char ch = (c < row.length()) ? row.charAt(c) : EMPTY;
                board[r][c] = ch;
                if (ch == DOT || ch == POWER_PELLET) dotsLeft++;
            }
        }

        // Place Pac-Man in the centre
        pacRow = 7; pacCol = 12;
        board[pacRow][pacCol] = EMPTY;

        // Place ghosts in ghost house area
        ghosts.add(new Ghost(9,  11, '1'));
        ghosts.add(new Ghost(9,  12, '2'));
        ghosts.add(new Ghost(9,  13, '3'));

        for (Ghost g : ghosts) board[g.row][g.col] = EMPTY;
    }

    // ─── Rendering ───────────────────────────────────────────────────────────────
    static void render() {
        clearConsole();
        System.out.println("╔══════════════════════════════════╗");
        System.out.printf ("║  SCORE: %-6d   LIVES: %-3s       ║%n",
                            score, "♥".repeat(lives));
        if (powerTimer > 0)
            System.out.printf("║  *** POWER MODE: %-2d ticks ***     ║%n", powerTimer);
        System.out.println("╚══════════════════════════════════╝");

        // Build display grid (copy board, overlay dynamic objects)
        char[][] display = new char[ROWS][COLS];
        for (int r = 0; r < ROWS; r++)
            display[r] = board[r].clone();

        // Overlay ghosts
        for (Ghost g : ghosts)
            display[g.row][g.col] = (powerTimer > 0) ? SCARED_GHOST : GHOST;

        // Overlay Pac-Man (always on top)
        display[pacRow][pacCol] = PACMAN;

        for (int r = 0; r < ROWS; r++) {
            System.out.print("  ");
            for (int c = 0; c < COLS; c++) {
                char ch = display[r][c];
                System.out.print(charToVisual(ch));
            }
            System.out.println();
        }

        System.out.println();
        System.out.println("  Controls: W=Up  S=Down  A=Left  D=Right  Q=Quit");
        System.out.print  ("  Move: ");
    }

    static String charToVisual(char ch) {
        switch (ch) {
            case WALL:         return "█";
            case DOT:          return "·";
            case POWER_PELLET: return "●";
            case PACMAN:       return "C";
            case GHOST:        return "G";
            case SCARED_GHOST: return "s";
            default:           return " ";
        }
    }

    static void clearConsole() {
        // Works in most Unix/Windows terminals
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    // ─── Movement & Collision ────────────────────────────────────────────────────
    static boolean movePacMan(char dir) {
        int nr = pacRow, nc = pacCol;
        switch (dir) {
            case 'w': case 'W': nr--; break;
            case 's': case 'S': nr++; break;
            case 'a': case 'A': nc--; break;
            case 'd': case 'D': nc++; break;
            default: return false;   // invalid input
        }

        // Tunnel wrap (left/right edges)
        if (nc < 0)     nc = COLS - 1;
        if (nc >= COLS) nc = 0;
        if (nr < 0)     nr = ROWS - 1;
        if (nr >= ROWS) nr = 0;

        if (board[nr][nc] == WALL) return false;   // blocked

        // Eat dot / pellet
        if (board[nr][nc] == DOT) {
            score += DOT_SCORE;
            dotsLeft--;
            board[nr][nc] = EMPTY;
        } else if (board[nr][nc] == POWER_PELLET) {
            score += PELLET_SCORE;
            dotsLeft--;
            board[nr][nc] = EMPTY;
            powerTimer = POWER_TICKS;
        }

        pacRow = nr;
        pacCol = nc;
        return true;
    }

    static void moveGhosts() {
        for (Ghost g : ghosts) g.move(board);
    }

    /** Returns true if Pac-Man collides with any ghost */
    static boolean checkGhostCollision() {
        for (Ghost g : ghosts) {
            if (g.row == pacRow && g.col == pacCol) {
                if (powerTimer > 0) {
                    // Eat ghost — respawn it
                    score += GHOST_SCORE;
                    g.row = 9; g.col = 12;
                } else {
                    return true;   // Pac-Man dies
                }
            }
        }
        return false;
    }

    // ─── Lose a Life ─────────────────────────────────────────────────────────────
    static void loseLife() {
        lives--;
        powerTimer = 0;
        System.out.println("\n  *** Caught by a ghost! ***");
        sleep(1500);
        if (lives > 0) {
            // Reset positions
            pacRow = 7; pacCol = 12;
            ghosts.get(0).row = 9; ghosts.get(0).col = 11;
            ghosts.get(1).row = 9; ghosts.get(1).col = 12;
            ghosts.get(2).row = 9; ghosts.get(2).col = 13;
        }
    }

    // ─── Utility ─────────────────────────────────────────────────────────────────
    static void sleep(long ms) {
        try { Thread.sleep(ms); } catch (InterruptedException ignored) {}
    }

    // ─── Main Game Loop ──────────────────────────────────────────────────────────
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("╔════════════════════════════════╗");
        System.out.println("║      CONSOLE PAC-MAN  v1.0     ║");
        System.out.println("║  W/A/S/D to move  |  Q to quit ║");
        System.out.println("╚════════════════════════════════╝");
        System.out.println("  Press ENTER to start...");
        scanner.nextLine();

        initBoard();

        while (true) {
            render();

            // Read input
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) continue;
            char dir = input.charAt(0);

            if (dir == 'q' || dir == 'Q') {
                System.out.println("\n  Thanks for playing! Final score: " + score);
                break;
            }

            // Move Pac-Man
            movePacMan(dir);

            // Tick power timer
            if (powerTimer > 0) powerTimer--;

            // Move ghosts (every move Pac-Man makes)
            moveGhosts();

            // Check collision after ghost move
            if (checkGhostCollision()) {
                loseLife();
                if (lives <= 0) {
                    render();
                    System.out.println("\n  ╔══════════════════════════╗");
                    System.out.println("  ║       GAME  OVER         ║");
                    System.out.printf ("  ║  Final Score: %-10d║%n", score);
                    System.out.println("  ╚══════════════════════════╝");
                    break;
                }
            }

            // Check win condition
            if (dotsLeft <= 0) {
                render();
                System.out.println("\n  ╔══════════════════════════╗");
                System.out.println("  ║   YOU WIN! All dots eaten ║");
                System.out.printf ("  ║  Final Score: %-10d║%n", score);
                System.out.println("  ╚══════════════════════════╝");
                break;
            }
        }

        scanner.close();
    }
}