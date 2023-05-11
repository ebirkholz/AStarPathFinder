package br.univali.searchalgorithms;


public enum HeuristicEnum {

    MANHATTAN("MANHATTAN"),
    EUCLIDEAN("EUCLIDEAN"),
    BREAKING_TIES("BREAKING_TIES");

    private final String name;
    

    HeuristicEnum(String name) {
        this.name = name;
    }

    public static Integer calculate(HeuristicEnum heuristic,
                                    int startX,
                                    int startY,
                                    int nodeX,
                                    int nodeY,
                                    int endX,
                                    int endY) {
        return switch (heuristic.name) {
            case "MANHATTAN" -> Math.abs(nodeX - endX) + Math.abs(nodeY - endY);
            case "EUCLIDEAN" -> (int) (Math.sqrt(nodeX - endX) + Math.sqrt(nodeY - endY));
            case "BREAKING_TIES" -> (int) 0.001*(Math.abs(((nodeX-endX)*(startY-endY)-((startX-endX)*(nodeY-endY)))));
            /**
             * Breaking ties calcula a heuristica da seguinte forma:
             * dx1 = current.x - goal.x
             * dy1 = current.y - goal.y
             * dx2 = start.x - goal.x
             * dy2 = start.y - goal.y
             * cross = abs(dx1*dy2 - dx2*dy1)
             * heuristic += cross*0.001
             */
            default -> 0;
        };
    }
}
