package search.maze;

public abstract class AbstractSearchEngine {
    protected Location[] searchPath = null;
    protected int pathCount;
    protected int maxDepth;
    protected Location startLoc, goalLoc, currentLoc;
    protected boolean isSearching = true;
    protected Maze maze;

    public Maze getMaze() { return maze; }

    public AbstractSearchEngine(int width, int height) {
        maze = new Maze(width, height);
        initSearch();
    }

    protected void initSearch() {
        if (searchPath == null) {
            searchPath = new Location[1000];
            for (int i = 0; i < 1000; i++) {
                searchPath[i] = new Location(); // Ensure this is okay with Location's default constructor
            }
        }
        pathCount = 0;
        startLoc = maze.startLoc;
        currentLoc = startLoc;
        goalLoc = maze.goalLoc;
        searchPath[pathCount++] = currentLoc;
    }

    protected boolean equals(Location d1, Location d2) {
        return d1.x == d2.x && d1.y == d2.y;
    }

    public Location[] getPath() {
        Location[] ret = new Location[maxDepth];
        for (int i = 0; i < maxDepth; i++) {
            ret[i] = searchPath[i];
        }
        return ret;
    }

    protected Location[] getPossibleMoves(Location loc) {
        Location[] tempMoves = new Location[4];
        int x = loc.x;
        int y = loc.y;
        int num = 0;

        if (maze.getValue(x - 1, y) == 0 || maze.getValue(x - 1, y) == Maze.GOAL_LOC_VALUE) {
            tempMoves[num++] = new Location(x - 1, y);
        }
        if (maze.getValue(x + 1, y) == 0 || maze.getValue(x + 1, y) == Maze.GOAL_LOC_VALUE) {
            tempMoves[num++] = new Location(x + 1, y);
        }
        if (maze.getValue(x, y - 1) == 0 || maze.getValue(x, y - 1) == Maze.GOAL_LOC_VALUE) {
            tempMoves[num++] = new Location(x, y - 1);
        }
        if (maze.getValue(x, y + 1) == 0 || maze.getValue(x, y + 1) == Maze.GOAL_LOC_VALUE) {
            tempMoves[num++] = new Location(x, y + 1);
        }
        return tempMoves;
    }
}
