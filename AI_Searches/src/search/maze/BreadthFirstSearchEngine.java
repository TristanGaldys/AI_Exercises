package search.maze;

public class BreadthFirstSearchEngine extends AbstractSearchEngine {

    public BreadthFirstSearchEngine(int width, int height) {
        super(width, height);
        doSearchOn2DGrid();
    }

    private void doSearchOn2DGrid() {
        int width = maze.getWidth();
        int height = maze.getHeight();
        boolean[][] alreadyVisited = new boolean[width][height];
        Location[][] predecessor = new Location[width][height];
        LocationQueue queue = new LocationQueue();

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                alreadyVisited[i][j] = false;
                predecessor[i][j] = null;
            }
        }

        alreadyVisited[startLoc.x][startLoc.y] = true;
        queue.addToBackOfQueue(startLoc);

        boolean success = false;
        while (!queue.isEmpty()) {
            Location head = queue.peekAtFrontOfQueue();
            if (head == null) break;

            Location[] connected = getPossibleMoves(head);
            for (Location loc : connected) {
                if (loc == null) break;

                int w = loc.x;
                int h = loc.y;
                if (!alreadyVisited[w][h]) {
                    alreadyVisited[w][h] = true;
                    predecessor[w][h] = head;
                    queue.addToBackOfQueue(loc);

                    if (equals(loc, goalLoc)) {
                        success = true;
                        break;
                    }
                }
            }

            if (success) {
                break; // Exit the outer loop as well
            }

            queue.removeFromFrontOfQueue();
        }

        // Calculate the shortest path from the predecessor array
        maxDepth = 0;
        if (success) {
            searchPath[maxDepth++] = goalLoc;
            while (!equals(searchPath[maxDepth - 1], startLoc)) {
                searchPath[maxDepth] = predecessor[searchPath[maxDepth - 1].x][searchPath[maxDepth - 1].y];
                maxDepth++;
            }
        }
    }

    protected class LocationQueue {
        private Location[] queue;
        private int tail, head, len;

        public LocationQueue(int num) {
            queue = new Location[num];
            head = tail = 0;
            len = num;
        }

        public LocationQueue() {
            this(400);
        }

        public void addToBackOfQueue(Location n) {
            queue[tail] = n;
            if (tail >= (len - 1)) {
                tail = 0;
            } else {
                tail++;
            }
        }

        public Location removeFromFrontOfQueue() {
            Location ret = queue[head];
            if (head >= (len - 1)) {
                head = 0;
            } else {
                head++;
            }
            return ret;
        }

        public boolean isEmpty() {
            return head == (tail + 1) % len;
        }

        public Location peekAtFrontOfQueue() {
            return queue[head];
        }
    }
}
