package br.univali.searchalgorithms;


public enum HeuristicEnum {

    MANHATTAN("MANHATTAN"),
    EUCLIDEAN("EUCLIDEAN");

    private final String name;

    HeuristicEnum(String name) {
        this.name = name;
    }

    public static Integer calculate(HeuristicEnum heuristic,
                                    int startX,
                                    int startY,
                                    int endX,
                                    int endY) {
        return switch (heuristic.name) {
            case "MANHATTAN" -> Math.abs(startX - endX) + Math.abs(startY - endY);
            case "EUCLIDEAN" -> (int) (Math.sqrt(startX - endX) + Math.sqrt(startY - endY));
            default -> 0;
        };
    }
}
