package search.maze;

public class DepthFirstSearchEngine extends AbstractSearchEngine {

    public DepthFirstSearchEngine(int width, int height) {
        super(width, height);
        iterateSearch(startLoc, 1);
    }

    private void iterateSearch(Location loc, int depth) {
        if (!isSearching) return;

        maze.setValue(loc.x, loc.y, (short) depth);
        Location[] moves = getPossibleMoves(loc);

        for (int i = 0; i < 4; i++) {
            if (moves[i] == null) break;  // out of possible moves from this location

            searchPath[depth] = moves[i];

            if (equals(moves[i], goalLoc)) {
                System.out.println("Found the goal at " + moves[i].x + ", " + moves[i].y);
                isSearching = false;
                maxDepth = depth;
                return;
            } else {
                iterateSearch(moves[i], depth + 1);

                if (!isSearching) return;
            }
        }
    }
}
